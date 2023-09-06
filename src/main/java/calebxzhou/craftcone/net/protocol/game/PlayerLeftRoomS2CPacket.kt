package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.utils.ByteBufUt.readObjectId
import io.netty.buffer.ByteBuf
import net.minecraft.client.server.IntegratedServer
import org.bson.types.ObjectId

/**
 * Created  on 2023-08-11,11:50.
 */
data class PlayerLeftRoomS2CPacket(
    val uid: ObjectId
) : Packet, InRoomProcessable {
    companion object : BufferReadable<PlayerLeftRoomS2CPacket> {
        override fun read(buf: ByteBuf): PlayerLeftRoomS2CPacket {
            return PlayerLeftRoomS2CPacket(buf.readObjectId())
        }

    }

    override fun process(server: IntegratedServer, room: ConeRoom) {
        room.onOtherPlayerLeft(uid)
    }

}
