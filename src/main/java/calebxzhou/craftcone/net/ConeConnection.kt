package calebxzhou.craftcone.net

import calebxzhou.craftcone.logger
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
    val channelHandler: ConeNetReceiver,
    val address: InetSocketAddress
) {
    companion object {
        var now: ConeConnection? = null
        fun connect(address: InetSocketAddress) {
            logger.info("连接到$address")
            val handler = ConeNetReceiver()
            now = ConeConnection(
                Bootstrap().group(ConeNetSender.workGroup)
                    .channel(NioDatagramChannel::class.java)
                    .handler(object : ChannelInitializer<DatagramChannel>() {
                        override fun initChannel(ch: DatagramChannel) {
                            ch.pipeline()
                                .addLast(ConeNetEncoder())
                                .addLast(ConeNetDecoder())
                                .addLast(handler)
                        }

                    })
                    .connect(address.address, address.port).syncUninterruptibly(),
                handler,
                address
            )
            logger.info("连接完成 $address")
        }

        fun disconnect() {
            logger.info("断开连接")
            now?.channelFuture?.cancel(true)
            now = null
        }
    }
}