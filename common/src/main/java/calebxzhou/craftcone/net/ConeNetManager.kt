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
import java.lang.IllegalArgumentException
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
        val handler = ConeClientChannelHandler(address)
        this.conn = ConeConnection(
            Bootstrap().group(workGroup)
                .channel(NioDatagramChannel::class.java)
                .handler(object : ChannelInitializer<DatagramChannel>() {
                    override fun initChannel(ch: DatagramChannel) {
                        ch.pipeline()
                            .addLast(handler)
                    }

                })
                .connect(address.address, address.port).syncUninterruptibly(), handler
        )
        LOG.info("连接完成 $address")
    }

    @JvmStatic
    fun sendPacket(packet: WritablePacket) {
        val data = FriendlyByteBuf(Unpooled.buffer())
        val packetType: Int
        val packetId = when (packet) {
            is ConeInGamePacket -> {
                packetType = ConeInGamePacket.PacketTypeNumber
                ConePacketSet.InGame.getPacketId(packet.javaClass)
            }

            is ConeOutGamePacket -> {
                packetType = ConeOutGamePacket.PacketTypeNumber
                ConePacketSet.OutGame.getPacketId(packet.javaClass)
            }

            else -> {
                throw IllegalArgumentException("cone packet必须是in game || out game")
            }
        } ?: let {
            LOG.error("找不到$packet 对应的包ID")
            return
        }
        //第1个byte，1st bit是包类型（ingame outgame)，剩下7bit是包ID
        val byte1 = (packetType shl 7) or packetId
        data.writeByte(byte1)
        //写入包数据
        packet.write(data)
        //发走
        val udpPacket = DatagramPacket(data, channelHandlerNow!!.serverAddr)
        channelFutureNow!!.channel().writeAndFlush(udpPacket)
        channelHandlerNow!!.packetCountTx++
    }

}

