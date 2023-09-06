package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.writeObjectId
import io.netty.buffer.ByteBuf
import org.bson.types.ObjectId

/**
 * Created  on 2023-07-06,8:48.
 */
//玩家请求加入房间
data class JoinRoomC2SPacket(
    val rid: ObjectId
) : Packet, BufferWritable {
    override fun write(buf: ByteBuf) {
        buf.writeObjectId(rid)
    }


}
