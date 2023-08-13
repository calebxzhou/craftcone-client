package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,17:27.
 */
//玩家登录请求
data class LoginC2SPacket(
    //玩家id
    val pid: Int,
    //密码
    val pwd: String,
) : Packet, BufferWritable {

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(pid)
        buf.writeUtf(pwd)
    }

}