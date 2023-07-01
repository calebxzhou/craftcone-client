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
        if(!packet.checkSendCondition()){
            LOG.info("包$packet 不符合发送条件")
            return
        }

        if (!ConeNetManager.connected) {
            LOG.warn("未连接服务器就发包了")
            return
        }
        val data = FriendlyByteBuf(Unpooled.buffer())
        val packetId = ConeNetManager.packetClassIdMap[packet.javaClass] ?: run {
            LOG.error("找不到包$packet 对应的ID！")
            return
        }

        data.writeByte(packetId)
        packet.write(data)

        val length = data.writerIndex()
        val newData = FriendlyByteBuf(Unpooled.buffer())
        //写入长度，防止粘包
        newData.writeVarInt(length)
        newData.writeBytes(data.array())
        ConeNetManager.thpool.submit {
            try {
                val out = ConeNetManager.serverSocket.getOutputStream()
                val arr = newData.array()
                //println(arr.joinToString { String.format("%02X",it) })
                out.write(arr)
            } catch (e: IOException) {
                LOG.error("发包错误：", e)
            }
        }

    }
}