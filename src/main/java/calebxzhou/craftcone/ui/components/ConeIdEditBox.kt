package calebxzhou.craftcone.ui.components

import calebxzhou.craftcone.REGEX_OBJID
import calebxzhou.craftcone.mc.Mc
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-24,12:37.
 */
class ConeIdEditBox(x: Int, y: Int, w: Int) : EditBox(Mc.font, x, y, w, 20, Component.literal("IdEditBox")) {
    init {
        setMaxLength(10)
    }

    val isValueValid
        get() = value.isNotEmpty() && value.matches(REGEX_OBJID)


    override fun tick() {
        super.tick()
    }

}
