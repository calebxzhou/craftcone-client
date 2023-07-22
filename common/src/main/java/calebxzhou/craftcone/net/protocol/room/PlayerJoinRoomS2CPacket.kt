package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.S2CPacket
import calebxzhou.libertorch.MC
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import java.util.*

/**
 * Created  on 2023-07-06,8:48.
 */
data class PlayerJoinRoomS2CPacket(
    val pid: UUID,
    val pName :String,
): S2CPacket {
    override fun process() {
        MC.gui.chat.addMessage(Component.literal("$pName 加入了房间").withStyle(ChatFormatting.YELLOW))
    }


}