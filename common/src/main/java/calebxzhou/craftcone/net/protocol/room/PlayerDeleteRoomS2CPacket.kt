package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.net.protocol.ResultPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-12,9:56.
 */
data class PlayerDeleteRoomS2CPacket(
    override val ok: Boolean,
    //成功rid 失败信息
    override val data: String,
): Packet,ResultPacket,RenderThreadProcessable{
    companion object : BufferReadable<PlayerDeleteRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerDeleteRoomS2CPacket {
            return PlayerDeleteRoomS2CPacket(buf.readBoolean(),buf.readUtf())
        }
    }

    override fun process() {
        ConeDialog.show(ConeDialogType.OK,"成功删除房间")
    }
}
