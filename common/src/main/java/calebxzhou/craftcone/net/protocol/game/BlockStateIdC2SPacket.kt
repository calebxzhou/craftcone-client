package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf
/**
 * Created  on 2023-07-17,17:16.
 */
data class BlockStateIdC2SPacket(
    //维度ID
    val dimId: Int,
    //区块位置 long
    val chunkPos: Long,
) : Packet, BufferWritable{

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeLong(chunkPos)
    }

}
