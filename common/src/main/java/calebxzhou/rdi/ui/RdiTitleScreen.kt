package calebxzhou.rdi.ui

import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.ui.screen.ConeConnectScreen
import calebxzhou.craftcone.ui.screen.ConeLoginScreen
import calebxzhou.craftcone.ui.screen.ConeRoomSelectScreen
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtTheme
import calebxzhou.libertorch.util.Gl
import calebxzhou.libertorch.util.OsDialogUt
import calebxzhou.libertorch.util.SysUt
import calebxzhou.libertorch.util.TimeUt
import calebxzhou.rdi.RdiConsts
import calebxzhou.rdi.RdiLevel
import com.mojang.blaze3d.platform.InputConstants
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.OptionsScreen
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen
import net.minecraft.core.RegistryAccess
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.levelgen.presets.WorldPresets

class RdiTitleScreen : Screen(Component.literal("主界面")) {
    init {
        SysUt.cleanMemory()
    }


    public override fun init() {
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        Gl.clearColor(LtTheme.now.bgColor)
        font.draw(poseStack, "${TimeUt.periodOfDay()}好", width / 2.0f - 36, height / 3f, 0xFFFFFF)
        font.draw(poseStack, "按下Enter键。" ,
            width / 2.0f - 36, height / 2f+20, 0xFFFFFF)
        super.render(poseStack, mouseX, mouseY, partialTick)
    }

    override fun tick() {
        val handle = Minecraft.getInstance().window.window
        when {
            InputConstants.isKeyDown(handle, InputConstants.KEY_K) -> {
                minecraft!!.setScreen(SelectWorldScreen(this))
                return
            }
            InputConstants.isKeyDown(handle, InputConstants.KEY_M) -> {
                try {
                    minecraft!!.setScreen(
                        Class.forName("com.terraformersmc.modmenu.gui.ModsScreen").getConstructor(
                            Screen::class.java
                        ).newInstance(this) as Screen
                    )
                } catch (e: Exception) {
                    OsDialogUt.showMessageBox("error", "必须安装ModMenu模组以使用本功能！！")
                    e.printStackTrace()
                }
                return
            }
            InputConstants.isKeyDown(handle, InputConstants.KEY_O) -> {
                minecraft!!.setScreen(OptionsScreen(this, minecraft!!.options))
                return
            }
            InputConstants.isKeyDown(handle, InputConstants.KEY_RETURN) || InputConstants.isKeyDown(handle, InputConstants.KEY_NUMPADENTER) -> {
                //连接
                //ConeNetManager.connect(RdiConsts.serverAddr)
                MC.setScreen(ConeConnectScreen())
                //RdiLevel.load(RdiTitleScreen(),RdiLevel.defaultLevelName)
            }
        }
    }



}
