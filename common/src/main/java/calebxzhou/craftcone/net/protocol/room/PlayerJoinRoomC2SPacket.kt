package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf
import java.util.*

/**
 * Created  on 2023-07-06,8:48.
 */
data class PlayerJoinRoomC2SPacket(
    val roomId: UUID
): C2SPacket {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(roomId)
    }


}