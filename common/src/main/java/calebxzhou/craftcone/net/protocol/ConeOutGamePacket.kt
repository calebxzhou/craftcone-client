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
interface ConeOutGamePacket : ConeProcessablePacket,ConeWritablePacket{
    companion object{
        const val PacketTypeNumber = 0
    }

}