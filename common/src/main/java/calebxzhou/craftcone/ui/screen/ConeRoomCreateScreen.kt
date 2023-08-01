package calebxzhou.craftcone.ui.screen

import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-24,22:43.
 */
class ConeRoomCreateScreen(val prevScreen: Screen): LtScreen("创建房间") {
    private lateinit var gameModeBtn :Button
    private var isCreative = false
    override fun init() {
        gameModeBtn =Button(100,100,100,50, Component.literal("游戏模式：生存"),::onChangeGameMode)
        addRenderableWidget(gameModeBtn)
        addRenderableWidget(Button(100,200,100,50, Component.literal("立刻创建"),::onCreateRoom))
        super.init()
    }

    private fun onCreateRoom(button: Button) {

    }

    override fun tick() {
        super.tick()
    }
    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()

        super.render(poseStack, mouseX, mouseY, partialTick)
    }

    private fun onChangeGameMode(button: Button) {
        if(!isCreative){
            gameModeBtn.  message= Component.literal("游戏模式：创造")
            isCreative=true
        }else{
            gameModeBtn.  message= Component.literal("游戏模式：生存")
            isCreative=false
        }
    }

    override fun onClose() {
        MC.setScreen(prevScreen)
    }
}