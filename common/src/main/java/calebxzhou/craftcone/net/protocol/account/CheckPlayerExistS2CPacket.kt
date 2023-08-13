package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.screen.ConeUidScreen
import calebxzhou.craftcone.utils.screenNow
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-18,7:46.
 */
data class CheckPlayerExistS2CPacket(
    val exists: Boolean
): Packet, RenderThreadProcessable {
    companion object : BufferReadable<CheckPlayerExistS2CPacket>{
        override fun read(buf: FriendlyByteBuf): CheckPlayerExistS2CPacket {
            return CheckPlayerExistS2CPacket(buf.readBoolean())
        }

    }

    override fun process() {
        if(screenNow is ConeUidScreen){
            screenNow.onResponse(this)
        }


    }

}
