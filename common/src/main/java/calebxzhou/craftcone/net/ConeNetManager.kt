package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.entity.ConeConnection
import calebxzhou.craftcone.net.protocol.ConePacketSet
import calebxzhou.craftcone.net.protocol.WritablePacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.libertorch.MC
import calebxzhou.rdi.ui.RdiTitleScreen
import io.netty.buffer.Unpooled
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramPacket
import net.minecraft.network.FriendlyByteBuf


/**
 * Created  on 2023-06-22,22:37.
 */
//网络管理器
object ConeNetManager {

    //TODO 做容器同步

    val workGroup = NioEventLoopGroup()



    @JvmStatic
    fun sendPacket(packet: WritablePacket) {
        val data = FriendlyByteBuf(Unpooled.buffer())
        val packetId = ConePacketSet.getPacketId(packet.javaClass) ?: let {
            LOG.error("找不到$packet 对应的包ID")
            return
        }
        data.writeByte(packetId)
        packet.write(data)
        //发走
        val address = ConeConnection.now?.address?:let {
            ConeDialog.show(ConeDialogType.ERR,"没连接服务器就发包了")
            ConeConnection.disconnect()
            MC.setScreen(RdiTitleScreen())
            return
        }
        val udpPacket = DatagramPacket(data, address)
        ConeConnection.now?.channelFuture?.channel()?.writeAndFlush(udpPacket)?:let {
            ConeDialog.show(ConeDialogType.ERR,"没连接服务器就发包了")
            ConeConnection.disconnect()
            MC.setScreen(RdiTitleScreen())
            return
        }
    }

}

