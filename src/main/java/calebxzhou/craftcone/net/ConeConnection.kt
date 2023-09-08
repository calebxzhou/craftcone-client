package calebxzhou.craftcone.net

import calebxzhou.craftcone.logger
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.codec.LineBasedFrameDecoder
import net.minecraft.network.Varint21FrameDecoder
import net.minecraft.network.Varint21LengthFieldPrepender
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
                    .channel(NioSocketChannel::class.java)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel) {
                            ch.pipeline()
                                .addLast("splitter", Varint21FrameDecoder())
                                .addLast("prepender", Varint21LengthFieldPrepender())
                                .addLast("decoder", ConeNetDecoder())
                                .addLast("packet_handler", ConeNetReceiver())
                                .addLast("encoder", ConeNetEncoder())
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
