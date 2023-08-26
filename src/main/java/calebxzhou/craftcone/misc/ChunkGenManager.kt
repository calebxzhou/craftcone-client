package calebxzhou.craftcone.misc

import calebxzhou.craftcone.logger
import net.minecraft.server.level.ChunkHolder
import net.minecraft.world.level.chunk.ChunkAccess

/**
 * Created  on 2023-08-26,17:17.
 */
object ChunkGenManager {
    //正在生成的区块
    private val generatingChunks = hashSetOf<ChunkAccess>()

    @JvmStatic
    fun addGeneratingChunk(chunkAccess: ChunkAccess) {
        generatingChunks += chunkAccess
    }

    @JvmStatic
    fun removeGeneratingChunk(chunkAccess: ChunkAccess) {
        generatingChunks -= chunkAccess
    }

    @JvmStatic
    fun isChunkGenerating(chunkAccess: ChunkAccess) = chunkAccess in generatingChunks

    fun clear() = generatingChunks.clear()
}
