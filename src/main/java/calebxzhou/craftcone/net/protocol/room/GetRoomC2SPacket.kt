package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.ConeByteBuf.Companion.writeObjectId
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf
import org.bson.types.ObjectId

/**
 * Created  on 2023-08-13,15:24.
 */
//获取房间信息
data class GetRoomC2SPacket(
    //null == myRoom
    val rid: ObjectId
) : Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeObjectId(rid)
    }
}
