package calebxzhou.craftcone.net

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferWritable
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

/**
 * Created  on 2023-08-07,23:37.
 */
class ConeNetEncoder : MessageToByteEncoder<BufferWritable>() {
    override fun encode(ctx: ChannelHandlerContext, packet: BufferWritable, data: ByteBuf) {
        logger.debug("Encoding packet ${packet.javaClass.simpleName} ")
        ConePacketSet.getPacketId(packet.javaClass)?.let { packetId->
            data.writeByte(packetId)
            packet.write(data)
            logger.debug("Packet ${packet.javaClass.simpleName} encoded with ID $packetId, size ${data.readableBytes()}")
        } ?: run {
            logger.error("Can't find corresponding ID of Packet ${packet.javaClass.simpleName}")
        }
    }
}
