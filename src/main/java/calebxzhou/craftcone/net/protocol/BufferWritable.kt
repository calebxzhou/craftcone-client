package calebxzhou.craftcone.net.protocol

import io.netty.buffer.ByteBuf


/**
 * Created  on 2023-07-21,22:22.
 */
interface BufferWritable : Packet {
    //写数据进ByteBuf
    fun write(buf: ByteBuf)
}
