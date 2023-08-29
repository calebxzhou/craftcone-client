package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.ConeByteBuf.Companion.readObjectId
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf
import org.bson.types.ObjectId

/**
 * Created  on 2023-08-11,11:50.
 */
data class PlayerLeftRoomS2CPacket(
    val uid: ObjectId
) : Packet, InRoomProcessable {
    companion object : BufferReadable<PlayerLeftRoomS2CPacket> {
        override fun read(buf: FriendlyByteBuf): PlayerLeftRoomS2CPacket {
            return PlayerLeftRoomS2CPacket(buf.readObjectId())
        }

    }

    override fun process(server: IntegratedServer, room: ConeRoom) {
        room.onOtherPlayerLeft(uid)
    }

}
