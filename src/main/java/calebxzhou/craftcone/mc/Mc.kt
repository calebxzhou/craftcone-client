package calebxzhou.craftcone.mc

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.toasts.Toast
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.core.RegistryAccess
import net.minecraft.world.Difficulty
import net.minecraft.world.level.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.WorldOptions
import net.minecraft.world.level.levelgen.presets.WorldPresets

/**
 * Created  on 2023-08-12,16:21.
 */
object Mc {
    private val MC: Minecraft
        get() = Minecraft.getInstance() ?: run {
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
        set(value) {
            MC.overlay = value
        }
        get() = MC.overlay
    val blockStates
        get() = Block.BLOCK_STATE_REGISTRY
    val blockStateAmount
        get() = Block.BLOCK_STATE_REGISTRY.size()

    fun getBlockStateById(id: Int): BlockState? {
        return blockStates.byId(id)
    }

    fun renderThread(todo: () -> Unit) {
        MC.execute(todo)
    }

    fun goTitleScreen() {
        screen = TitleScreen()
    }

    fun addToast(toast: Toast) {
        MC.toasts.addToast(toast)
    }

    fun hasLevel(levelName: String): Boolean {
        return MC.levelSource.levelExists(levelName)
    }

    fun loadLevel(levelName: String) {
        MC.createWorldOpenFlows().loadLevel(TitleScreen(), levelName)
    }

    fun copyClipboard(content: String) {
        MC.keyboardHandler.clipboard = content
    }

    fun createLevel(
        levelName: String,
        isCreative: Boolean,
        seed: Long
    ) {
        val levelSettings = LevelSettings(
            levelName,
            if (isCreative) GameType.CREATIVE else GameType.SURVIVAL,
            false,
            Difficulty.HARD,
            isCreative,
            GameRules(), WorldDataConfiguration.DEFAULT
        )
        MC.createWorldOpenFlows().createFreshLevel(
            levelName,
            levelSettings,
            WorldOptions(seed, true, true),
            WorldPresets::createNormalWorldDimensions
        )
    }
}
