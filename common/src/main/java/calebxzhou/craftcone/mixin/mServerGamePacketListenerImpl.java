package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.utils.NetUtils;
import com.google.gson.Gson;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-07-12,12:33.
 */
@Mixin(ServerGamePacketListenerImpl.class)
public class mServerGamePacketListenerImpl {
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V",at=@At("HEAD"))

    private void onSendPacket(Packet<?> packet, @Nullable PacketSendListener packetSendListener, CallbackInfo ci){
        if(packet instanceof ClientboundContainerSetSlotPacket
        || packet instanceof ClientboundContainerSetContentPacket
    || packet instanceof ClientboundContainerSetDataPacket
        || packet instanceof ClientboundContainerClosePacket)
        {
            NetUtils.INSTANCE.printAllData(packet);
        }
    }
}
