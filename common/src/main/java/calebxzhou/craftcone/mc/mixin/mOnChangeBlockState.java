package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.entity.ConeRoom;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

/**
 * Created  on 2023-06-29,20:31.
 */
@Mixin(Level.class)
abstract class mOnChangeBlockState {


    @Shadow @Final public boolean isClientSide;

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at=@At(value = "RETURN",ordinal = 3))
    private void onSetBlock(BlockPos blockPos, BlockState blockState, int i, int j, CallbackInfoReturnable<Boolean> cir){
        if(!isClientSide){
            Objects.requireNonNull(ConeRoom.getNow()).onSetBlock((Level)(Object)this,blockPos,blockState);
        }
    }

}
