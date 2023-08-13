package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-18,7:41.
 */
//用户是否存在（已注册）
data class CheckPlayerExistC2SPacket(
    val pid: Int
): Packet, BufferWritable{
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(pid)
    }

}
