package calebxzhou.craftcone.ui.components

import calebxzhou.libertorch.MC
import calebxzhou.libertorch.ui.DefaultColors
import com.mojang.blaze3d.vertex.PoseStack
import java.util.*

/**
 * Created  on 2023-07-30,8:53.
 */
class ConeRoomInfoEntry(private val rid:UUID): ConeEntry() {
    override fun render(
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
    ) {
        MC.font.draw(poseStack,rid.toString(),top.toFloat(),left.toFloat(),DefaultColors.White.color.opaque)
    }

}