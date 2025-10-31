package xyz.meowing.knit.mixins;

//#if MC > 1.20.1
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.meowing.knit.Knit;
import xyz.meowing.knit.internal.events.TickEvent;

@Mixin(ClientCommonNetworkHandler.class)
public class MixinClientCommonNetworkHandler {
    @Inject(method = "onPing", at = @At("HEAD"))
    private void zen$onPingStart(CommonPingS2CPacket packet, CallbackInfo ci) {
       Knit.getEventBus().post(new TickEvent.Server.Start());
    }

    @Inject(method = "onPing", at = @At("TAIL"))
    private void zen$onPingEnd(CommonPingS2CPacket packet, CallbackInfo ci) {
        Knit.getEventBus().post(new TickEvent.Server.End());
    }
}
//#else
//$$ import org.spongepowered.asm.mixin.Mixin;
//$$
//#if FORGE-LIKE
//$$ @Mixin(net.minecraft.client.multiplayer.ClientPacketListener.class)
//#else
//$$ @Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
//#endif
//$$ public class MixinClientCommonNetworkHandler {
//$$    // No impl for 1.20 yet.
//$$ }
//#endif