package calebxzhou.craftcone.utils

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-07-01,18:11.
 */
object LevelUtils {
    //设置方块的flag，
    // 1 will cause a block update. 2 will send the change to clients. 4 will prevent the block from being re-rendered. 8 will force any re-renders to run on the main thread instead 16 will prevent neighbor reactions (e.g. fences connecting, observers pulsing). 32 will prevent neighbor reactions from spawning drops. 64 will signify the block is being moved. Flags can be OR-ed
    const val DEFAULT_FLAG = 3
    // the default limit for the cascading block updates
    const val DEFAULT_RECURSION_LEFT = 512
    fun Level.setBlockDefault(blockPos: BlockPos, state: BlockState) : Boolean{
        return this.setBlock(blockPos, state, DEFAULT_FLAG, DEFAULT_RECURSION_LEFT)
    }
}