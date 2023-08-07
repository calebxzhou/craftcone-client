package calebxzhou.craftcone.net

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.rdi.goRdiTitleScreen
import io.netty.channel.nio.NioEventLoopGroup


/**
 * Created  on 2023-06-22,22:37.
 */
//网络管理器
object ConeNetSender {

    //TODO 做容器同步

    val workGroup = NioEventLoopGroup()

    @JvmStatic
    fun sendPacket(packet: BufferWritable) {
        //发走
        ConeConnection.now?.channelFuture?.channel()?.writeAndFlush(packet)?:let {
            ConeDialog.show(ConeDialogType.ERR,"连接服务器失败")
            ConeConnection.disconnect()
            goRdiTitleScreen()
            return
        }
    }

}

