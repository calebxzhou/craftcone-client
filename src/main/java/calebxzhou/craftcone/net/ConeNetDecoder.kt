package calebxzhou.craftcone.net

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
        out += ConePacketSet.createPacket(data.readByte().toInt(), data)?: return
    }
}
