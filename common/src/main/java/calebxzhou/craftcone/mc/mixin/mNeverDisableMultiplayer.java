package calebxzhou.craftcone.mc.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Created  on 2023-08-17,15:44.
 */
@Mixin(Minecraft.class)
public class mNeverDisableMultiplayer {
    @Overwrite
    public boolean allowsMultiplayer() {
        return true;
    }
}
