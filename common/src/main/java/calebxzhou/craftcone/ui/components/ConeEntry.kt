package calebxzhou.craftcone.ui.components

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.AbstractSelectionList

/**
 * Created  on 2023-07-30,8:54.
 */
abstract class ConeEntry : AbstractSelectionList.Entry<ConeEntry>() {
    abstract override fun render(
        poseStack: PoseStack,
        index: Int,
        top: Int,
        left: Int,
        width: Int,
        height: Int,
        mouseX: Int,
        mouseY: Int,
        isMouseOver: Boolean,
        partialTick: Float
    )

}