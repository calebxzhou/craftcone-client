package calebxzhou.craftcone.ui.overlay

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.ui.DefaultColors
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.gui.components.toasts.Toast
import net.minecraft.client.gui.components.toasts.ToastComponent
import net.minecraft.network.chat.Component
import net.minecraft.util.FormattedCharSequence
import net.minecraft.util.Mth
import kotlin.math.max

/**
 * Created  on 2023-08-15,8:03.
 */
fun coneToast(level: MsgLevel,msg:()->String){

}
class ConeToast(val level:MsgLevel,val msg:String,val time:Int=5000) :Toast{
    var progress = 0f
    private var lastProgress = 0f
    private var lastProgressTime = 0L

    private val msgLines: MutableList<FormattedCharSequence> = Mc.font.split(Component.literal(msg), 100)
    private val width = max(200, msgLines.maxOf(Mc.font::width))

    private var timeSinceLastVisible = 0L
    private var visibility = Toast.Visibility.SHOW
    override fun render(
        poseStack: PoseStack,
        toastComponent: ToastComponent,
        timeSinceLastVisible: Long
    ): Toast.Visibility {

        GuiComponent.fill(poseStack, 3, 10, 157, 33, level.color.opaque);
        msgLines.forEachIndexed { i, str ->
            GuiComponent.drawString(poseStack, Mc.font, str, 18, 22 + i * 12, DefaultColors.White.color.opaque)
        }

        if (progress > 0f) {
            renderProgress(poseStack, timeSinceLastVisible)
        }
        checkVisibility(timeSinceLastVisible)
        renderDisplayTimeLeft(poseStack)

        return visibility
    }

    //下方剩余时间条
    private fun renderDisplayTimeLeft(poseStack: PoseStack) {
        val prog = 1 - timeSinceLastVisible.toFloat()/time.toFloat()
        var progX = (3 + width * prog).toInt()
        if(progX<3)
            progX= 3
        val h = height()
        GuiComponent.fill(poseStack, 3, h, progX, h + 1, DefaultColors.White.color.opaque)
        GuiComponent.fill(poseStack, 3, h + 1, progX, h + 2, DefaultColors.KleinBlue.color.opaque)
        GuiComponent.fill(poseStack, 3, h + 2, progX, h + 3, DefaultColors.White.color.opaque)
    }

    //上方进度条
    private fun renderProgress(poseStack: PoseStack, timeSinceLastVisible: Long) {
        val prog = Mth.clampedLerp(lastProgress, progress, (timeSinceLastVisible - lastProgressTime).toFloat() / 100.0f)
        val progX = getProgressBarLength(prog).toInt()
        GuiComponent.fill(poseStack, 3, 8, progX, 9, DefaultColors.Yellow.color.opaque)
        GuiComponent.fill(poseStack, 3, 9, progX, 10, DefaultColors.Red.color.opaque)
        lastProgress = prog
        lastProgressTime = timeSinceLastVisible
    }

    //根据进度获取进度条长度
    private fun getProgressBarLength(progress: Float): Float {
        return 3f + 154f * progress
    }

    //检查一下到没到时间，到时间就不显示了
    private fun checkVisibility(timeSinceLastVisible: Long) {
        this.timeSinceLastVisible = timeSinceLastVisible
        visibility = if (timeSinceLastVisible < time){
            Toast.Visibility.SHOW
        }
        else{
            Toast.Visibility.HIDE
        }
    }


    override fun width(): Int {
        return width
    }

    override fun height(): Int {
        return 20 + msgLines.size * 12
    }

}