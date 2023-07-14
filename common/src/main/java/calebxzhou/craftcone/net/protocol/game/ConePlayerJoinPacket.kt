package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.ConeInGamePacket
import calebxzhou.libertorch.MC
import net.minecraft.ChatFormatting
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import java.util.UUID

/**
 * Created  on 2023-07-06,8:48.
 */
data class ConePlayerJoinPacket (
    val pid: UUID,
    val pName: String,
): ConeInGamePacket {

    companion object{
        fun read(buf: FriendlyByteBuf): ConePlayerJoinPacket {
            return ConePlayerJoinPacket(buf.readUUID(),buf.readUtf())
        }
    }
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(pid)
        buf.writeUtf(pName)
    }

    override fun process() {
        MC.gui.chat.addMessage(Component.literal("$pName 加入了房间").withStyle(ChatFormatting.YELLOW))

    }


}