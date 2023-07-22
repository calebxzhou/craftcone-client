package calebxzhou.craftcone.utils

import calebxzhou.craftcone.Cone
import calebxzhou.craftcone.LOG
import calebxzhou.libertorch.MC
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-07-01,18:11.
 */
object LevelUt {
    //设置方块的flag，
    // 1 will cause a block update. 2 will send the change to clients. 4 will prevent the block from being re-rendered. 8 will force any re-renders to run on the main thread instead 16 will prevent neighbor reactions (e.g. fences connecting, observers pulsing). 32 will prevent neighbor reactions from spawning drops. 64 will signify the block is being moved. Flags can be OR-ed
    const val DEFAULT_FLAG = 1 or 2
    // the default limit for the cascading block updates
    const val DEFAULT_RECURSION_LEFT = 512
    fun ServerLevel.setBlockDefault(blockPos: BlockPos, state: BlockState)  {
        this.setBlock(blockPos, state, DEFAULT_FLAG, DEFAULT_RECURSION_LEFT)
    }
    @JvmStatic
    fun Level.dimensionName(): String {
        return this.dimension().location().toString()
    }
    //根据维度编号取维度
    @JvmStatic
    fun getLevelByDimId(dimId : Int) : Level {
        val dim = Cone.numDimKeyMap[dimId]?:run {
            //Cone.numDimKeyMap.forEach { (k, v) -> LOG.error("$k $v") }
            LOG.warn("找不到编号为${dimId}的维度。")
            Level.OVERWORLD
        }

        val level = MC?.singleplayerServer?.getLevel(dim) ?:run {
            throw IllegalStateException("处理数据包时，未在游玩状态！")
        }
        return level
    }
    //根据维度取维度编号
    @JvmStatic
    fun getDimIdByLevel(level: Level) : Int{
        return Cone.numDimKeyMap.filterValues { it == level.dimension() }.keys.first()
    }
}
