package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
data class PlayerJoinedRoomPacket(
    val pid: Int,
    val pName: String
) : Packet, InRoomProcessable{
    companion object : BufferReadable<PlayerJoinedRoomPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerJoinedRoomPacket {
            return PlayerJoinedRoomPacket(buf.readVarInt(),buf.readUtf())
        }

    }

    override fun process(server: IntegratedServer, room: ConeRoom) {
        coneMsg(MsgType.Chat,MsgLevel.Info,"$pName 加入了房间")
        room.addPlayer(ConePlayer(pid,pName))
    }
}