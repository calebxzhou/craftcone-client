package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-12,9:57.
 */
data class DeleteRoomC2SPacket(
    val rid:Int
): Packet,BufferWritable{

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(rid)
    }

}
