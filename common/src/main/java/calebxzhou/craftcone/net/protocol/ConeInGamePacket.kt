package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.Cone
import calebxzhou.craftcone.LOG
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level

/**
 * Created  on 2023-06-29,20:43.
 */
//游戏内数据包
interface ConeInGamePacket : ConeProcessablePacket,ConeWritablePacket{
    companion object{
        const val PacketTypeNumber = 1
        fun read(buf: FriendlyByteBuf): ConeInGamePacket {
            throw NotImplementedError("必须使用实现类的read方法")
        }
        //根据维度编号取维度
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
        fun getDimIdByLevel(level: Level) : Int{
            return Cone.numDimKeyMap.filterValues { it == level.dimension() }.keys.first()
        }
    }


}