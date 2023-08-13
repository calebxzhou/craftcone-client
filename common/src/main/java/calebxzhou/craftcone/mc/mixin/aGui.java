package calebxzhou.craftcone.mc.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created  on 2023-08-13,21:08.
 */
@Mixin(Gui.class)
public interface aGui {
    @Accessor
    Component getOverlayMessageString();
}
