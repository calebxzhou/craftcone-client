package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf
/**
 * Created  on 2023-07-17,17:16.
 */
data class ReadBlockC2SPacket(
    //维度ID
    val dimId: Int,
    //方块位置
    val bpos: Long,
) : Packet, BufferWritable{

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeLong(bpos)
    }

}
