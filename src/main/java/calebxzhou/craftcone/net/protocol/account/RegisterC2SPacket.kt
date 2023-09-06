package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.writeUtf
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-07-21,10:37.
 */
data class RegisterC2SPacket(
    val pName: String,
    val pwd: String,
    val email: String,
) : Packet, BufferWritable {

    override fun write(buf: ByteBuf) {
        buf.writeUtf(pName)
        buf.writeUtf(pwd)
        buf.writeUtf(email)
    }

}
