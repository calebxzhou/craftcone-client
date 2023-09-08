package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.screen.OkResponseScreen
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Created  on 2023-08-15,8:47.
 */
data class OkDataS2CPacket(val data: ByteBuf) : Packet, RenderThreadProcessable {
    companion object : BufferReadable<OkDataS2CPacket> {
        override fun read(buf: ByteBuf): OkDataS2CPacket {
            return OkDataS2CPacket(buf.readBytes(buf.readableBytes()))
        }

    }

    override fun process() {
        if (!Mc.isStarted) {
            logger.info("Received Ok Data: Size ${data.readableBytes()}")
            return
        }
        when (val screen = Mc.screen) {
            is OkResponseScreen -> screen.onOk(data)
        }
    }
}
