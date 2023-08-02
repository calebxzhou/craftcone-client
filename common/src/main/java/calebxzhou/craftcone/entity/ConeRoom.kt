package calebxzhou.craftcone.entity

import java.util.*

/**
 * Created  on 2023-08-01,19:43.
 */
data class ConeRoom(
    //房间ID
    val rid: UUID,
    //房间名
    val rName: String,
    //房主ID
    val ownerUid: UUID,
    //mod加载器？Fabric：Forge
    val isFabric: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
    //地图种子
    val seed: Long,
) {
    companion object {
        var now: ConeRoom? = null
    }
}
object ConeRoomManager{
    fun loadRoom(rid: String,seed: Long){

    }
}