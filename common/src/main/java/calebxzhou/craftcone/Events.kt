package calebxzhou.craftcone

import calebxzhou.craftcone.Cone.inGame
import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.net.ConeNetManager.sendPacket
import calebxzhou.craftcone.net.protocol.game.ConeChatPacket
import calebxzhou.craftcone.net.protocol.game.ConePlayerJoinPacket
import calebxzhou.craftcone.net.protocol.game.ConePlayerQuitPacket
import calebxzhou.craftcone.net.protocol.game.ConeSetBlockPacket
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.MCS
import dev.architectury.event.EventResult
import dev.architectury.event.events.common.BlockEvent
import dev.architectury.event.events.common.ChatEvent
import dev.architectury.event.events.common.InteractionEvent
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.event.events.common.PlayerEvent
import dev.architectury.utils.value.IntValue
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-06-29,18:12.
 */
object Events{
    fun register(){
        BlockEvent.BREAK.register(::onBreakBlock)
        PlayerEvent.PLAYER_JOIN.register(::onPlayerJoin)
        PlayerEvent.PLAYER_QUIT.register(::onPlayerQuit)
        ChatEvent.RECEIVED.register(::onChat )
        LifecycleEvent.SERVER_STARTED.register(::onLocalServerStarted)
        LifecycleEvent.SERVER_STOPPING.register(::onLocalServerStopping)
    }



    private fun onPlayerQuit(player: ServerPlayer) {
        sendPacket(
            ConePlayerQuitPacket(
                player.uuid,
                player.displayName.string
            )
        )
    }

    private fun onPlayerJoin(player: ServerPlayer) {
        sendPacket(
            ConePlayerJoinPacket(
                player.uuid,
                player.displayName.string
            )
        )
    }



    private fun onBreakBlock(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        serverPlayer: ServerPlayer?,
        intValue: IntValue?
    ): EventResult? {
        sendPacket(
            ConeSetBlockPacket(
                level,
                blockPos,
                Blocks.AIR.defaultBlockState()
            )
        )
        return EventResult.pass()
    }


    //广播包：本地服务器接收到聊天信息时
    private fun onChat(player: ServerPlayer?, component: Component?): EventResult? {
        if(player==null || component==null)
            return EventResult.pass()
        ConeNetManager.sendPacket(ConeChatPacket(MC.user.name,component.string))
        return EventResult.pass()
    }

    //本地服务器启动时
    private fun onLocalServerStarted(server: MinecraftServer) {
        //打开地图时，每个维度编成数字
        Cone.numDimKeyMap.clear()
        server.levelKeys().forEach {
            val number = Cone.numDimKeyMap.size
            LOG.info("Dimension Number: $number $it")
            Cone.numDimKeyMap += Pair(number,it)
        }

        inGame = true
    }


    //本地服务器关闭时
    private fun onLocalServerStopping(server: MinecraftServer?) {
        inGame = false
    }
}
