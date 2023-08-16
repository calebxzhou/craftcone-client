package calebxzhou.craftcone.entity

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.net.protocol.room.JoinRoomPacket
import calebxzhou.craftcone.net.protocol.room.LeaveRoomPacket
import calebxzhou.craftcone.ui.coneErr
import calebxzhou.craftcone.ui.coneMsg
import calebxzhou.craftcone.ui.screen.ConeRoomInfoScreen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Created  on 2023-08-11,12:11.
 */
data class ConeRoom(
    //房间ID
    val id: Int,
    //房间名
    val name: String,
    //房主id
    val ownerId: Int,
    //mc版本
    val mcVersion: String,
    //mod加载器？Fabric：Forge
    val isFabric: Boolean,
    //创造
    val isCreative: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
    //地图种子
    val seed: Long,
    //创建时间
    val createTime: Long,
): Packet,RenderThreadProcessable {
    //玩家列表
    val players = hashMapOf<Int, ConePlayer>()

    //维度ID以及对应res key
    private val dimIdKeys = hashMapOf<Int,ResourceKey<Level>>()
    //维度res key以及对应维度ID
    private val dimKeysId = hashMapOf<ResourceKey<Level>,Int>()
    //保存的区块 {维度ID[区x,区z]}
    val savedChunks = hashMapOf<Int,MutableList<ChunkPos>>()
    companion object : BufferReadable<ConeRoom>{
        var now: ConeRoom? = null
        //从服务器收到房间信息
        override fun read(buf: FriendlyByteBuf): ConeRoom {
            return ConeRoom(
                buf.readVarInt(),
                buf.readUtf(),
                buf.readVarInt(),
                buf.readUtf(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readVarInt(),
                buf.readLong(),
                buf.readLong(),
            )
        }

        //载入房间
        fun loadAndJoin(room: ConeRoom) {
            if (Mc.blockStateAmount != room.blockStateAmount) {
                coneErr("方块状态数量不一致：您${Mc.blockStateAmount}个/房间${room.blockStateAmount}个。检查Mod列表！")
                return
            }
            coneMsg(MsgType.Toast,MsgLevel.Info,"开始载入房间 $room")
            logger.info{ "载入房间中 $room" }
            ConeNetSender.sendPacket(JoinRoomPacket(room.id))
            now = room
            val levelName = "${Mc.playerName}-${room.id}"
            if(Mc.hasLevel(levelName)){
                Mc.loadLevel(levelName)
            }else{
               Mc.createLevel(levelName,room.isCreative,room.seed)
            }
        }
        //卸载房间
        fun unload(){
            if(now == null){
                logger.warn { "现在没有游玩任何房间，不应该要求卸载" }
                return
            }
            logger.info { "正在卸载房间" }
            ConeNetSender.sendPacket(LeaveRoomPacket())
            now = null
        }
    }

    override fun process() {
        Mc.screen = ConeRoomInfoScreen(Mc.screen?:TitleScreen(),this)
    }

    override fun toString(): String {
        return "$name(ID=$id)"
    }

    fun addPlayer(player: ConePlayer) {
        players += player.id to player
    }
    fun removePlayer(id:Int){
        players -= id
    }
    val createTimeStr:String
        get() = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.ofInstant(Instant.ofEpochMilli(createTime),
            ZoneId.systemDefault()))

    //新增维度
    fun addDimension(level: ServerLevel){
        val id = dimIdKeys.size
        dimIdKeys += id to level.dimension()
        dimKeysId += level.dimension() to id
    }

    //根据维度编号取维度
    fun getLevelByDimId(dimId : Int) : ServerLevel {
        val dim: ResourceKey<Level> = dimIdKeys[dimId]?:run {
            //Cone.numDimKeyMap.forEach { (k, v) -> LOG.error("$k $v") }
            logger.warn("找不到编号为${dimId}的维度。")
            Level.OVERWORLD
        }

        val level = Mcl.getLevel(dim) ?:run {
            throw IllegalStateException("处理数据包时，未在游玩状态！")
        }
        return level
    }
    //根据维度取维度编号（取不到就默认主世界，0）
    fun getDimIdByLevel(level: Level) : Int{
        return dimKeysId[level.dimension()]?:0
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