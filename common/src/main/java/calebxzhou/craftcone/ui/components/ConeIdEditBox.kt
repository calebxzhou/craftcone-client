package calebxzhou.craftcone.ui.components

import calebxzhou.craftcone.Consts
import calebxzhou.libertorch.MC
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-24,12:37.
 */
class ConeIdEditBox(x:Int, y:Int, w:Int ) : EditBox(MC.font,x,y,w,20,Component.literal("IdEditBox")) {
    init {
        setMaxLength(10)
    }
    val isValueValid
        get() =  value.isNotEmpty() && value.matches(Consts.regexInt)

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        super.render(poseStack, i, j, f)
    }

    override fun tick() {
        super.tick()
    }

}