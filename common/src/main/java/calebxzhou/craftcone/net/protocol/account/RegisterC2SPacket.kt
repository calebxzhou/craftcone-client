package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf
import java.util.*

/**
 * Created  on 2023-07-21,10:37.
 */
data class RegisterC2SPacket(
    val pid: UUID,
    val pwd : String,
): C2SPacket {

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(pid)
        buf.writeUtf(pwd)
    }

}
