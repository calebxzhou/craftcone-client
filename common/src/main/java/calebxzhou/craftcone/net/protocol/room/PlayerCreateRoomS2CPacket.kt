package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.S2CPacket
import calebxzhou.craftcone.ui.screen.ConeRoomCreateScreen
import calebxzhou.libertorch.MC
import java.util.*

/**
 * Created  on 2023-08-01,19:18.
 */
data class PlayerCreateRoomS2CPacket(
    val isSuccess:Boolean,
    val rid: UUID,
    val msg: String
) : S2CPacket{
    override fun process() {
        val screen = MC.screen
        if(screen is ConeRoomCreateScreen){
            screen.onResponse(this)
        }
    }

}
