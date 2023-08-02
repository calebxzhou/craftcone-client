package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.ReadablePacket
import calebxzhou.craftcone.net.protocol.S2CPacket
import calebxzhou.craftcone.ui.screen.ConeRoomJoinScreen
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf
import java.util.*

/**
 * Created  on 2023-08-02,9:58.
 */
data class RoomInfoS2CPacket(
    //房间ID
    val rid: UUID,
    //房间名
    val rName: String,
    //房主ID
    val ownerUid: UUID,
    //mod加载器？Fabric：Forge
    val isFabric: Boolean,
    //创造
    val isCreative: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
    //地图种子
    val seed: Long,
):S2CPacket{
    companion object : ReadablePacket<RoomInfoS2CPacket>{

        override fun read(buf: FriendlyByteBuf): RoomInfoS2CPacket {
            return RoomInfoS2CPacket(
                buf.readUUID(),
                buf.readUtf(),
                buf.readUUID(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readInt(),
                buf.readLong()
            )
        }
    }
    override fun process() {
        val screen = MC.screen
        if(screen is ConeRoomJoinScreen){
            screen.onResponse(this)
        }
    }

}
