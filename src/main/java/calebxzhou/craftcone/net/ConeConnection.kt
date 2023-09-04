package calebxzhou.craftcone.net

import calebxzhou.craftcone.logger
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import java.net.InetSocketAddress

/**
 * Created  on 2023-08-01,12:04.
 */
data class ConeConnection(
    val channelFuture: ChannelFuture,
    val address: InetSocketAddress
) {
    companion object {
        var now: ConeConnection? = null
        fun connect(address: InetSocketAddress) {
            logger.info("连接到$address")
            now = ConeConnection(
                Bootstrap()
                    .group(NioEventLoopGroup())
                    .channel(NioDatagramChannel::class.java)
                    .handler(object : ChannelInitializer<DatagramChannel>() {
                        override fun initChannel(ch: DatagramChannel) {
                            ch.pipeline()
                                .addLast(ConeNetEncoder())
                                .addLast(LengthFieldBasedFrameDecoder(65536,0,2,0,2))
                                .addLast(LengthFieldPrepender(2))
                                .addLast(ConeNetDecoder())
                                .addLast(ConeNetReceiver())
                        }

                    })
                    .connect(address.address, address.port).syncUninterruptibly(),
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
