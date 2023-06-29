package calebxzhou.craftcone.forge

import calebxzhou.craftcone.Cone.init
import calebxzhou.craftcone.MOD_ID
import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod(MOD_ID)
class ConeForge {
    init {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(MOD_ID, FMLJavaModLoadingContext.get().modEventBus)
        init()
    }
}