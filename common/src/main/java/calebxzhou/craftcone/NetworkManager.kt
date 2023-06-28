package calebxzhou.craftcone

import calebxzhou.craftcone.utils.NetworkUtils.toBigEndian
import io.netty.buffer.Unpooled
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.PacketListener
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.util.thread.NamedThreadFactory
import java.io.IOException
import java.net.Socket
import java.util.concurrent.Executors

/**
 * Created  on 2023-06-22,22:37.
 */
object NetworkManager {
    //网络线程池
    val thpool = Executors.newCachedThreadPool(NamedThreadFactory("CraftCone-Network"))
    var serverSocket: Socket = Socket("localhost", 19198)

    init {

        Receiver.start()
    }

    //发送数据包到cc服务端，使服务器广播此包到房间内所有人
    @JvmStatic
    fun sendPacket(packet: Packet<out PacketListener>) {
        if (PacketsToBroadcast.s2cNotSend.contains(packet.javaClass)) {
            println("不发送$packet")
            return
        }
        //获得数据包
        val buffer = FriendlyByteBuf(Unpooled.buffer())
        val packetId = ConnectionProtocol.PLAY.getPacketId(PacketFlow.CLIENTBOUND, packet) ?: return
        buffer.writeVarInt(packetId)
        packet.write(buffer)

        //发到服务器
        val data = buffer.array()

        try {
            thpool.submit {
                serverSocket.getOutputStream().write(data)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun reconnect() {
        thpool.execute {
            serverSocket = Socket("localhost", 19198)
        }
    }

    //从cc服务端接收数据
    object Receiver : Thread("CraftCone-Receiver") {
        private val buffer = ByteArray(16 * 1024 * 1024)
        override fun run() {
            while (true) {
                try {
                    val input = serverSocket.getInputStream()
                    var bytesRead = 0
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        val data = buffer.copyOf(bytesRead)
                        val fbuf = FriendlyByteBuf(Unpooled.wrappedBuffer(data))
                        //创建packet对象
                        val packetId = fbuf.readVarInt()
                        println("${serverSocket.remoteSocketAddress}传入${data.size}  包ID $packetId")
                        val packet = ConnectionProtocol.PLAY.createPacket(PacketFlow.CLIENTBOUND, packetId, fbuf)
                        println("packet: $packet")

                        //val addr = Minecraft.getInstance().connection?.connection?.remoteAddress
                        //Socket(addr as LocalAddress)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}