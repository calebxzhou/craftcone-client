package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import net.minecraft.network.FriendlyByteBuf
/**
 * Created  on 2023-07-06,8:48.
 */
data class ChatC2CPacket (
    val senderName: String,
    val content: String,
): Packet,RenderThreadProcessable,BufferWritable {


    companion object : BufferReadable<ChatC2CPacket> {
        override fun read(buf: FriendlyByteBuf): ChatC2CPacket {
            return ChatC2CPacket(buf.readUtf(),buf.readUtf())
        }
    }

    override fun process() {
        val str = "<$senderName> $content"
        logger.info(str)
        Mc.InGame.addChatMsg(str)
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(senderName)
        buf.writeUtf(content)
    }


}