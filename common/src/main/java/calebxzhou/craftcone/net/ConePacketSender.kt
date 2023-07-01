package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConePacket
import io.netty.buffer.Unpooled
import net.minecraft.network.FriendlyByteBuf
import java.io.IOException

/**
 * Created  on 2023-06-29,20:49.
 */
object ConePacketSender {
    @JvmStatic
    fun checkAndSendPacket(packet: ConePacket) {
        if(!packet.checkSendCondition())
            return
        ConeNetManager.thpool.submit {
            if (!ConeNetManager.connected) {
                LOG.warn("未连接服务器就发包了")
                return@submit
            }
            val data = FriendlyByteBuf(Unpooled.buffer())
            val packetId = ConeNetManager.packetClassIdMap[packet.javaClass] ?: run {
                LOG.error("找不到包$packet 对应的ID！")
                return@submit
            }
            data.writeByte(packetId)
            packet.write(data)
            val bytes = data.array()

            try {
                ConeNetManager.serverSocket.getOutputStream().write(bytes)
            } catch (e: IOException) {
                LOG.error("发包错误：", e)
            }
        }

    }
}