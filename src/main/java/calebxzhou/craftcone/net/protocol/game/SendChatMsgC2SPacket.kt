package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
data class SendChatMsgC2SPacket(
    val msg: String,
) : Packet, BufferWritable {

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(msg)
    }


}
