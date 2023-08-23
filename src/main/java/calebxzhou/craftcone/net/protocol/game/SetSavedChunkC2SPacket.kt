package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeChunkPos
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-08-19,7:31.
 */
data class SetSavedChunkC2SPacket(
    val dimId: Int,
    val chunkPos: ConeChunkPos
) : Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeInt(chunkPos.asInt)
    }

}
