package calebxzhou.craftcone.utils

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-08-01,19:52.
 */

val blockStates
    get() = Block.BLOCK_STATE_REGISTRY
val blockStateAmount
    get() = Block.BLOCK_STATE_REGISTRY.size()
fun getBlockStateById(id:  Int): BlockState? {
    return blockStates.byId(id)
}