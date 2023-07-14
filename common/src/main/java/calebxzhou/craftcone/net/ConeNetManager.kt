package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConeInGamePacket
import calebxzhou.craftcone.net.protocol.ConeOutGamePacket
import calebxzhou.craftcone.net.protocol.ConePacketSet
import calebxzhou.craftcone.net.protocol.ConeWritablePacket
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.util.thread.NamedThreadFactory
import java.lang.IllegalArgumentException
import java.net.InetSocketAddress
import java.util.concurrent.Executors


/**
 * Created  on 2023-06-22,22:37.
 */
//网络管理器
object ConeNetManager {
    //TODO 做容器同步
    //网络线程池
    val thpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), NamedThreadFactory("CraftCone-Network"))

    private val workGroup= NioEventLoopGroup()
    private var channelFutureNow : ChannelFuture? = null
    private var channelHandlerNow : ConeClientChannelHandler? = null

    fun connect(address: InetSocketAddress): ChannelFuture {
        LOG.info("连接到$address")
        channelHandlerNow = ConeClientChannelHandler(address)
        val b = Bootstrap().group(workGroup)
            .channel(NioDatagramChannel::class.java)
            .handler(object : ChannelInitializer<DatagramChannel>() {
                override fun initChannel(ch: DatagramChannel) {
                    ch.pipeline()
                        .addLast(channelHandlerNow)
                }

            })
            .connect(address.address, address.port).syncUninterruptibly()
        channelFutureNow = b
        LOG.info("连接完成")
        return b
    }

    @JvmStatic
    fun sendPacket(packet: ConeWritablePacket) {
        if(channelFutureNow==null  || channelHandlerNow == null){
            LOG.warn("未连接服务器就发包了")
            return
        }
        val data = FriendlyByteBuf(Unpooled.buffer())
        val packetType: Int
        val packetId = when (packet) {
            is ConeInGamePacket -> {
                packetType = 1
                ConePacketSet.InGame.getPacketId(packet.javaClass)
            }

            is ConeOutGamePacket -> {
                packetType = 0
                ConePacketSet.OutGame.getPacketId(packet.javaClass)
            }

            else -> {
                throw IllegalArgumentException("cone packet必须是in game || out game")
            }
        }?: let{
            LOG.error("找不到$packet 对应的包ID")
            return
        }
        //第1个byte，1st bit是包类型（ingame outgame)，剩下7bit是包ID
        val byte1 = (packetType shl 7) or packetId
        data.writeByte(byte1)
        //写入包数据
        packet.write(data)
        //发走
        val udpPacket = DatagramPacket(data,channelHandlerNow!!.serverAddr)
        channelFutureNow!!.channel().writeAndFlush(udpPacket)
        channelHandlerNow!!.packetCountTx++
    }

}

