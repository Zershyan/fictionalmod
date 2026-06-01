package io.zershyan.fictional.mixin.fictional.client;

import io.zershyan.fictional.network.Channel;
import io.zershyan.fictional.network.toserver.AttackInUsingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Final
    public Options options;

    @Inject(
            method = "handleKeybinds",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"
            )
    )
    public void attackInUsingItem(CallbackInfo ci) {
        if(this.player != null && this.player.isUsingItem()) {
            if(this.options.keyAttack.isDown()) {
                Channel.sendToServer(new AttackInUsingPacket());
            }
        }
    }
}
