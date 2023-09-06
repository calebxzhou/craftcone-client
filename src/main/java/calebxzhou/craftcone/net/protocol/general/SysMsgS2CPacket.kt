package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.coneMsg
import calebxzhou.craftcone.utils.ByteBufUt.readUtf
import calebxzhou.craftcone.utils.ByteBufUt.readVarInt
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-08-13,20:50.
 */
data class SysMsgS2CPacket(
    val type: MsgType,
    val lvl: MsgLevel,
    val msg: String
) : Packet, RenderThreadProcessable {
    companion object : BufferReadable<SysMsgS2CPacket> {
        override fun read(buf: ByteBuf): SysMsgS2CPacket =
            SysMsgS2CPacket(MsgType[buf.readVarInt()], MsgLevel[buf.readVarInt()], buf.readUtf())

    }

    override fun process() {
        coneMsg(type, lvl, msg)
    }
}
