package calebxzhou.craftcone

import calebxzhou.craftcone.command.ConeRefreshChunkCommand
import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.ConeNetSender.sendPacket
import calebxzhou.craftcone.net.protocol.game.PlayerChatC2SPacket
import calebxzhou.craftcone.net.protocol.game.SetBlockC2CPacket
import calebxzhou.craftcone.utils.LevelUt
import calebxzhou.craftcone.utils.LevelUt.numDimKeyMap
import com.mojang.brigadier.CommandDispatcher
import dev.architectury.event.EventResult
import dev.architectury.event.events.client.ClientPlayerEvent
import dev.architectury.event.events.common.BlockEvent
import dev.architectury.event.events.common.ChatEvent
import dev.architectury.event.events.common.CommandRegistrationEvent
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.utils.value.IntValue
import net.minecraft.client.player.LocalPlayer
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-06-29,18:12.
 */
object Events{
    fun register(){
        BlockEvent.BREAK.register(::onBreakBlock)
        ChatEvent.RECEIVED.register(::onChat )
        ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(::onClientPlayerJoin)
        LifecycleEvent.SERVER_STARTED.register(::onLocalServerStarted)
        LifecycleEvent.SERVER_STOPPING.register(::onLocalServerStopping)
        CommandRegistrationEvent.EVENT.register(::onRegisterCommand)
    }

    private fun onRegisterCommand(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        context: CommandBuildContext,
        selection: Commands.CommandSelection
    ) {
        ConeRefreshChunkCommand.register(dispatcher)
    }

    private fun onClientPlayerJoin(localPlayer: LocalPlayer) {
        Mcl.startLanShare(localPlayer.isCreative)
    }

    private fun onLocalServerStopping(minecraftServer: MinecraftServer?) {
        logger.info { "本地服务器正在停止" }
        Room.unload()
    }






    private fun onBreakBlock(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        serverPlayer: ServerPlayer?,
        intValue: IntValue?
    ): EventResult? {
        sendPacket(
            SetBlockC2CPacket(
                LevelUt.getDimIdByLevel(level),
                blockPos.asLong(),
                Block.BLOCK_STATE_REGISTRY.getId(Blocks.AIR.defaultBlockState())
            )
        )

        return EventResult.pass()
    }


    //广播包：本地服务器接收到聊天信息时
    private fun onChat(player: ServerPlayer?, component: Component?): EventResult? {
        if(player==null || component==null)
            return EventResult.pass()
        ConeNetSender.sendPacket(PlayerChatC2SPacket(Mc.playerName,component.string))
        return EventResult.pass()
    }

    //本地服务器启动时
    private fun onLocalServerStarted(server: MinecraftServer) {
        //打开地图时，每个维度编成数字
        numDimKeyMap.clear()
        server.levelKeys().forEach {
            val number = numDimKeyMap.size
            logger.info("Dimension Number: $number $it")
            numDimKeyMap += Pair(number,it)
        }
    }


}
