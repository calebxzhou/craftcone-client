package calebxzhou.craftcone.net.protocol

import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-06-29,20:46.
 */
data class ConeSetBlockPacket(val dimId: Int,val bpos:BlockPos,val state:BlockState) : ConePacket{
    override fun write(buf: FriendlyByteBuf) {
        buf.writeByte(dimId)
        buf.writeBlockPos(bpos)
        buf.writeId(Block.BLOCK_STATE_REGISTRY,state)
    }

    override fun handle() {
        TODO("Not yet implemented")
    }

}
