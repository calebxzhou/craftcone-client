package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.net.ConeConnection
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.coneErr
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-15,8:53.
 */
class DisconnectByServerPacket:Packet,RenderThreadProcessable {
    companion object : BufferReadable<DisconnectByServerPacket>{
        override fun read(buf: FriendlyByteBuf): DisconnectByServerPacket {
            return DisconnectByServerPacket()
        }

    }
    override fun process() {
        ConeConnection.disconnect()
        coneErr("被服务器断开连接")

    }
}