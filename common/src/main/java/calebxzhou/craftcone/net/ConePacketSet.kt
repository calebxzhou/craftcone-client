package calebxzhou.craftcone.net

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.net.protocol.account.LoginPacket
import calebxzhou.craftcone.net.protocol.account.RegisterPacket
import calebxzhou.craftcone.net.protocol.game.*
import calebxzhou.craftcone.net.protocol.general.SysMsgPacket
import calebxzhou.craftcone.net.protocol.room.*
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-14,8:55.
 */
object ConePacketSet {

    //包writer/reader种类 [id]
    private val packetTypes = arrayListOf<PacketType>()
    //s2c读
    private val packetIdReaders = linkedMapOf<Int,(FriendlyByteBuf) -> Packet>()
    //c2s写
    private val packetWriterClassIds = linkedMapOf<Class<out BufferWritable>,Int>()
    init {
        registerPacket(ConeRoom::read)
        registerPacket(SysMsgPacket::read)

        registerPacket(LoginPacket::class.java)
        registerPacket(RegisterPacket::class.java)
        registerPacket(SendChatMsgPacket::class.java)
        registerPacket(PlayerMoveC2CPacket::class.java)
        registerPacket(PlayerMoveC2CPacket::read)
        registerPacket(GetChunkPacket::class.java)
        registerPacket(BlockDataPacket::read)
        registerPacket(SetBlockPacket::class.java)
        registerPacket(SetBlockPacket::read)
        // registerPacket(SetBlockStateC2SPacket::class.java)

        registerPacket(GetRoomPacket::class.java)
        registerPacket(CreateRoomPacket::class.java)
        registerPacket(DelRoomPacket::class.java)
        registerPacket(JoinRoomPacket::class.java)
        registerPacket(PlayerJoinedRoomPacket::read)
        registerPacket(LeaveRoomPacket::class.java)
        registerPacket(PlayerLeftRoomPacket::read)



    }

    private fun registerPacket(reader: (FriendlyByteBuf) -> Packet){
        packetIdReaders += Pair (packetTypes.size,reader)
        packetTypes += PacketType.READ
    }
    private fun registerPacket(writerClass: Class<out BufferWritable>) {
        packetWriterClassIds += Pair (writerClass, packetTypes.size)
        packetTypes += PacketType.WRITE
    }


    fun createPacket(packetId: Int,data: FriendlyByteBuf): Packet? {
        return packetIdReaders[packetId] ?.invoke(data)?:let{
            logger.error { "找不到ID$packetId 的包" }
            null
        }

    }
    fun processPacket(packet: Packet) {
        when(packet){
            is RenderThreadProcessable ->{
                Mc.renderThread {
                    packet.process()
                }
            }
            is ServerThreadProcessable ->{
                Mcl.logicThread {
                    packet.process(it)
                }
            }
            is InRoomProcessable ->{
                Mcl.logicThread {
                    packet.process(it,ConeRoom.now?:let {
                        logger.warn { "收到了房间内数据包，但是没有在房间内！" }
                        return@logicThread
                    })
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