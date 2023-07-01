package calebxzhou.craftcone.net

import calebxzhou.craftcone.PacketsToBroadcast
import calebxzhou.craftcone.net.protocol.ConeExplodePacket
import calebxzhou.craftcone.net.protocol.ConePacket
import calebxzhou.craftcone.net.protocol.ConeSetBlockPacket
import calebxzhou.craftcone.net.protocol.ConeSetSectionPacket
import io.netty.buffer.Unpooled
import net.minecraft.network.ConnectionProtocol
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.PacketListener
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.PacketFlow
import net.minecraft.util.thread.NamedThreadFactory
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.Socket
import java.util.concurrent.Executors

/**
 * Created  on 2023-06-22,22:37.
 */
//网络管理器
object ConeNetManager {
    //网络线程池
    val thpool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), NamedThreadFactory("CraftCone-Network"))
    var serverSocket: Socket
    var connected = false

    //包ID和constructor的map
    val packetIdCtorMap = linkedMapOf<Int, (FriendlyByteBuf) -> ConePacket>()

    //包class和Id的map
    val packetClassIdMap = linkedMapOf<Class<out ConePacket>, Int>()

    init {
        serverSocket = Socket("localhost", 19198)
        connected = true
        val packetClassList = listOf(
            ConeSetBlockPacket::class.java,
            ConeSetSectionPacket::class.java,
        )
        val packetList: List<(FriendlyByteBuf) -> ConePacket> = listOf(
            ConeSetBlockPacket::read,
            ConeSetSectionPacket::read,
        )

        packetList.forEach {
            packetIdCtorMap += Pair(packetIdCtorMap.size, it)
        }
        packetClassList.forEach {
            packetClassIdMap += Pair(it, packetClassIdMap.size)
        }
        if (packetList.size != packetClassList.size) {
            throw IllegalArgumentException("包ID和constructor的map&包class和Id的map size不一致")
        }
    }


    @JvmStatic
    fun reconnect() {
        thpool.execute {
            serverSocket = Socket("localhost", 19198)
            connected = true
        }
    }

}

