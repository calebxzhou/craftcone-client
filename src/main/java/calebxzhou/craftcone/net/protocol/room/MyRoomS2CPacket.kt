package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.ConeByteBuf.Companion.readObjectId
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.network.FriendlyByteBuf
import org.bson.types.ObjectId

/**
 * Created  on 2023-08-30,8:37.
 */
data class MyRoomS2CPacket(
    val rid: ObjectId
) : Packet, RenderThreadProcessable {
    companion object : BufferReadable<MyRoomS2CPacket> {
        override fun read(buf: FriendlyByteBuf) = MyRoomS2CPacket(buf.readObjectId())

    }

    override fun process() {
        val hs = rid.toHexString()
        coneMsg(MsgType.Dialog, MsgLevel.Info, "您的房间ID：$hs")

    }
}
