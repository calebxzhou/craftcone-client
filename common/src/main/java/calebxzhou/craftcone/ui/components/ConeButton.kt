package calebxzhou.craftcone.ui.components

import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-08-01,16:33.
 */
class ConeButton(val x:Int,val y:Int,width:Int,val msg:String,val onClick: (Button)->Unit) :Button(x,y,width,20, Component.literal(msg),onClick) {
}