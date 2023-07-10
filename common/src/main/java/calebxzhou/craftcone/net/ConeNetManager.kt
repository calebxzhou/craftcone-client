package calebxzhou.craftcone.net

import calebxzhou.craftcone.Consts
import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConeChatPacket
import calebxzhou.craftcone.net.protocol.ConePacket
import calebxzhou.craftcone.net.protocol.ConeSetBlockPacket
import calebxzhou.rdi.RdiConsts
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
import java.net.InetSocketAddress
import java.util.concurrent.Executors


/**
 * Created  on 2023-06-22,22:37.
 */
//网络管理器
object ConeNetManager {
    //网络线程池
    val thpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), NamedThreadFactory("CraftCone-Network"))

    //包ID和constructor
    val packetCtors = arrayListOf< (FriendlyByteBuf) -> ConePacket>()

    //包class和Id的map
    val packetClassIdMap = linkedMapOf<Class<out ConePacket>, Int>()

    private val workGroup= NioEventLoopGroup()
    private var channelFutureNow : ChannelFuture? = null
    private var channelHandlerNow : ConeClientChannelHandler? = null
    init {
        val packetClassList = listOf(
            ConeSetBlockPacket::class.java,
            ConeChatPacket::class.java,
        )
        val packetList: List<(FriendlyByteBuf) -> ConePacket> = listOf(
            ConeSetBlockPacket::read,
            ConeChatPacket::read,
        )

        packetList.forEach {
            packetCtors +=  it
        }
        packetClassList.forEach {
            packetClassIdMap += Pair(it, packetClassIdMap.size)
        }

        if (packetList.size != packetClassList.size) {
            throw IllegalArgumentException("包ID和constructor的map&包class和Id的map size不一致")
        }
    }

    fun getPacketId(packetClass: Class<out ConePacket>): Int? {
        return packetClassIdMap[packetClass]
    }
    fun createPacket(packetId : Int,buf : FriendlyByteBuf): ConePacket? {
        return packetCtors[packetId]?.invoke(buf)
    }
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
    fun checkAndSendPacket(packet: ConePacket) {
        if(channelFutureNow==null  || channelHandlerNow == null){
            LOG.warn("未连接服务器就发包了")
            return
        }
        if(!packet.checkSendCondition())
            return
        val data = FriendlyByteBuf(Unpooled.buffer())
        val packetId = getPacketId(packet.javaClass) ?: let{
            LOG.error("找不到$packet 对应的包ID")
            return
        }
        data.writeByte(packetId)
        packet.write(data)
        val udpPacket = DatagramPacket(data,channelHandlerNow!!.serverAddr)
        channelFutureNow!!.channel().writeAndFlush(udpPacket)
        channelHandlerNow!!.packetCountTx++
    }

}

