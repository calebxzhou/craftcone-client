package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-21,10:37.
 */
data class RegisterC2SPacket(
    val pName: String,
    val pwd: String,
) : Packet, BufferWritable {

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(pName)
        buf.writeUtf(pwd)
    }

}
