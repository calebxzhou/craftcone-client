package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-12,9:57.
 */
//删除我的房间
class DelRoomC2SPacket() : Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
    }

}
