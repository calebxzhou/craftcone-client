package calebxzhou.craftcone.entity

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.net.protocol.room.PlayerJoinRoomC2SPacket
import calebxzhou.craftcone.net.protocol.room.PlayerLeaveRoomC2SPacket
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.craftcone.ui.overlay.coneDialog
import calebxzhou.craftcone.ui.screen.ConeRoomInfoScreen
import calebxzhou.craftcone.utils.blockStateAmount
import net.minecraft.network.FriendlyByteBuf
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Created  on 2023-08-11,12:11.
 */
data class Room(
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
    val players = hashMapOf<Int, ConePlayer>()


    companion object : BufferReadable<Room>{
        var now: Room? = null
        //从服务器收到房间信息
        override fun read(buf: FriendlyByteBuf): Room {
            return Room(
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
        fun load(room: Room) {
            logger.info{ "载入房间中 $room" }
            if (blockStateAmount != room.blockStateAmount) {
                coneDialog(ConeDialogType.ERR){
                    "方块状态数量不一致：您${blockStateAmount}个/房间${room.blockStateAmount}个。检查Mod列表！"
                }
                return
            }
            ConeNetSender.sendPacket(PlayerJoinRoomC2SPacket(room.id))
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
            ConeNetSender.sendPacket(PlayerLeaveRoomC2SPacket())
            now = null
        }
    }
    //从服务器收到房间信息
    override fun process() {
        (Mc.screen as ConeRoomInfoScreen).onResponse(this)
    }
    override fun toString(): String {
        return "$name($id)"
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