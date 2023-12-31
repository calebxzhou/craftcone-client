package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeChunkPos
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.writeVarInt
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-07-17,17:16.
 */
data class GetChunkC2SPacket(
    //维度ID
    val dimId: Int,
    //区块位置 long
    val chunkPos: ConeChunkPos,
) : Packet, BufferWritable {

    override fun write(buf: ByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeInt(chunkPos.asInt)
    }

}
