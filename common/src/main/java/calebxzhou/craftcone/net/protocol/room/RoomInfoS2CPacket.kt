package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.S2CPacket
import java.util.*

/**
 * Created  on 2023-07-24,8:44.
 */
data class RoomInfoS2CPacket(
    //房间ID
    val rid:UUID,
    //房间名
    val rName:String,
    //mod加载器？Fabric：Forge
    val modLoader: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
    //地图种子
    val seed: Long,

) : S2CPacket{
    override fun process() {

    }

}
