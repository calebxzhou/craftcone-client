package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.coneInfoT
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-08-30,8:45.
 */
class CloseScreenS2CPacket() : Packet, RenderThreadProcessable {
    companion object : BufferReadable<CloseScreenS2CPacket> {
        override fun read(buf: ByteBuf) = CloseScreenS2CPacket()
    }

    override fun process() {
        logger.info("服务器关闭了您当前的画面。")
        Mc.screen?.onClose()
    }
}
