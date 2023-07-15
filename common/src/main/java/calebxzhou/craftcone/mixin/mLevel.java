package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.NeighborUpdateManager;
import calebxzhou.craftcone.net.ConeNetManager;
import calebxzhou.craftcone.net.protocol.game.ConeSetBlockPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import net.minecraft.world.level.redstone.NeighborUpdater;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayDeque;

/**
 * Created  on 2023-06-29,20:31.
 */
@Mixin(Level.class)
abstract class mLevel {

    @Shadow @Final protected NeighborUpdater neighborUpdater;


/*    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
            at=@At(value = "HEAD"))
    private void onSetBlock(BlockPos blockPos, BlockState blockState, int i, CallbackInfoReturnable<Boolean> cir){
        //1. 不发送非源头液体
        if(blockState.getBlock() instanceof LiquidBlock){
            if (!blockState.getFluidState().isSource()) {
                return;
            }
        }
        //2. 不发送neighbor update方块
            if(NeighborUpdateManager.has(blockPos)){
                return;
            }
        System.out.println(blockPos+""+blockState. getBlock()+"");
        ConeNetManager.sendPacket(new ConeSetBlockPacket((Level)(Object)this,blockPos,blockState));
    }*/

}
