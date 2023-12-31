package calebxzhou.craftcone.test

import calebxzhou.craftcone.DEFAULT_PORT
import calebxzhou.craftcone.entity.ConeChunkPos
import calebxzhou.craftcone.net.ConeConnection
import calebxzhou.craftcone.net.ConeNetSender.sendPacket
import calebxzhou.craftcone.net.protocol.account.LoginByNameC2SPacket
import calebxzhou.craftcone.net.protocol.game.GetChunkC2SPacket
import calebxzhou.craftcone.net.protocol.game.SetBlockC2SPacket
import calebxzhou.craftcone.net.protocol.room.JoinRoomC2SPacket
import net.minecraft.core.BlockPos
import org.bson.types.ObjectId
import java.lang.Thread.sleep
import java.net.InetSocketAddress

/**
 * Created  on 2023-08-29,22:50.
 */
//TODO 测试网络性能 eg 插入1000000个方块  2.删除房间时一起删除本地的存档

fun main() {
    ConeConnection.connect(InetSocketAddress("localhost", DEFAULT_PORT))
    sleep(1000)
    sendPacket(LoginByNameC2SPacket("davickk", "123"))
    Thread.sleep(1000)
    sendPacket(JoinRoomC2SPacket(ObjectId("64f07fed12698d58d364d256")))
    //write512x512blocks()
}
fun write512x512blocks(){

    val letters = ('a'..'z') + ('A'..'Z')
    val t1 = System.currentTimeMillis()
    for(x in -256..256){
        for(z in -256 .. 256){
            val y=64
            sendPacket(SetBlockC2SPacket(0, BlockPos(x, y, z), 1, (1..2000)
                .map { letters.random() }
                .joinToString(separator = "")))
        }
    }
    val t2 = System.currentTimeMillis()
    println()
    println("写512x512方块用时")
    println(t2-t1)
}
fun read32x32chunks(){
    for(x in -16 .. 16){
        for (z in -16 .. 16){
            sendPacket(GetChunkC2SPacket(0, ConeChunkPos(x,z)))
        }
    }
}
