package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConePacketSender;
import calebxzhou.craftcone.net.protocol.ConeExplodePacket;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created  on 2023-07-01,16:49.
 */
@Mixin(ServerLevel.class)
abstract class mServerLevel extends Level {

    private mServerLevel(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, holder, supplier, bl, bl2, l, i);
    }

    //广播包：爆炸
   // @Inject(method = "explode",at=@At(value = "RETURN"),locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onExplode(@Nullable Entity exploder,
                           @Nullable DamageSource damageSource,
                           @Nullable ExplosionDamageCalculator context,
                           double x,
                           double y,
                           double z,
                           float size,
                           boolean causesFire,
                           Explosion.BlockInteraction mode,
                           CallbackInfoReturnable<Explosion> cir,Explosion explosion){
        ConePacketSender.checkAndSendPacket(new ConeExplodePacket(this, (float) x, (float) y, (float) z,size,explosion.getToBlow()));
    }
}
