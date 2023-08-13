package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.misc.NeighborUpdateManager;
import calebxzhou.craftcone.net.ConeNetSender;
import calebxzhou.craftcone.net.protocol.game.SetBlockC2CPacket;
import calebxzhou.craftcone.utils.LevelUt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created  on 2023-07-15,23:22.
 */

@Mixin(BlockItem.class)
public class mBlockItem {
    //广播包：玩家成功设置方块时（通常为放置）
    @Inject(method = "place",at=@At(value = "INVOKE",target = "Lnet/minecraft/world/InteractionResult;sidedSuccess(Z)Lnet/minecraft/world/InteractionResult;"))
    private void onPlayerPlace(BlockPlaceContext blockPlaceContext, CallbackInfoReturnable<InteractionResult> cir){
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        //Player player = blockPlaceContext.getPlayer();
        //ItemStack itemStack = blockPlaceContext.getItemInHand();
        BlockState blockState2 = level.getBlockState(blockPos);
        //2. 不发送neighbor update方块
        if(NeighborUpdateManager.has(blockPos)){
            return;
        }
        ConeNetSender.sendPacket(new SetBlockC2CPacket(LevelUt.getDimIdByLevel(level),
                blockPos.asLong(), Block.BLOCK_STATE_REGISTRY.getId(blockState2)));
    }
}
