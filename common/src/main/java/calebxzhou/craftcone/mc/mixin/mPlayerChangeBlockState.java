package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.entity.ConeRoom;
import calebxzhou.craftcone.net.ConeNetSender;
import calebxzhou.craftcone.net.protocol.game.SetBlockPacket;
import calebxzhou.craftcone.utils.LevelUt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

/**
 * Created  on 2023-07-15,23:39.
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public class mPlayerChangeBlockState {
    //右键点击完方块，就发出它的新状态
    @Inject(method = "use",at=@At(value = "RETURN"))
    private void onRightClickBlock(Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir){
        Objects.requireNonNull(ConeRoom.getNow()).onRightClickBlock(level,blockHitResult.getBlockPos());
        //
    }
}
