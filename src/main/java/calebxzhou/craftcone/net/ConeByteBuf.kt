package calebxzhou.craftcone.net

import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf
import org.bson.types.ObjectId

class ConeByteBuf(source: ByteBuf) : FriendlyByteBuf(source) {
    companion object{

        fun FriendlyByteBuf.writeObjectId(objectId: ObjectId):FriendlyByteBuf{
            writeBytes(objectId.toByteArray())
            return this
        }

        fun FriendlyByteBuf.readObjectId(): ObjectId = ObjectId(readBytes(12).nioBuffer())
    }
}
