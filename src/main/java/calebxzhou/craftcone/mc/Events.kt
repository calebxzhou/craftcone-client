package calebxzhou.craftcone.mc

import calebxzhou.craftcone.command.ConeRefreshChunkCommand
import calebxzhou.craftcone.entity.ConeChunkPos
import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.misc.ChunkGenManager
import calebxzhou.craftcone.net.ConeNetSender.sendPacket
import calebxzhou.craftcone.net.protocol.game.*
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents
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
import net.minecraft.world.level.chunk.LevelChunk
import org.quiltmc.qsl.command.api.CommandRegistrationCallback
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents

/**
 * Created  on 2023-06-29,18:12.
 */
object Events {
    fun register() {
        PlayerBlockBreakEvents.AFTER.register(Events::onBreakBlock)
        ServerMessageEvents.CHAT_MESSAGE.register(Events::onChat)
        ServerWorldLoadEvents.LOAD.register(Events::onLevelLoad)
        //ServerLifecycleEvents.READY.register(::onLocalServerReady)
        ServerLifecycleEvents.STOPPING.register(Events::onLocalServerStopping)
        ClientPlayConnectionEvents.JOIN.register(Events::onClientPlayerJoin)
        ClientPlayConnectionEvents.DISCONNECT.register(Events::onClientPlayerLeave)
        ClientChunkEvents.CHUNK_LOAD.register(Events::onClientChunkLoad)
        CommandRegistrationCallback.EVENT.register(Events::onRegisterCommand)
        ClientTickEvents.START.register(::onClientTickStart)
        //TODO 当关闭容器画面时 上传容器内容
    }

    var ticks = 0
    private fun onClientTickStart(mc: Minecraft) {
        if(ticks<20){
            mc.player?.let {
                sendPacket(MovePlayerXyzC2SPacket(it.x.toFloat(),it.y.toFloat(),it.z.toFloat()))
                sendPacket(MovePlayerWpC2SPacket(it.xRot, it.yRot))
            }
            ticks++
        }else{
            ticks=0
        }
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
        message.signedContent()?.let { sendPacket(SendChatMsgC2SPacket(it)) }
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
