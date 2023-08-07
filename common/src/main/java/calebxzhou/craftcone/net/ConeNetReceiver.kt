package calebxzhou.craftcone.net

import calebxzhou.craftcone.entity.ConeConnection
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.rdi.goRdiTitleScreen
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-05,9:24.
 */
@Sharable
class ConeNetReceiver : SimpleChannelInboundHandler<DatagramPacket>() {


    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable) {
        logger.error("连接错误：",cause)
        ConeConnection.disconnect()
        goRdiTitleScreen()
        ConeDialog.show(ConeDialogType.ERR,"连接错误。${cause.javaClass.name}:${cause.localizedMessage}")
    }
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: DatagramPacket) {
        //第一个byte
        val packetId = msg.content().readByte().toInt()
        val data = FriendlyByteBuf(msg.content())
        ConePacketSet.createAndProcess(packetId, data)
    }
}