package calebxzhou.craftcone.net.protocol

import net.minecraft.network.FriendlyByteBuf


/**
 * Created  on 2023-07-13,17:27.
 */
//通用数据包
interface ReadablePacket<T: S2CPacket> : Packet{
    // 读取 数据
    fun read(buf: FriendlyByteBuf) : T
}