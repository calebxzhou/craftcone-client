package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.LOG
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import java.util.UUID

/**
 * Created  on 2023-07-06,8:48.
 */
data class ConeChatPacket (
    val senderName: String,
    val content: String,
): ConePacket {

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

    override fun checkSendCondition(): Boolean {
        return true
    }
}