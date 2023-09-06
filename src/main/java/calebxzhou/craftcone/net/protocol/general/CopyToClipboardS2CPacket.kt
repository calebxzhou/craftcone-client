package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.coneInfoT
import calebxzhou.craftcone.utils.ByteBufUt.readUtf
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-08-30,8:45.
 */
data class CopyToClipboardS2CPacket(
    val content: String
) : Packet, RenderThreadProcessable {
    companion object : BufferReadable<CopyToClipboardS2CPacket> {
        override fun read(buf: ByteBuf) = CopyToClipboardS2CPacket(buf.readUtf())
    }

    override fun process() {
        logger.info("服务器将内容“$content”复制到了你的剪贴板。")
        Mc.copyClipboard(content)
    }
}
