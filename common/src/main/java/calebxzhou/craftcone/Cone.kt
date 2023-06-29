package calebxzhou.craftcone

import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import org.slf4j.Logger
import org.slf4j.LoggerFactory



const val MOD_ID = "craftcone"
val LOG: Logger = LoggerFactory.getLogger("CraftCone")
val MC: Minecraft
    get() = Minecraft.getInstance()

object Cone {
    val dimensionNumberMap = hashMapOf<Int,ResourceKey<Level>>()
    @JvmStatic
    fun init() {
        Events.register()
        NetworkManager
    }
}
/*


 */