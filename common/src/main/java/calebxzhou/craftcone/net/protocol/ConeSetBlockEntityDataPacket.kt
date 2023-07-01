package calebxzhou.craftcone.net.protocol

import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-01,18:06.
 */
class ConeSetBlockEntityDataPacket : ConePacket {
    override fun write(buf: FriendlyByteBuf) {

    }

    override fun process() {

    }

    override fun checkSendCondition(): Boolean {
        return true
    }
}