package calebxzhou.craftcone.model

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.ConeClientChannelHandler
import calebxzhou.craftcone.net.ConeNetManager
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress

/**
 * Created  on 2023-08-01,12:04.
 */
data class ConeConnection(
    val channelFuture: ChannelFuture,
    val channelHandler: ConeClientChannelHandler,
    val address: InetSocketAddress
){
    companion object{
        var now:ConeConnection? = null
        fun connect(address: InetSocketAddress) {
            LOG.info("连接到$address")
            val handler = ConeClientChannelHandler()
            now = ConeConnection(
                Bootstrap().group(ConeNetManager.workGroup)
                    .channel(NioDatagramChannel::class.java)
                    .handler(object : ChannelInitializer<DatagramChannel>() {
                        override fun initChannel(ch: DatagramChannel) {
                            ch.pipeline()
                                .addLast(handler)
                        }

                    })
                    .connect(address.address, address.port).syncUninterruptibly(),
                handler,
                address
            )
            LOG.info("连接完成 $address")
        }
        fun disconnect(){
            LOG.info("断开连接")
            now=null
        }
    }
}