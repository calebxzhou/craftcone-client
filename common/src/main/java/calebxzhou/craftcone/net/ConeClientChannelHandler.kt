package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.entity.ConeConnection
import calebxzhou.craftcone.net.protocol.ConePacketSet
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
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


    override fun channelInactive(ctx: ChannelHandlerContext) {
        super.channelInactive(ctx)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable) {
        LOG.error("连接错误：",cause)
        ConeDialog.show(ConeDialogType.ERR,"连接错误。${cause.javaClass.name}:${cause.localizedMessage}")
        ConeConnection.disconnect()
    }
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: DatagramPacket) {
        //第一个byte
        val packetId = msg.content().readByte().toInt()
        val data = FriendlyByteBuf(msg.content())
        ConePacketSet.createPacketAndProcess(packetId, data)
    }
}