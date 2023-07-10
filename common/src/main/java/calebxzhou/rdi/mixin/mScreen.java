package calebxzhou.rdi.mixin;

import calebxzhou.rdi.ui.RdiTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-07-10,16:26.
 */
@Mixin(Screen.class)
public class mScreen {
    //防止画面迁移错误
    @Redirect(method = "wrapScreenError",at=@At(value = "INVOKE",target = "Ljava/lang/Runnable;run()V"))
    private static void noError(Runnable runnable){
        try {
            runnable.run();
        } catch (Throwable t) {
            Minecraft.getInstance().setScreen(new RdiTitleScreen());
        }
    }
}
