package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.ReadablePacket
import calebxzhou.craftcone.net.protocol.S2CPacket
import calebxzhou.craftcone.ui.screen.ConeConnectScreen
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-18,7:46.
 */
data class CheckPlayerExistS2CPacket(
    val exists: Boolean
): S2CPacket {
    companion object : ReadablePacket<CheckPlayerExistS2CPacket>{
        override fun read(buf: FriendlyByteBuf): CheckPlayerExistS2CPacket {
            return CheckPlayerExistS2CPacket(buf.readBoolean())
        }

    }

    override fun process() {
        val screen = MC.screen
        if(screen is ConeConnectScreen){
            screen.onResponse(this)
        }


    }

}
