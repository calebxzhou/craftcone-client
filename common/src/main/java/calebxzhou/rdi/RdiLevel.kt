package calebxzhou.rdi

import calebxzhou.libertorch.MC
import net.minecraft.client.gui.screens.GenericDirtMessageScreen
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.RegistryAccess
import net.minecraft.network.chat.Component
import net.minecraft.world.Difficulty
import net.minecraft.world.level.DataPackConfig
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.GameType
import net.minecraft.world.level.LevelSettings
import net.minecraft.world.level.levelgen.WorldGenSettings
import net.minecraft.world.level.levelgen.presets.WorldPresets

/**
 * Created  on 2023-07-10,13:04.
 */
object RdiLevel {
    val defaultLevelName
        get() = "rdi_level_${MC.user.name}"
    const val defaultWorldGenSeed = 1145141919810L

    private fun getDefaultSettings(levelName: String): LevelSettings {
        return LevelSettings(levelName,
            if(RdiConsts.DebugGameMode) GameType.CREATIVE else GameType.SURVIVAL,
            false,
            Difficulty.HARD,
            RdiConsts.DebugGameMode,
            GameRules(), DataPackConfig.DEFAULT)
    }
    private fun getDefaultWorldPresets(registry: RegistryAccess): WorldGenSettings {
        return WorldPresets.createNormalWorldFromPreset(registry, defaultWorldGenSeed)
    }
    fun load(screen: Screen,levelName : String){
        MC.setScreen(GenericDirtMessageScreen(Component.literal("正在加入RDI服务器。")))
        if(MC.levelSource.levelExists(levelName)){
            MC.createWorldOpenFlows().loadLevel(screen,levelName)
        }else{
            val registry = RegistryAccess.builtinCopy().freeze()
            MC.createWorldOpenFlows().createFreshLevel(levelName,
                getDefaultSettings(levelName), registry, getDefaultWorldPresets(registry))
        }
    }
}