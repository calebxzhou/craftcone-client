package calebxzhou.craftcone.mc.mixin;

import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.ArrayDeque;

/**
 * Created  on 2023-07-15,22:03.
 */
@Mixin(CollectingNeighborUpdater.class)
public interface aCollectingNeighborUpdater {
    @Accessor
    ArrayDeque<CollectingNeighborUpdater.NeighborUpdates> getStack();
}
