package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.net.ConeConnection
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.coneErrD
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-15,8:53.
 */
class DisconnectS2CPacket : Packet, RenderThreadProcessable {
    companion object : BufferReadable<DisconnectS2CPacket> {
        override fun read(buf: FriendlyByteBuf): DisconnectS2CPacket {
            return DisconnectS2CPacket()
        }

    }

    override fun process() {
        ConeConnection.disconnect()
        coneErrD("被服务器断开连接")

    }
}
