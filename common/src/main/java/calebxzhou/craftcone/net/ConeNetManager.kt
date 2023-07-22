package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConePacketSet
import calebxzhou.craftcone.net.protocol.WritablePacket
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import net.minecraft.network.FriendlyByteBuf
import java.net.InetSocketAddress


/**
 * Created  on 2023-06-22,22:37.
 */
//网络管理器
object ConeNetManager {
    //TODO 做容器同步

    private val workGroup = NioEventLoopGroup()
    private var conn: ConeConnection? = null
    val serverConnection
        get() = conn?:let {
            throw IllegalStateException("服务器连接为null，禁止发送数据包")
        }
    fun connect(address: InetSocketAddress) {
        LOG.info("连接到$address")
        val handler = ConeClientChannelHandler()
        this.conn = ConeConnection(
            Bootstrap().group(workGroup)
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
        val udpPacket = DatagramPacket(data, serverConnection.address)
        serverConnection.channelFuture.channel().writeAndFlush(udpPacket)
        serverConnection.channelHandler.packetCountTx++
    }

}

