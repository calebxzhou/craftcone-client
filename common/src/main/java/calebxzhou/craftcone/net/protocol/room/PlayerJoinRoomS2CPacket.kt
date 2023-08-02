package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.ReadablePacket
import calebxzhou.craftcone.net.protocol.S2CPacket
import calebxzhou.craftcone.ui.screen.ConeRoomJoinScreen
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
//加入房间响应（房间信息）
data class PlayerJoinRoomS2CPacket(
    //方块状态数量
    val blockStateAmount: Int,
    //地图种子
    val seed: Long,
) : S2CPacket {
    companion object : ReadablePacket<PlayerJoinRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerJoinRoomS2CPacket {
            return PlayerJoinRoomS2CPacket(buf.readInt(),buf.readLong())
        }

    }
    override fun process() {
        val screen = MC.screen
        if(screen is ConeRoomJoinScreen){
            screen.onResponse(this)
        }
    }


}

//   MC.gui.chat.addMessage(Component.literal("$pName 加入了房间").withStyle(ChatFormatting.YELLOW))