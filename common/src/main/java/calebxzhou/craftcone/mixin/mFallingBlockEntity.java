package calebxzhou.craftcone.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Created  on 2023-07-01,9:57.
 */
@Mixin(FallingBlockEntity.class)
abstract class mFallingBlockEntity extends Entity {


    //广播包：成功设置方块时（掉落方块下落成功）
   /* @Inject(method = "tick",at=@At(value = "INVOKE",target = "Lnet/minecraft/server/level/ChunkMap;broadcast(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/protocol/Packet;)V"))
    private void onFallingBlockFellSuccess(CallbackInfo ci){
        BlockPos blockPos = blockPosition();
        BlockState blockState = level.getBlockState(blockPos);
        ConeNetManager.checkAndSendPacket(new ConeSetBlockPacket(level,blockPos,blockState));
    }*/




    private mFallingBlockEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }
}
