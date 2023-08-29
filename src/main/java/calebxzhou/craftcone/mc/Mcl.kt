package calebxzhou.craftcone.mc

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.mixin.aGui
import calebxzhou.craftcone.utils.OsDialogUt
import com.mojang.authlib.GameProfile
import net.minecraft.client.Minecraft
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.HttpUtil
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.GameType
import net.minecraft.world.level.Level
import java.util.*

/**
 * Created  on 2023-08-15,8:15.
 */
//mc playing level
object Mcl {
    private val MC: Minecraft
        get() = Minecraft.getInstance() ?: run {
            throw IllegalStateException("Minecraft Not Start !")
        }
    val MCS: IntegratedServer?
        get() = Minecraft.getInstance().singleplayerServer

    @JvmStatic
    var isLocalServerReady = false
    val gameMode
        get() = MC.gameMode?.playerMode
    var actionBarMsg
        get() = (MC.gui as aGui).overlayMessageString
        set(value) = MC.gui.setOverlayMessage(value, false)
    val player
        get() = MC.player
    val level
        get() = MC.level?.dimension()?.let { MCS?.getLevel(it) }

    fun logicThread(todo: (IntegratedServer) -> Unit) {
        MCS?.let {
            it.execute {
                todo.invoke(it)
            }
        }
    }

    fun getLevel(dim: ResourceKey<Level>): ServerLevel? {
        return MCS?.getLevel(dim)
    }

    fun getOverworld(mcs: IntegratedServer): ServerLevel {
        return mcs.overworld()
    }

    fun startLanShare(isCreative: Boolean) = MCS?.publishServer(
            if (isCreative) GameType.CREATIVE else GameType.SURVIVAL,
            isCreative,
            HttpUtil.getAvailablePort()
        ) ?: let {
            logger.error("启动局域网模式失败")
        }


    fun addChatMsg(msg: String) {
        addChatMsg(Component.literal(msg))
    }

    fun addChatMsg(component: Component) {
        logger.info("系统消息 $component")
        MC.gui.chat.addMessage(component)
    }


    fun spawnPlayer(player: ServerPlayer) = MCS?.let { getOverworld(it).addFreshEntity(player) }

    fun despawnPlayer(player: ServerPlayer) = MCS?.let {
        getOverworld(it).removePlayerImmediately(
            player, Entity.RemovalReason.DISCARDED
        )
    }


}

fun ConePlayer.toMcPlayer(mcs: IntegratedServer): ServerPlayer =
    ServerPlayer(mcs, mcs.overworld(), GameProfile(UUID.nameUUIDFromBytes(id.toByteArray()), name))
