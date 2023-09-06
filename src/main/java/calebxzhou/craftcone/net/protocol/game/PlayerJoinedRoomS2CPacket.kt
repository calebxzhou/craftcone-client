package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.utils.ByteBufUt.readObjectId
import calebxzhou.craftcone.utils.ByteBufUt.readUtf
import io.netty.buffer.ByteBuf
import net.minecraft.client.server.IntegratedServer
import org.bson.types.ObjectId

/**
 * Created  on 2023-07-06,8:48.
 */
data class PlayerJoinedRoomS2CPacket(
    val uid: ObjectId,
    val pName: String
) : Packet, InRoomProcessable {
    companion object : BufferReadable<PlayerJoinedRoomS2CPacket> {
        override fun read(buf: ByteBuf): PlayerJoinedRoomS2CPacket {
            return PlayerJoinedRoomS2CPacket(buf.readObjectId(), buf.readUtf())
        }

    }

    override fun process(server: IntegratedServer, room: ConeRoom) {
        room.onOtherPlayerJoined(ConePlayer(uid, pName))
    }
}
