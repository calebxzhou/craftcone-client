package calebxzhou.craftcone.net.protocol

import net.minecraft.network.FriendlyByteBuf
import calebxzhou.craftcone.net.protocol.*


/**
 * Created  on 2023-07-21,22:22.
 */
interface BufferWritable : Packet{
    //写数据进FriendlyByteBuf
    fun write(buf: FriendlyByteBuf)
}