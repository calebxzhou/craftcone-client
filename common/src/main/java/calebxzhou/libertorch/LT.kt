package calebxzhou.libertorch

import net.minecraft.client.Minecraft
import net.minecraft.client.server.IntegratedServer

/**
 * Created  on 2023-07-10,11:02.
 */
val MC: Minecraft
    get() = Minecraft.getInstance() ?: run{
        throw IllegalStateException("Minecraft Not Start !")
    }
val MCS: IntegratedServer?
    get() = Minecraft.getInstance().singleplayerServer