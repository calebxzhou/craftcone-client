package calebxzhou.craftcone.utils

import net.minecraft.core.BlockPos
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-08-01,19:52.
 */


fun chunkForEachBlock(level: Level,chunkPos: ChunkPos, doFor: (BlockPos, BlockState)->Unit){
    val x: Int = chunkPos.x shl 4
    val z: Int = chunkPos.z shl 4
    for (xx in x until x + 16) {
        for (zz in z until z + 16) {
            for (yy in 0 until level.maxBuildHeight) {
                val blockPos = BlockPos(xx, yy, zz)
                val blockState = level.getBlockState(blockPos)
                doFor.invoke(blockPos,blockState)
            }
        }
    }
}