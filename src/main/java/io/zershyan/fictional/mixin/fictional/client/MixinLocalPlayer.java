package io.zershyan.fictional.mixin.fictional.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.zershyan.fictional.common.event.SprintWithUsingItemEvent;
import io.zershyan.fictional.common.event.UsingSlownessEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @WrapOperation(
            method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z")
    )
    private boolean cancelUsingThoracotomyItemSlowness(LocalPlayer instance, Operation<Boolean> original) {
        return original.call(instance) && !MinecraftForge.EVENT_BUS.post(new UsingSlownessEvent(instance));
    }

    @WrapOperation(
            method = "aiStep",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", ordinal = 1)
    )
    private boolean canUsingItemSprinting(LocalPlayer instance, Operation<Boolean> original) {
        SprintWithUsingItemEvent event = new SprintWithUsingItemEvent(instance);
        MinecraftForge.EVENT_BUS.post(event);
        return original.call(instance) && !event.canSpirit();
    }
}
