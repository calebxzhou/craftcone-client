package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
//玩家离开房间（不包含数据）
class LeaveRoomC2SPacket  : Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
    }


}