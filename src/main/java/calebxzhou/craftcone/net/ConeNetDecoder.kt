package calebxzhou.craftcone.net

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageDecoder
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-07,23:42.
 */
class ConeNetDecoder : MessageToMessageDecoder<DatagramPacket>() {
    override fun decode(ctx: ChannelHandlerContext?, msg: DatagramPacket, out: MutableList<Any>) {
        val packetId = msg.content().readByte().toInt()
        val data = FriendlyByteBuf(msg.content())
        ConePacketSet.createPacket(packetId, data)?.also {
            out += it
        }
    }
}