package calebxzhou.craftcone

import net.minecraft.network.PacketListener
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.*

/**
 * Created  on 2023-06-21,22:51.
 */
object PacketsToBroadcast {
    //本地服务端->本地客户端 广播给房间内其他玩家的包
    val s2cSend = hashSetOf<Class<*>>(
        //方块更新
        ClientboundSectionBlocksUpdatePacket::class.java,
        ClientboundBlockUpdatePacket::class.java,
        ClientboundBlockEventPacket::class.java,
        ClientboundBlockDestructionPacket::class.java,
        ClientboundBlockEntityDataPacket::class.java,
        ClientboundLightUpdatePacket::class.java,
        //世界事件
        ClientboundLevelEventPacket::class.java,
        //玩家对话
        ClientboundPlayerChatPacket::class.java,
        //爆炸
        ClientboundExplodePacket::class.java,
    )
    //本地服务端<-本地客户端 广播给房间内其他玩家的包
    val c2sSend = hashSetOf<Class<*>>(
        //方块放置
        ServerboundUseItemOnPacket::class.java,
        ServerboundPlayerActionPacket::class.java,
    )


    //本地服务端->本地客户端 不广播给房间内其他玩家的包
    @JvmField
    val s2cNotSend = hashSetOf<Class<*>>(
        ClientboundBlockChangedAckPacket::class.java,
        ClientboundChatPreviewPacket::class.java,
        ClientboundCommandSuggestionsPacket::class.java,
        ClientboundContainerClosePacket::class.java,
        ClientboundCustomChatCompletionsPacket::class.java,
        ClientboundDisconnectPacket::class.java,
        ClientboundHorseScreenOpenPacket::class.java,
        ClientboundLoginPacket::class.java,
        ClientboundOpenBookPacket::class.java,
        ClientboundOpenScreenPacket::class.java,
        ClientboundOpenSignEditorPacket::class.java,
        ClientboundLevelChunkWithLightPacket::class.java,
        ClientboundForgetLevelChunkPacket::class.java,
        ClientboundSetChunkCacheCenterPacket::class.java,
        ClientboundGameEventPacket::class.java,
        ClientboundSetTimePacket::class.java,
        ClientboundSystemChatPacket::class.java,
        ClientboundPlayerAbilitiesPacket::class.java,
        ClientboundSetEntityDataPacket::class.java,
        ClientboundUpdateAttributesPacket::class.java,
        ClientboundPlayerPositionPacket::class.java,
        ClientboundEntityEventPacket::class.java,
        )
    val debug_PacketsNotPrint = listOf<Class<*>>(
        ClientboundRotateHeadPacket::class.java,
        ClientboundSetEntityMotionPacket::class.java,
        ClientboundMoveEntityPacket::class.java,
        ClientboundMoveEntityPacket.PosRot::class.java,
        ClientboundMoveEntityPacket.Pos::class.java,
        ClientboundMoveEntityPacket.Rot::class.java,
        ClientboundTeleportEntityPacket::class.java,
    )
    //本地客户端->本地服务端 不广播给房间内其他玩家的包
    @JvmField
    val c2sNotSend = listOf(
        ServerboundClientInformationPacket::class.java,
    )
}