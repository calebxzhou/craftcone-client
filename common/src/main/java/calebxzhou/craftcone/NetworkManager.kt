package calebxzhou.craftcone

import calebxzhou.craftcone.utils.ReflectUtils.fieldsNameValueMap
import io.netty.buffer.Unpooled
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.PacketListener
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket
import net.minecraft.util.thread.NamedThreadFactory
import net.minecraft.world.entity.player.Player
import java.io.IOException
import java.net.Socket
import java.net.SocketException
import java.util.concurrent.Executors

/**
 * Created  on 2023-06-22,22:37.
 */
object NetworkManager {
    //网络线程池
    val thpool = Executors.newCachedThreadPool(NamedThreadFactory("CraftCone-Network"))
    var serverSocket: Socket
    var connected = false
    init {
        serverSocket= Socket("localhost", 19198)
        connected=true
        Receiver.start()
    }

    //发送数据包到cc服务端，使服务器广播此包到房间内所有人
    @JvmStatic
    fun sendPacket(packet: Packet<out PacketListener>) {
        if(!connected)
            return
        if (!PacketsToBroadcast.s2cSend.contains(packet.javaClass) && !PacketsToBroadcast.c2sSend.contains(packet.javaClass)) {
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
            connected = true
        }
    }

    //从cc服务端接收数据
    object Receiver : Thread("CraftCone-Receiver") {
        private val buffer = ByteArray(16 * 1024 * 1024)

        override fun run() {
            while (true) {
                if(!connected)
                    continue
                try {
                    receivePacket()
                }catch (e: SocketException){
                    connected = false
                    LOG.error("断开了与cc服务器的连接，原因：${e.localizedMessage}")
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        private fun receivePacket(){
            val input = serverSocket.getInputStream()
            var bytesRead = 0
            while (input.read(buffer).also { bytesRead = it } != -1) {
                val data = buffer.copyOf(bytesRead)
                val fbuf = FriendlyByteBuf(Unpooled.wrappedBuffer(data))
                val localConn = MC.connection
                if(localConn != null){
                    //包的第一个varint是id
                    val packetId = fbuf.readVarInt()
                    //创建packet对象
                    val packet  =
                        ConnectionProtocol.PLAY.createPacket(PacketFlow.CLIENTBOUND, packetId, fbuf) as Packet<ClientGamePacketListener>

                    //让mc处理这个包
                    MC.submit {
                        processPacket(localConn,packet,)
                    }

                }

            }
        }

        private fun processPacket(localConn: ClientPacketListener, packet: Packet<ClientGamePacketListener>) {
            //debug
            if(!PacketsToBroadcast.debug_PacketsNotPrint.contains(packet.javaClass)){
                val packetInfo = "${packet.javaClass.simpleName} ${packet.fieldsNameValueMap()}"
                LOG.info("packetIn: $packetInfo")
            }
            //debug

            var processRequired = true
            if(packet is ClientboundMoveEntityPacket){
                MC.level?.let { level ->
                    packet.getEntity(level)?.let { entity->
                        //不移动自己
                        if(entity is Player){
                            if(entity.isLocalPlayer){
                                processRequired = false
                            }
                        }
                    }
                }

            }
            if(packet is ClientboundContainerSetContentPacket){
                //不广播自己背包
                if (packet.containerId == 0) {
                    processRequired=false
                }
            }
            if(packet is ClientboundContainerSetDataPacket){
                //不广播自己背包
                if (packet.containerId == 0) {
                    processRequired=false
                }
            }
            if(packet is ClientboundContainerSetSlotPacket){
                //不广播自己背包
                if (packet.containerId == 0) {
                    processRequired=false
                }
            }

            try {
                if(processRequired)
                    packet.handle(localConn)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}

