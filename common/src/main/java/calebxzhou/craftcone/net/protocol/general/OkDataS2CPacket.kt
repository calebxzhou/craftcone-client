package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.screen.OkResponseScreen
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-15,8:47.
 */
data class OkDataS2CPacket(val data:FriendlyByteBuf): Packet,RenderThreadProcessable {
    companion object : BufferReadable<OkDataS2CPacket>{
        override fun read(buf: FriendlyByteBuf): OkDataS2CPacket {
            buf.retain()
            return OkDataS2CPacket(buf)
        }

    }

    override fun process() {
        when(val screen = Mc.screen){
            is OkResponseScreen -> screen.onOk(data)
        }
    }
}