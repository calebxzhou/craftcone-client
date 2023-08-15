package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
//玩家请求加入房间
data class JoinRoomC2SPacket(
    val rid: Int
): Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(rid)
    }


}