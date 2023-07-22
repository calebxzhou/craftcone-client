package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.ReadablePacket
import calebxzhou.craftcone.net.protocol.S2CPacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,17:39.
 */
//登录响应
data class LoginS2CPacket(
    //是否登录成功
    val isSuccess: Boolean,
    //错误信息
    val msg: String,
) : S2CPacket {
    companion object : ReadablePacket{
        override fun read(buf: FriendlyByteBuf): LoginS2CPacket {
            //for client
            return LoginS2CPacket(buf.readBoolean(),buf.readUtf())
        }

    }

    override fun process() {
        TODO("Not yet implemented")
    }


}
