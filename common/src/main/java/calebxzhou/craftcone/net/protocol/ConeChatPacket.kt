package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.MC
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import java.util.UUID

/**
 * Created  on 2023-07-06,8:48.
 */
data class ConeChatPacket (
    val senderPid: UUID,
    val content: String,
): ConePacket {

    companion object{
        fun read(buf: FriendlyByteBuf): ConeChatPacket {
            return ConeChatPacket(buf.readUUID(),buf.readUtf())
        }
    }
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(senderPid)
        buf.writeUtf(content)
    }

    override fun process() {
        val str = "<$senderPid> $content"
        LOG.info(str)
        MC?.gui?.chat?.addMessage(Component.literal(str))
    }

    override fun checkSendCondition(): Boolean {
        return true
    }
}