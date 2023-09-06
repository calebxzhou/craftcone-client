package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.writeUtf
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
data class SendChatMsgC2SPacket(
    val msg: String,
) : Packet, BufferWritable {

    override fun write(buf: ByteBuf) {
        buf.writeUtf(msg)
    }


}
