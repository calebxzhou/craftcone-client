package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.net.protocol.account.ConeLoginRequestPacket
import calebxzhou.craftcone.net.protocol.account.ConeLoginResponsePacket
import calebxzhou.craftcone.net.protocol.game.ConeChatPacket
import calebxzhou.craftcone.net.protocol.game.ConePlayerJoinPacket
import calebxzhou.craftcone.net.protocol.game.ConePlayerQuitPacket
import calebxzhou.craftcone.net.protocol.game.ConeSetBlockPacket
import net.minecraft.network.FriendlyByteBuf
import java.lang.IllegalArgumentException

/**
 * Created  on 2023-07-14,8:55.
 */
object ConePacketSet {
    object InGame{
        fun getPacketId(packetClass: Class<out ConeInGamePacket>): Int? {
            return classToId[packetClass]
        }
        fun createPacket(packetId : Int, data : FriendlyByteBuf): ConeInGamePacket {
            return idToReader[packetId].invoke(data)
        }
        init {
            addPackets(
                Pair(ConeSetBlockPacket::class.java,ConeSetBlockPacket::read),
                Pair(ConeChatPacket::class.java,ConeChatPacket::read),
                Pair(ConePlayerJoinPacket::class.java,ConePlayerJoinPacket::read),
                Pair(ConePlayerQuitPacket::class.java,ConePlayerQuitPacket::read),
            )
        }
        private val classToId = linkedMapOf<Class<out ConeInGamePacket>, Int>()
        private val idToReader = arrayListOf<(FriendlyByteBuf) -> ConeInGamePacket>()
        private fun addPackets(vararg packetClassAndReader: Pair<Class<out ConeInGamePacket>,(FriendlyByteBuf) -> ConeInGamePacket>){
            packetClassAndReader.forEach {
                val packetClass = it.first
                val packetReader = it.second
                addPacket(packetClass,packetReader)
            }
        }
        private fun addPacket(packetClass: Class<out ConeInGamePacket>, packetReader : (FriendlyByteBuf) -> ConeInGamePacket){
            val size = idToReader.size
            val id = classToId.put( packetClass,size)
            if(id != -1){
                throw IllegalArgumentException("$packetClass 已经注册ID为$id")
            }
            idToReader += packetReader
        }
    }

    object OutGame{
        fun getPacketId(packetClass: Class<out ConeOutGamePacket>): Int? {
            return classToId[packetClass]
        }
        fun createPacket(packetId : Int, data : FriendlyByteBuf): ConeOutGamePacket {
            return idToReader[packetId].invoke(data)
        }
        init {
            addPackets(
                Pair(ConeLoginRequestPacket::class.java,ConeLoginRequestPacket::read),
                Pair(ConeLoginResponsePacket::class.java,ConeLoginResponsePacket::read),
            )
        }
        private val classToId = linkedMapOf<Class<out ConeOutGamePacket>, Int>()
        private val idToReader = arrayListOf<(FriendlyByteBuf) -> ConeOutGamePacket>()
        private fun addPackets(vararg packetClassAndReader: Pair<Class<out ConeOutGamePacket>,(FriendlyByteBuf) -> ConeOutGamePacket>){
            packetClassAndReader.forEach {
                val packetClass = it.first
                val packetReader = it.second
                addPacket(packetClass,packetReader)
            }
        }
        private fun addPacket(packetClass: Class<out ConeOutGamePacket>, packetReader : (FriendlyByteBuf) -> ConeOutGamePacket){
            val size = idToReader.size
            val id = classToId.put( packetClass,size)
            if(id != -1){
                throw IllegalArgumentException("$packetClass 已经注册ID为$id")
            }
            idToReader += packetReader
        }
        
    }
    

}