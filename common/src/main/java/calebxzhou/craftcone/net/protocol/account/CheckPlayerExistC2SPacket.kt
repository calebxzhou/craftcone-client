package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf
import java.util.*

/**
 * Created  on 2023-07-18,7:41.
 */
//用户是否存在（已注册）
data class CheckPlayerExistC2SPacket(
    val pid: UUID
): C2SPacket{
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(pid)
    }

}