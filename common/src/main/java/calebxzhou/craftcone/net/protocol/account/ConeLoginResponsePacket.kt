package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.ConeOutGamePacket
import calebxzhou.craftcone.net.protocol.ConeReadablePacket
import calebxzhou.craftcone.net.protocol.ConeWritablePacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,17:39.
 */
//登录响应
data class ConeLoginResponsePacket(
    //是否登录成功
    val isSuccess: Boolean,
    //错误信息
    val msg: String,
) : ConeOutGamePacket{
    companion object : ConeReadablePacket{
        override fun read(buf: FriendlyByteBuf): ConeLoginResponsePacket {
            //for client
            return ConeLoginResponsePacket(buf.readBoolean(),buf.readUtf())
        }

    }

    override fun process() {
        //for client
    }

    override fun write(buf: FriendlyByteBuf) {
        //for server
        buf.writeBoolean(isSuccess)
        buf.writeUtf(msg)
    }


}
