package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConeInGamePacket
import calebxzhou.craftcone.utils.LevelUt.dimensionName
import net.minecraft.core.BlockPos
import net.minecraft.nbt.NbtOps
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-07-17,17:16.
 */
//保存单个方块的包（setBlock）
data class ConeSaveBlockPacket(
    //维度
    val level: Level,
    //方块位置
    val bpos: BlockPos,
    //状态
    val state: BlockState?,
) : ConeInGamePacket{
    override fun process() {
        throw IllegalStateException("此包只能由cone server处理，client不应该处理")
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(level.dimensionName())
        buf.writeLong(bpos.asLong())
        val bState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, state)
        val stateTag = bState.get().left().get()
        LOG.info(stateTag.asString)
    }

}
