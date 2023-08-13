package calebxzhou.craftcone.mc

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.mixin.aGui
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.server.IntegratedServer
import net.minecraft.core.RegistryAccess
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.HttpUtil
import net.minecraft.world.Difficulty
import net.minecraft.world.level.*
import net.minecraft.world.level.levelgen.presets.WorldPresets

/**
 * Created  on 2023-08-12,16:21.
 */
object Mc {
    private val MC: Minecraft
        get() = Minecraft.getInstance() ?: run{
            throw IllegalStateException("Minecraft Not Start !")
        }
    var screen
        get() = MC.screen
        set(value) = renderThread { MC.setScreen(value) }
    val playerName
        get() = MC.user.name
    val windowWidth
        get() = MC.window.guiScaledWidth
    val windowHeight
        get() = MC.window.guiScaledHeight
    val hwnd
        get() = MC.window.window
    val font
        get() = MC.font
    var overlay
        set(value) {MC.overlay = value}
        get() = MC.overlay
    fun renderThread(todo: ()->Unit){
        MC.execute(todo)
    }
    fun hasLevel(levelName:String): Boolean {
        return MC.levelSource.levelExists(levelName)
    }
    fun loadLevel(levelName: String){
        MC.createWorldOpenFlows().loadLevel(TitleScreen(),levelName)
    }
    fun copyClipboard(content:String){
        MC.keyboardHandler.clipboard = content
    }
    fun createLevel(
        levelName: String,
        isCreative: Boolean,
        seed:Long){
        val registry = RegistryAccess.builtinCopy().freeze()
        val levelSettings = LevelSettings (
            levelName,
            if(isCreative) GameType.CREATIVE else GameType.SURVIVAL,
            false,
            Difficulty.HARD,
            isCreative,
            GameRules(), DataPackConfig.DEFAULT)
        val worldPresets = WorldPresets.createNormalWorldFromPreset(registry,seed)
        MC.createWorldOpenFlows().createFreshLevel(levelName,
            levelSettings, registry,worldPresets
        )
    }
    object InGame{
        private val MCS: IntegratedServer?
            get() = Minecraft.getInstance().singleplayerServer?.also {
                logger.warn { "本地服务器未启动！" }
            }

        val gameMode
            get() = MC.gameMode?.playerMode
        var actionBarMsg
            get() = (MC.gui as aGui).overlayMessageString
            set(value) = MC.gui.setOverlayMessage(value,false)
        val player
            get() = MC.player
        val level
            get() = MC.level
        fun logicThread(todo: (IntegratedServer) -> Unit){
            MCS?.let {
                it.execute{
                    todo.invoke(it)
                }
            }
        }
        fun getLevel(dim: ResourceKey<Level>): ServerLevel? {
            return MCS?.getLevel(dim)
        }
        fun startLanShare(isCreative: Boolean){
            MCS?.publishServer(
                if(isCreative)GameType.CREATIVE else GameType.SURVIVAL,
                isCreative,
                HttpUtil.getAvailablePort())?:let {
                logger.error { "启动局域网模式失败" }
            }
        }
        fun addChatMsg(msg:String){
            addChatMsg(Component.literal(msg))
        }
        fun addChatMsg(component: Component){
            Mc.InGame.addChatMsg(component)
        }
    }
}