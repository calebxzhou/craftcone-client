package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.ui.screen.ConeRoomCreateScreen
import calebxzhou.craftcone.utils.screenNow
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-01,19:18.
 */
data class PlayerCreateRoomS2CPacket(
    val ok:Boolean,
    val data: String,
) : Packet, RenderThreadProcessable{
    companion object:BufferReadable<PlayerCreateRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerCreateRoomS2CPacket {
            return PlayerCreateRoomS2CPacket(buf.readBoolean(),buf.readUtf())
        }

    }
    override fun process() {
        if(screenNow is ConeRoomCreateScreen){
            screenNow.onResponse(this)
        }
    }

}
