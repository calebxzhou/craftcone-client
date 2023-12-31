package calebxzhou.craftcone.net

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import net.minecraft.client.Minecraft

/**
 * Created  on 2023-08-26,11:20.
 */
object ConePacketProcessor {
    private val procScope = CoroutineScope(Dispatchers.Default)
    fun processPacket(packet: Packet) {
        logger.debug("Processing packet ${packet.javaClass.simpleName}")
        when (packet) {
            is RenderThreadProcessable -> Mc.renderThread {
                packet.process()
            }

            is InRoomProcessable -> ConeRoom.now?.let { room ->
                Mcl.logicThread { serv ->
                    packet.process(
                        serv,
                        room
                    )
                }
            }
        }
    }

}
