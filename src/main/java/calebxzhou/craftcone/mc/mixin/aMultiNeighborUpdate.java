package calebxzhou.craftcone.mc.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Created  on 2023-07-15,22:20.
 */
@Mixin(CollectingNeighborUpdater.MultiNeighborUpdate.class)
public interface aMultiNeighborUpdate {
	@Accessor
	public BlockPos getSourcePos();
}
