package calebxzhou.craftcone.net

import calebxzhou.craftcone.logger
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToMessageDecoder

/**
 * Created  on 2023-08-07,23:42.
 */
class ConeNetDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, data: ByteBuf, out: MutableList<Any>) {
        val packetId = data.readByte().toInt()
        logger.debug("decoding data, size = ${data.readableBytes()}, Packet ID = $packetId, ")
        out += ConePacketSet.createPacket(packetId, data) ?: return
    }
}
