package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-30,8:37.
 */
class GetMyRoomC2SPacket() : Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
    }
}
