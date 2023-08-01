package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf
import java.util.*

/**
 * Created  on 2023-07-06,8:48.
 */
//玩家请求创建房间
data class PlayerCreateRoomC2SPacket(
    val uid: UUID,
    val isCreative: Boolean
): C2SPacket {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(uid)
        buf.writeBoolean(isCreative)
    }


}