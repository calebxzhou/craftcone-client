package calebxzhou.craftcone.ui.screen

import calebxzhou.libertorch.MC
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomMineScreen(val prevScreen: Screen) : ConeScreen("我的房间（可创建5个）") {
    private lateinit var joinBtn: Button
    private lateinit var delBtn: Button
    override fun init() {
        joinBtn = Button(100,height-30,80,20, Component.literal("加入房间"),::onJoinRoom)
        delBtn = Button(180,height-30,80,20,Component.literal("删除房间"),::onDeleteRoom)

        addRenderableWidget(joinBtn)
        addRenderableWidget(delBtn)
    }

    private fun onMyRoom(button: Button) {

    }

    private fun onDeleteRoom(button: Button) {
        MC.setScreen(ConeRoomCreateScreen(this))
    }

    private fun onJoinRoom(button: Button) {

    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()

        super.render(poseStack, mouseX, mouseY, partialTick)

    }

    override fun tick() {
       // joinBtn.visible = roomIdBox.isValueValid

        super.tick()
    }

    override fun shouldCloseOnEsc(): Boolean {
        return true
    }

}