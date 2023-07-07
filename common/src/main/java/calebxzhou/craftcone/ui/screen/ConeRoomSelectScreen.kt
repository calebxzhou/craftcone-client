package calebxzhou.craftcone.ui.screen

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomSelectScreen() : Screen(Component.literal("ConeServerSelectScreen")) {
    private lateinit var joinBtn: Button
    private lateinit var createBtn: Button
    override fun init() {
        joinBtn = Button(150,150,100,20, Component.literal("加入房间"),::onJoinRoom)
        createBtn = Button(300,300,100,20,Component.literal("创建房间"),::onCreateRoom)
        addRenderableWidget(joinBtn)
        addRenderableWidget(createBtn)
    }

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        super.render(poseStack, i, j, f)
    }


    private fun onCreateRoom(button: Button) {

    }


    private fun onJoinRoom(button: Button) {

    }
}