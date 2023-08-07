package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.Events;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-08-07,22:26.
 */
@Mixin(PauseScreen.class)
public class mPauseScreen {
    @Inject(method = "method_19836",at=@At(value = "INVOKE",target = "Lnet/minecraft/client/multiplayer/ClientLevel;disconnect()V"))
    private void onQuit(Button button, CallbackInfo ci){
        Events.INSTANCE.onClickQuitSaveButton();
    }
}
