package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.entity.ConeRoom;
import calebxzhou.craftcone.misc.NeighborUpdateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

/**
 * Created  on 2023-07-15,23:22.
 */

@Mixin(BlockItem.class)
public class mPlayerPlaceBlock {
    //广播包：玩家成功设置方块时（通常为放置）
    @Inject(method = "place",at=@At(value = "INVOKE",target = "Lnet/minecraft/world/InteractionResult;sidedSuccess(Z)Lnet/minecraft/world/InteractionResult;"))
    private void onPlayerPlace(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir){
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        //2. 不发送neighbor update方块
        if(NeighborUpdateManager.has(blockPos)){
            return;
        }
        Objects.requireNonNull(ConeRoom.getNow()).onPlayerPlaceBlock(level,blockPos);
    }
}
