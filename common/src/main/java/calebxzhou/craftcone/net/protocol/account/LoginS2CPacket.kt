package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.ClientProcessable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.ResultPacket
import calebxzhou.craftcone.ui.screen.ConeLoginScreen
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,17:39.
 */
//登录响应
data class LoginS2CPacket(
    //是否登录成功
    override val ok: Boolean,
    //错误信息
    override val data: String,
) : Packet, ClientProcessable,ResultPacket {
    companion object : BufferReadable<LoginS2CPacket>{
        override fun read(buf: FriendlyByteBuf): LoginS2CPacket {
            //for client
            return LoginS2CPacket(buf.readBoolean(),buf.readUtf())
        }

    }

    override fun process() {
        val screen = MC.screen
        if(screen is ConeLoginScreen){
            screen.onResponse(this)
        }
    }


}
