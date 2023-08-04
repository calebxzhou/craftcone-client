package calebxzhou.craftcone.net

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.ClientProcessable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.account.*
import calebxzhou.craftcone.net.protocol.game.*
import calebxzhou.craftcone.net.protocol.room.*
import calebxzhou.libertorch.MC
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-14,8:55.
 */
object ConePacketSet {
    init {
        registerPacket(CheckPlayerExistC2SPacket::class.java)
        registerPacket(CheckPlayerExistS2CPacket::read)
        registerPacket(LoginC2SPacket::class.java)
        registerPacket(LoginS2CPacket::read)
        registerPacket(RegisterC2SPacket::class.java)
        registerPacket(RegisterS2CPacket::read)
        registerPacket(ChatC2CPacket::class.java)
        registerPacket(ChatC2CPacket::read)
        registerPacket(PlayerMoveC2CPacket::class.java)
        registerPacket(PlayerMoveC2CPacket::read)
        registerPacket(ReadBlockC2SPacket::class.java)
        registerPacket(ReadBlockS2CPacket::read)
        registerPacket(SaveBlockC2SPacket::class.java)
        registerPacket(SetBlockC2CPacket::class.java)
        registerPacket(SetBlockC2CPacket::read)
        // registerPacket(SetBlockStateC2SPacket::class.java)

        registerPacket(PlayerCreateRoomC2SPacket::class.java)
        registerPacket(PlayerCreateRoomS2CPacket::read)
        registerPacket(PlayerJoinRoomC2SPacket::class.java)
        registerPacket(PlayerLeaveRoomC2SPacket::class.java)
        registerPacket(RoomInfoS2CPacket::read)


    }
    //包writer/reader种类 [id]
    private val packetTypes = arrayListOf<PacketType>()
    //s2c读
    private val packetIdReaders = linkedMapOf<Int,(FriendlyByteBuf) -> Packet>()
    //c2s写
    private val packetWriterClassIds = linkedMapOf<Class<out BufferWritable>,Int>()
    private fun registerPacket(reader: (FriendlyByteBuf) -> Packet){
        packetIdReaders += Pair (packetTypes.size,reader)
        packetTypes += PacketType.READ
    }
    private fun registerPacket(writerClass: Class<out BufferWritable>) {
        packetWriterClassIds += Pair (writerClass, packetTypes.size)
        packetTypes += PacketType.WRITE
    }

    //服务端传入包 客户端这边创建+处理
    fun createAndProcess(packetId: Int, data: FriendlyByteBuf){
        val type = packetTypes.getOrNull(packetId)?:let {
            logger.error { "找不到ID$packetId 的包" }
            return
        }
        when(type){
            PacketType.READ ->{
                val packet = packetIdReaders[packetId] ?.invoke(data)?:let{
                    logger.error { "找不到ID$packetId 的包" }
                    return
                }
                processPacket(packet)
            }
            else -> {
                logger.error { "服务端只能传入c2s包 ID$packetId 不是s2c包" }
                return
            }
        }

    }

    private fun processPacket(packet: Packet) {
        when(packet){
            is ClientProcessable ->{
                MC.execute {
                    packet.process()
                }
            }
        }
    }


    fun getPacketId(packetClass: Class<out Packet>): Int? {
        return packetWriterClassIds[packetClass]
    }
    enum class PacketType {
        READ,WRITE
    }

}