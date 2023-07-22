package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConePacketSet
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-05,9:24.
 */
@Sharable
class ConeClientChannelHandler : SimpleChannelInboundHandler<DatagramPacket>() {

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
        //第一个byte
        val packetId = msg.content().readByte().toInt()
        val data = FriendlyByteBuf(msg.content())
        ConePacketSet.createPacketAndProcess(packetId, data)
    }
}