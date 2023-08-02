package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-02,13:27.
 */
data class SetBlockStateC2SPacket(
    val id:Int,
    val bState:String
):C2SPacket {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(id)
        buf.writeUtf(bState)
    }

}