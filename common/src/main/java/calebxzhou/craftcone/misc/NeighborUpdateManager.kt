package calebxzhou.craftcone.misc

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.mixin.aMultiNeighborUpdate
import net.minecraft.core.BlockPos
import net.minecraft.world.level.redstone.CollectingNeighborUpdater
import net.minecraft.world.level.redstone.CollectingNeighborUpdater.ShapeUpdate

/**
 * Created  on 2023-07-15,22:14.
 */
//用来记录neighbor update(nupd)， 底下map里有的blockpos 不发送set block包给别的客户端
object NeighborUpdateManager {
    private val blockPosesInUpdate = hashSetOf<BlockPos>()

    //当mc添加neighbor update时
    @JvmStatic
    fun onAdd(updates: CollectingNeighborUpdater.NeighborUpdates){
        val bpos = when(updates){
            is ShapeUpdate -> {
                updates.pos
            }
            is CollectingNeighborUpdater.SimpleNeighborUpdate -> {
                updates.pos
            }
            is CollectingNeighborUpdater.MultiNeighborUpdate -> {
                (updates as aMultiNeighborUpdate).sourcePos
            }
            is CollectingNeighborUpdater.FullNeighborUpdate -> {
                updates.pos
            }
            else ->{
                LOG.error("无效的方块更新！ $updates")
                return
            }
        }
        blockPosesInUpdate += bpos
    }
    @JvmStatic
    fun has(bpos: BlockPos): Boolean {
        return blockPosesInUpdate.contains(bpos)
    }
    @JvmStatic
    //当mc清空nupd时
    fun onClear(){
        blockPosesInUpdate.clear()
    }
}