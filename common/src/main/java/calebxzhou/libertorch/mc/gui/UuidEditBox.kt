package calebxzhou.libertorch.mc.gui

import calebxzhou.craftcone.Consts
import calebxzhou.libertorch.MC
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-24,12:37.
 */
class UuidEditBox(x:Int,y:Int,w:Int,h:Int) : EditBox(MC.font,x,y,w,h,Component.literal("UuidEditBox")) {
    init {
        setMaxLength(36)
    }
    val isValueValid
        get() =  value.isNotEmpty() && value.matches(Consts.regexUuid)

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        super.render(poseStack, i, j, f)
    }

    override fun tick() {
        super.tick()
    }

}