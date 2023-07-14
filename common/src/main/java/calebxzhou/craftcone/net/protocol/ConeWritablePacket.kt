package calebxzhou.craftcone.net.protocol

import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,17:27.
 */
//通用数据包
interface ConeWritablePacket {
    //写数据进FriendlyByteBuf
    fun write(buf: FriendlyByteBuf)
}