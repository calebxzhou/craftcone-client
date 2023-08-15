package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
data class PlayerJoinedRoomS2CPacket(
    val pid: Int,
    val pName: String
) : Packet, InRoomProcessable{
    companion object : BufferReadable<PlayerJoinedRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerJoinedRoomS2CPacket {
            return PlayerJoinedRoomS2CPacket(buf.readVarInt(),buf.readUtf())
        }

    }

    override fun process(server: IntegratedServer, room: Room) {
        coneMsg(MsgType.Chat,MsgLevel.Info,"$pName 加入了房间")
        room.addPlayer(ConePlayer(pid,pName))
    }
}