package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.writeObjectId
import calebxzhou.craftcone.utils.ByteBufUt.writeUtf
import io.netty.buffer.ByteBuf
import org.bson.types.ObjectId

/**
 * Created  on 2023-07-13,17:27.
 */
//玩家登录请求
data class LoginByUidC2SPacket(
    //玩家id
    val uid: ObjectId,
    //密码
    val pwd: String,
) : Packet, BufferWritable {

    override fun write(buf: ByteBuf) {
        buf.writeObjectId(uid)
        buf.writeUtf(pwd)
    }

}
