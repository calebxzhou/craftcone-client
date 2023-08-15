package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-13,15:24.
 */
data class RoomInfoC2SPacket(
    val rid:Int
) : Packet,BufferWritable{
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(rid)
    }
}