package calebxzhou.rdi.mixin;

import calebxzhou.rdi.ui.UiMetrics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-07-10,16:31.
 */
@Mixin(Minecraft.class)
public class mMinecraft {
    @Shadow
    @Nullable
    public Screen screen;

    //记录画面迁移
    @Inject(method = "setScreen",at=@At("HEAD"))
    private void setScreen(Screen guiScreen, CallbackInfo ci){
        UiMetrics.logScreenChange(screen,guiScreen);
    }
}
