package calebxzhou.libertorch

import net.minecraft.client.Minecraft

/**
 * Created  on 2023-07-10,11:02.
 */
val MC: Minecraft
    get() = Minecraft.getInstance() ?: run{
        throw IllegalStateException("Minecraft 未启动")
    }