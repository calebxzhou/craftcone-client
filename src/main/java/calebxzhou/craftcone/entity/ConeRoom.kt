package calebxzhou.craftcone.entity

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.mc.toMcPlayer
import calebxzhou.craftcone.net.ConeNetSender.sendPacket
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.net.protocol.game.SetBlockC2SPacket
import calebxzhou.craftcone.net.protocol.room.JoinRoomC2SPacket
import calebxzhou.craftcone.net.protocol.room.LeaveRoomC2SPacket
import calebxzhou.craftcone.ui.coneErrD
import calebxzhou.craftcone.ui.coneMsg
import calebxzhou.craftcone.ui.screen.ConeRoomInfoScreen
import calebxzhou.craftcone.utils.ByteBufUt.readObjectId
import calebxzhou.craftcone.utils.ByteBufUt.readUtf
import calebxzhou.craftcone.utils.ByteBufUt.readVarInt
import calebxzhou.craftcone.utils.blockStateOfId
import io.netty.buffer.ByteBuf
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import org.bson.types.ObjectId
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Created  on 2023-08-11,12:11.
 */
data class ConeRoom(
    //房间ID
    val id: ObjectId,
    //房间名
    val name: String,
    //mc版本
    val mcVersion: String,
    //创造
    val isCreative: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
    //地图种子
    val seed: Long,
    //创建时间
    val createTime: Long,
) : Packet, RenderThreadProcessable {

    //玩家列表
    private val inRoomPlayers = hashMapOf<ObjectId, ConePlayer>()

    val playerInfos
        get() = inRoomPlayers.values.map { it.mcPlayerInfo }

    companion object : BufferReadable<ConeRoom> {
        //维度ID以及对应res key
        private val dimIdKeys = hashMapOf<Int, ResourceKey<Level>>()

        //维度res key以及对应维度ID
        private val dimKeysId = hashMapOf<ResourceKey<Level>, Int>()
        @JvmStatic
        var now: ConeRoom? = null
            private set

        //从服务器收到房间信息
        override fun read(buf: ByteBuf): ConeRoom {
            return ConeRoom(
                buf.readObjectId(),
                buf.readUtf(),
                buf.readUtf(),
                buf.readBoolean(),
                buf.readVarInt(),
                buf.readLong(),
                buf.readLong(),
            )
        }

        //载入房间地图
        fun loadRoomLevel(room: ConeRoom) {
            if (Mc.blockStateAmount != room.blockStateAmount) {
                coneErrD("方块状态数量不一致：您${Mc.blockStateAmount}个/房间${room.blockStateAmount}个。检查Mod列表！")
                return
            }

            coneMsg(MsgType.Toast, MsgLevel.Info, "开始载入房间 $room")
            logger.info("载入房间中 $room")
            val levelName = "${Mc.playerName}-${room.id}"
            join(room)
            if (Mc.hasLevel(levelName)) {
                Mc.loadLevel(levelName)
            } else {
                Mc.createLevel(levelName, room.isCreative, room.seed)
            }

        }

        fun join(room: ConeRoom) {
            sendPacket(JoinRoomC2SPacket(room.id))
            now = room
        }

        //卸载房间
        fun unload() {
            if (now == null) {
                logger.warn("现在没有游玩任何房间，不应该要求卸载")
                return
            }
            logger.info("正在卸载房间")
            sendPacket(LeaveRoomC2SPacket())
            dimIdKeys.clear()
            dimKeysId.clear()
            now = null
        }
    }

    override fun process() {
        Mc.screen = ConeRoomInfoScreen(Mc.screen ?: TitleScreen(), this)
    }

    override fun toString(): String {
        return "$name(ID=$id)"
    }

    fun onOtherPlayerJoined(player: ConePlayer) {
        coneMsg(MsgType.Chat, MsgLevel.Info, "${player.name} 加入了房间")
        inRoomPlayers += player.id to player
        player.toMcPlayer().let { Mcl.spawnPlayer(it) }
    }

    fun onOtherPlayerLeft(uid: ObjectId) = inRoomPlayers[uid]?.let {
        coneMsg(MsgType.Chat, MsgLevel.Info, "${it.name} 离开了房间")
        inRoomPlayers -= id
        Mcl.despawnPlayer(it.toMcPlayer())
    } ?: let {
        logger.warn("收到了玩家 $uid 的离开房间包 但是没找到此玩家")
        return
    }

    fun getPlayer(id: ObjectId): ConePlayer? = inRoomPlayers[id]

    val createTimeStr: String
        get() = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(createTime),
                ZoneId.systemDefault()
        ).let {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(it)
        }


    //新增维度
    fun addDimension(level: ServerLevel) {
        logger.info("正在载入level ${level.dimension()}")
        val id = dimIdKeys.size
        dimIdKeys += id to level.dimension()
        dimKeysId += level.dimension() to id
    }

    //根据维度编号取维度
    fun getLevelByDimId(dimId: Int) = dimIdKeys[dimId]

    //根据维度取维度编号（取不到就默认主世界，0）
    fun getDimIdByLevel(level: Level): Int = dimKeysId[level.dimension()] ?: 0

    //当玩家破坏方块
    fun onPlayerBreakBlock(clientLevel: Level, blockPos: BlockPos) {
        if (clientLevel is ClientLevel)
            return
        val level = Mcl.getLevel(clientLevel.dimension()) ?: return
        sendPacket(
            SetBlockC2SPacket(
                getDimIdByLevel(level),
                blockPos,
                blockStateOfId(Blocks.AIR.defaultBlockState())
            )
        )
    }

    //当玩家右键点击方块
    fun onRightClickBlock(clientLevel: Level, pos: BlockPos) {
        if (clientLevel is ClientLevel)
            return
        val level = Mcl.getLevel(clientLevel.dimension()) ?: return
        //Mcl.logicThread {
        val tag = level.getBlockEntity(pos)?.saveWithoutMetadata()
        sendPacket(
            SetBlockC2SPacket(
                getDimIdByLevel(level),
                pos,
                blockStateOfId(level.getBlockState(pos)),
                tag?.asString
            )
        );
        // }
    }


    fun onPlayerPlaceBlock(clientLevel: Level, blockPos: BlockPos) {
        if (clientLevel is ClientLevel)
            return
        val level = Mcl.getLevel(clientLevel.dimension()) ?: return
        sendPacket(
            SetBlockC2SPacket(
                getDimIdByLevel(level),
                blockPos,
                blockStateOfId(level.getBlockState(blockPos)),
                level.getBlockEntity(blockPos)?.saveWithoutMetadata()?.asString
            )
        )
    }

    fun onSetBlock(clientLevel: Level, blockPos: BlockPos, blockState: BlockState) {
        if (clientLevel is ClientLevel)
            return
        if (!Mcl.isLocalServerReady)
            return
        if (Mcl.player == null)
            return
        val level = Mcl.getLevel(clientLevel.dimension()) ?: return

        val tag = level.getBlockEntity(blockPos)?.saveWithoutMetadata()
        sendPacket(
            SetBlockC2SPacket(
                getDimIdByLevel(level),
                blockPos,
                blockStateOfId(blockState),
                tag?.asString
            )
        )
    }

}
/*fun initialize(rid: String) {
      ConeDialog.show(ConeDialogType.WARN,"正在初始化房间，禁止退出游戏，禁止断开网络连接！")
      blockStates.forEachIndexed { i: Int, blockState: BlockState ->
          val bState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, blockState)
          val stateStr = bState.get().left().get().asString
          ConeNetManager.sendPacket(SetBlockStateC2SPacket(i,stateStr))
      }
      ConeDialog.show(ConeDialogType.INFO,"初始化完成")
  }*/
