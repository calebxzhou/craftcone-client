package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConeInGamePacket
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-06,8:48.
 */
data class ConeChatPacket (
    val senderName: String,
    val content: String,
): ConeInGamePacket {

    companion object{
        fun read(buf: FriendlyByteBuf): ConeChatPacket {
            return ConeChatPacket(buf.readUtf(),buf.readUtf())
        }
    }
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(senderName)
        buf.writeUtf(content)
    }

    override fun process() {
        val str = "<$senderName> $content"
        LOG.info(str)
        MC.gui.chat.addMessage(Component.literal(str))
    }

}