package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.C2CPacket
import calebxzhou.craftcone.net.protocol.ReadablePacket
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-06,8:48.
 */
data class ChatC2CPacket (
    val senderName: String,
    val content: String,
): C2CPacket {


    companion object : ReadablePacket<ChatC2CPacket> {
        override fun read(buf: FriendlyByteBuf): ChatC2CPacket {
            return ChatC2CPacket(buf.readUtf(),buf.readUtf())
        }
    }

    override fun process() {
        val str = "<$senderName> $content"
        LOG.info(str)
        MC.gui.chat.addMessage(Component.literal(str))
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(senderName)
        buf.writeUtf(content)
    }


}