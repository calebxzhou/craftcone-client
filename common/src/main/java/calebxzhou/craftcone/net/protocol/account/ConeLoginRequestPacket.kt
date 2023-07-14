package calebxzhou.craftcone.net.protocol.account

import calebxzhou.craftcone.net.protocol.ConeOutGamePacket
import calebxzhou.craftcone.net.protocol.ConeReadablePacket
import calebxzhou.craftcone.net.protocol.ConeWritablePacket
import net.minecraft.network.FriendlyByteBuf
import java.util.*

/**
 * Created  on 2023-07-13,17:27.
 */
//玩家登录请求
data class ConeLoginRequestPacket(
    //玩家UUID
    val pid: UUID,
    //密码
    val pwd: String,
) : ConeOutGamePacket{
    companion object : ConeReadablePacket{
        override fun read(buf: FriendlyByteBuf): ConeLoginRequestPacket {
            //for server
            return ConeLoginRequestPacket(buf.readUUID(),buf.readUtf())
        }

    }
    override fun process() {
        //for server
    }

    override fun write(buf: FriendlyByteBuf){
        //for client
        buf.writeUUID(pid)
        buf.writeUtf(pwd)
    }

}