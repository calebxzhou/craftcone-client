package calebxzhou.craftcone.ui.components

import calebxzhou.libertorch.MC
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-08-01,16:31.
 */
open class ConeEditBox(val x:Int, val y : Int, width: Int): EditBox(MC.font,x,y,width,20, Component.literal("输入框")) {

}