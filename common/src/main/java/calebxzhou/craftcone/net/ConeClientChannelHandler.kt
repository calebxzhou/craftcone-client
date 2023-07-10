package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConePacket
import io.netty.channel.*
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.socket.DatagramPacket
import net.minecraft.network.FriendlyByteBuf
import java.net.InetSocketAddress

/**
 * Created  on 2023-07-05,9:24.
 */
@Sharable
class ConeClientChannelHandler(val serverAddr: InetSocketAddress) : SimpleChannelInboundHandler<DatagramPacket>() {

    //接收包数
    var packetCountRx = 0
        private set
    //发包数
    var packetCountTx = 0

    override fun channelInactive(ctx: ChannelHandlerContext) {
        super.channelInactive(ctx)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        LOG.error("连接错误：",cause)
    }
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: DatagramPacket) {
        ++packetCountRx
        val packetId = msg.content().readByte().toInt()
        val data = FriendlyByteBuf(msg.content())
        ConeNetManager.createPacket(packetId,data)?.process() ?:let {
            LOG.error("找不到包ID $packetId 的ctor")
            return
        }
    }
}