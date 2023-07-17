package calebxzhou.craftcone.model

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-07-16,11:49.
 */
data class ConeBlockRecord(
    val blockPos: BlockPos,
    val blockState: BlockState
)
