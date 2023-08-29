package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.ConeByteBuf.Companion.writeObjectId
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf
import org.bson.types.ObjectId

/**
 * Created  on 2023-07-13,17:27.
 */
//玩家登录请求
data class LoginByNameC2SPacket(
    //玩家id
    val name: String,
    //密码
    val pwd: String,
) : Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(name)
        buf.writeUtf(pwd)
    }

}