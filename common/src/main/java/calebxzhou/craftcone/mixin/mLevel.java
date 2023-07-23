package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConeNetManager;
import calebxzhou.craftcone.net.protocol.room.SaveBlockC2SPacket;
import calebxzhou.craftcone.utils.LevelUt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created  on 2023-06-29,20:31.
 */
@Mixin(Level.class)
abstract class mLevel {


    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at=@At(value = "RETURN",ordinal = 3))
    private void onSetBlockAir(BlockPos blockPos, BlockState blockState, int i, int j, CallbackInfoReturnable<Boolean> cir){
        ConeNetManager.sendPacket(
                new SaveBlockC2SPacket(
                        LevelUt.getDimIdByLevel((Level)(Object)this),
                        blockPos.asLong(),
                        Block.BLOCK_STATE_REGISTRY.getId(blockState)
                )
        );
    }

}
