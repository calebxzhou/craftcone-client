package calebxzhou.craftcone.misc

import calebxzhou.craftcone.utils.LevelUt.dimensionName
import calebxzhou.libertorch.MCS
import io.netty.buffer.Unpooled
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.LevelResource
import java.nio.file.Files

/**
 * Created  on 2023-07-16,11:28.
 */
object BlockJournal {

    @JvmStatic
    fun write(level: Level, blockPos: BlockPos, blockState: BlockState){
        val chunk = level.getChunk(blockPos)
        //维度名称
        val dim = level.dimensionName()
        //区块坐标
        val chunkPos = chunk.pos

        val dic = MCS.getWorldPath(LevelResource.ROOT)
            .resolve("cone")
            .resolve("block_journal")
            .resolve(dim.replace(":","："))
            .resolve(chunkPos.toLong().toString(36))
        Files.createDirectories(dic)
        val chunkBlockPath = dic.resolve(blockPos.asLong().toString(36))
        val buf = FriendlyByteBuf(Unpooled.buffer())
        buf.writeId(Block.BLOCK_STATE_REGISTRY, blockState)
        val data = buf.array()
        Files.write(chunkBlockPath,data)
        buf.clear()
        //chunk.getBlockState()
    }

}