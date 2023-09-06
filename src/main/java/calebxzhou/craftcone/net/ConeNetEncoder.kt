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
        val packetId = ConePacketSet.getPacketId(packet.javaClass) ?: let {
            logger.error("找不到$packet 对应的包ID")
            return
        }
        val buf = ByteBuf(data)
        buf.writeByte(packetId)
        packet.write(buf)
    }
}
