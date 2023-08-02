package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.ReadablePacket
import calebxzhou.craftcone.net.protocol.S2CPacket
import calebxzhou.craftcone.ui.screen.ConeRoomCreateScreen
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-01,19:18.
 */
data class PlayerCreateRoomS2CPacket(
    val isSuccess:Boolean,
    val data: String,
) : S2CPacket{
    companion object:ReadablePacket<PlayerCreateRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerCreateRoomS2CPacket {
            return PlayerCreateRoomS2CPacket(buf.readBoolean(),buf.readUtf())
        }

    }
    override fun process() {
        val screen = MC.screen
        if(screen is ConeRoomCreateScreen){
            screen.onResponse(this)
        }
    }

}
