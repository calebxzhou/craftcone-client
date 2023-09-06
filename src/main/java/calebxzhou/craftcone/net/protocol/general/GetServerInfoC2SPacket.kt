package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import io.netty.buffer.ByteBuf


class GetServerInfoC2SPacket() : Packet, BufferWritable {
    override fun write(buf: ByteBuf) {
    }

}
