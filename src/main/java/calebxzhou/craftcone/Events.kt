package calebxzhou.craftcone

import calebxzhou.craftcone.command.ConeRefreshChunkCommand
import calebxzhou.craftcone.entity.ConeChunkPos
import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.misc.ChunkGenManager
import calebxzhou.craftcone.net.ConeNetSender.sendPacket
import calebxzhou.craftcone.net.protocol.game.GetChunkC2SPacket
import calebxzhou.craftcone.net.protocol.game.SendChatMsgC2SPacket
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.multiplayer.ClientPacketListener
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.chunk.LevelChunk
import org.quiltmc.qsl.command.api.CommandRegistrationCallback
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents
import org.quiltmc.qsl.screen.api.client.ScreenEvents

/**
 * Created  on 2023-06-29,18:12.
 */
object Events {
    fun register() {
        PlayerBlockBreakEvents.AFTER.register(::onBreakBlock)
        ServerMessageEvents.CHAT_MESSAGE.register(::onChat)
        ServerWorldLoadEvents.LOAD.register(::onLevelLoad)
        //ServerLifecycleEvents.READY.register(::onLocalServerReady)
        ServerLifecycleEvents.STOPPING.register(::onLocalServerStopping)
        ClientPlayConnectionEvents.JOIN.register(::onClientPlayerJoin)
        ClientPlayConnectionEvents.DISCONNECT.register(::onClientPlayerLeave)
        ClientChunkEvents.CHUNK_LOAD.register(::onClientChunkLoad)
        CommandRegistrationCallback.EVENT.register(::onRegisterCommand)
        //TODO 当关闭容器画面时 上传容器内容
    }

    private fun onClientChunkLoad(level: ClientLevel, chunk: LevelChunk) {
        ConeRoom.now?.let {
            sendPacket(GetChunkC2SPacket(it.getDimIdByLevel(level), chunk.pos.run { ConeChunkPos(x, z) }))
        }
    }

    private fun onClientPlayerLeave(listener: ClientPacketListener, minecraft: Minecraft) {
        Mcl.isLocalServerReady = false
        ConeRoom.unload()
    }

    private fun onClientPlayerJoin(listener: ClientPacketListener, sender: PacketSender, minecraft: Minecraft) {
        Mcl.isLocalServerReady = true
        Mcl.startLanShare(ConeRoom.now?.isCreative ?: false)
    }

    private fun onChat(message: PlayerChatMessage, player: ServerPlayer, bound: ChatType.Bound) {
        //玩家发聊天消息时
        message.unsignedContent?.let { SendChatMsgC2SPacket(it.string) }?.let { sendPacket(it) }
    }

    private fun onBreakBlock(
        level: Level,
        player: Player,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BlockEntity?
    ) {
        ConeRoom.now?.onPlayerBreakBlock(level, blockPos)
    }

    private fun onLevelLoad(server: MinecraftServer, level: ServerLevel) {
        ConeRoom.now?.addDimension(level)
    }

    private fun onRegisterCommand(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        context: CommandBuildContext,
        selection: Commands.CommandSelection
    ) {
        ConeRefreshChunkCommand.register(dispatcher)
    }


    private fun onLocalServerStopping(minecraftServer: MinecraftServer?) {
        ChunkGenManager.clear()
    }


    //本地服务器启动时
    private fun onLocalServerReady(server: MinecraftServer) {

    }


}
