package calebxzhou.craftcone.mixin;

import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Created  on 2023-06-20,22:21.
 */
@Mixin(Connection.class)
public class mConnection {



    //local c发给local s的包，转发给cc服务器（登录过程，eg，握手/申请加入 etc）
    //本地C2S包
 /*   @Inject(method = "doSendPacket",at=@At(value = "INVOKE",target = "Lio/netty/channel/Channel;writeAndFlush(Ljava/lang/Object;)Lio/netty/channel/ChannelFuture;"))
    private void broadcastPacket(Packet<?> packet, @Nullable PacketSendListener packetSendListener, ConnectionProtocol connectionProtocol, ConnectionProtocol connectionProtocol2, CallbackInfo ci){
            NetworkManager.sendPacket(packet);
    }
*/

    //local c收到local s的包，转发给cc服务器（游玩过程，eg，放置方块，生成实体 etc）
    //本地S2C包
  /*  @Inject(method = "genericsFtw",
            at=@At(value = "HEAD" ))
    private static void onRecv(Packet<? extends PacketListener> packet, PacketListener packetListener, CallbackInfo ci){
        NetworkManager.sendPacket(packet);
    }*/

}