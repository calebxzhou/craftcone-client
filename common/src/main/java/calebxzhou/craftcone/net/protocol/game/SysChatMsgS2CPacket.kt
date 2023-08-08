package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-08-08,23:11.
 */
data class SysChatMsgS2CPacket(
    val msg : String
) :Packet,RenderThreadProcessable{
    companion object : BufferReadable<SysChatMsgS2CPacket>{
        override fun read(buf: FriendlyByteBuf): SysChatMsgS2CPacket {
            return SysChatMsgS2CPacket(buf.readUtf())
        }

    }

    override fun process() {
        MC.gui.chat.addMessage(Component.literal(msg))
        logger.info("系统消息 $msg")
    }

}
