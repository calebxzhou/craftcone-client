package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.ui.screen.ConeRegisterScreen
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,17:39.
 */
//响应
data class RegisterS2CPacket(
    //是否成功
    override val ok: Boolean,
    //错误信息
    override val data: String,
) : Packet, RenderThreadProcessable, ResultPacket {
    companion object : BufferReadable <RegisterS2CPacket>{
        override fun read(buf: FriendlyByteBuf): RegisterS2CPacket {
            return RegisterS2CPacket(buf.readBoolean(),buf.readUtf())
        }

    }
    override fun process() {

        val screen = MC.screen
        if(screen is ConeRegisterScreen){
            screen.onResponse(this)
        }
    }


}
