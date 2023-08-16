package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-13,20:50.
 */
data class SysMsgPacket(
    val type:MsgType,
    val lvl: MsgLevel,
    val msg:String
): Packet,RenderThreadProcessable {
    companion object:BufferReadable<SysMsgPacket>{
        override fun read(buf: FriendlyByteBuf): SysMsgPacket {
            return SysMsgPacket(MsgType[buf.readVarInt()],MsgLevel[buf.readVarInt()],buf.readUtf())
        }

    }
    override fun process() {
       coneMsg(type,lvl,msg)
    }
}
