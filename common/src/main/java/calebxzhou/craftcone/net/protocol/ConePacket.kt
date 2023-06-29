package calebxzhou.craftcone.net.protocol

import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-06-29,20:43.
 */
interface ConePacket {
    fun write(buf: FriendlyByteBuf)
    fun handle()

}