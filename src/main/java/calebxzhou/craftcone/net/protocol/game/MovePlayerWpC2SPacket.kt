package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-07-13,10:21.
 */
data class MovePlayerWpC2SPacket(
    val w: Float,
    val p: Float,
) : Packet, BufferWritable {

    override fun write(buf: ByteBuf) {
        buf.writeFloat(w)
        buf.writeFloat(p)
    }


}
