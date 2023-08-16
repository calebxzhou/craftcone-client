package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-11,11:50.
 */
data class PlayerLeftRoomPacket(
    val pid: Int
): Packet,InRoomProcessable {
    companion object :BufferReadable<PlayerLeftRoomPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerLeftRoomPacket {
            return PlayerLeftRoomPacket(buf.readVarInt())
        }

    }
    override fun process(server: IntegratedServer, room: ConeRoom) {
        val player = room.players[pid] ?:let {
            logger.warn { "收到了玩家 $pid 的离开房间包 但是没找到此玩家" }
            return
        }
        coneMsg(MsgType.Chat,MsgLevel.Info, "${player.name} 离开了房间" )
        room.removePlayer(pid)
    }

}
