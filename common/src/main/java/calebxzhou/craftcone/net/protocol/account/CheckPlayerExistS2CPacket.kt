package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.ReadablePacket
import calebxzhou.craftcone.net.protocol.S2CPacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-18,7:46.
 */
data class CheckPlayerExistS2CPacket(
    val exists: Boolean
): S2CPacket {
    companion object : ReadablePacket{
        override fun read(buf: FriendlyByteBuf): CheckPlayerExistS2CPacket {
            return CheckPlayerExistS2CPacket(buf.readBoolean())
        }

    }

    override fun process() {
        TODO("Not yet implemented")
    }

}
