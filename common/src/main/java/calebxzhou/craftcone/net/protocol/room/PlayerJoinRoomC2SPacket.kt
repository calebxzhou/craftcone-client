package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf
import java.util.*

/**
 * Created  on 2023-07-06,8:48.
 */
//玩家请求加入房间
data class PlayerJoinRoomC2SPacket(
    val rid: UUID
): C2SPacket {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(rid)
    }


}