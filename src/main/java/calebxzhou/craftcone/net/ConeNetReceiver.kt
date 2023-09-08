package calebxzhou.craftcone.net

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.ui.coneErrD
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 * Created  on 2023-07-05,9:24.
 */
@Sharable
class ConeNetReceiver : SimpleChannelInboundHandler<Packet>() {


    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable) {
        logger.error("连接错误：", cause)
        ConeConnection.disconnect()
        coneErrD("连接错误。${cause.javaClass.name}:${cause.localizedMessage}")
        Mc.goTitleScreen()
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        Mc.goTitleScreen()
    }
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: Packet) {
        ConePacketProcessor.processPacket(msg)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }
}
