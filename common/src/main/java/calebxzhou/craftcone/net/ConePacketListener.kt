package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.PacketsToBroadcast
import calebxzhou.craftcone.utils.ReflectUtils.fieldsNameValueMap
import io.netty.buffer.Unpooled
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.*
import java.net.SocketException

/**
 * Created  on 2023-06-29,20:44.
 */
object ConePacketListener : Thread("CraftCone-Listener") {
    private val buffer = ByteArray(16 * 1024 * 1024)
    override fun run() {
        while (true) {
            if(!ConeNetManager.connected)
                continue
            try {
                receivePacket()
            }catch (e: SocketException){
                ConeNetManager.connected = false
                LOG.error("断开了与cc服务器的连接，原因：${e.localizedMessage}")
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun receivePacket(){
        val input = ConeNetManager.serverSocket.getInputStream()
        var bytesRead = 0
        while (input.read(buffer).also { bytesRead = it } != -1) {
            val data = buffer.copyOf(bytesRead)

            val fbuf = FriendlyByteBuf(Unpooled.wrappedBuffer(data))
            //包的1st byte是id
            val packetId = fbuf.readByte().toInt()
            val packet = ConeNetManager.packetIdCtorMap[packetId]?.invoke(fbuf)?: run{
                LOG.error("找不到包$packetId 对应的ctor")
                return

            }
            LOG.info("收到包 $packet")
            packet.process()
        }
    }

    private fun processPacket(localConn: ClientPacketListener, packet: Packet<ClientGamePacketListener>) {
        //debug
        if(!PacketsToBroadcast.debug_PacketsNotPrint.contains(packet.javaClass)){
            val packetInfo = "${packet.javaClass.simpleName} ${packet.fieldsNameValueMap()}"
            LOG.info("packetIn: $packetInfo")
        }

    }

}