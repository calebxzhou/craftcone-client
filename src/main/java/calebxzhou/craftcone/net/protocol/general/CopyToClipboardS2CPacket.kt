package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.coneInfoT
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-30,8:45.
 */
data class CopyToClipboardS2CPacket(
    val content: String
) : Packet, RenderThreadProcessable {
    companion object : BufferReadable<CopyToClipboardS2CPacket> {
        override fun read(buf: FriendlyByteBuf) = CopyToClipboardS2CPacket(buf.readUtf())
    }

    override fun process() {
        coneInfoT("服务器将内容“$content”复制到了你的剪贴板。")
        Mc.copyClipboard(content)
    }
}
