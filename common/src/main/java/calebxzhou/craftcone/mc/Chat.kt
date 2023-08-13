package calebxzhou.craftcone.mc

import calebxzhou.libertorch.MC
import net.minecraft.network.chat.Component

object Chat {
    fun addMsg(msg:String){
        addMsg(Component.literal(msg))
    }
    fun addMsg(component: Component){
        MC.gui.chat.addMessage(component)
    }
}