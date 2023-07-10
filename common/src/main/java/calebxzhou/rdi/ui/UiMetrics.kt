package calebxzhou.rdi.ui

import calebxzhou.craftcone.LOG
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-04-19,18:19.
 */
object UiMetrics {

    //画面迁移记录
    @JvmStatic
    fun logScreenChange(prevScreen: Screen?, nextScreen: Screen?) {
        val str = StringBuilder("画面迁移： ")

        if (prevScreen != null)
            str.append(prevScreen.javaClass.name)
        else
            str.append("null")

        str.append(" -> ")

        if (nextScreen != null)
            str.append(nextScreen.javaClass.name)
        else
            str.append("null")
        LOG.info(str.toString())

    }


}
