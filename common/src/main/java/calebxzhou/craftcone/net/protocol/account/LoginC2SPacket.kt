package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf
import calebxzhou.craftcone.net.protocol.*
import java.util.*

/**
 * Created  on 2023-07-13,17:27.
 */
//玩家登录请求
data class LoginC2SPacket(
    //玩家UUID
    val pid: UUID,
    //密码
    val pwd: String,
) : Packet, BufferWritable {

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(pid)
        buf.writeUtf(pwd)
    }

}