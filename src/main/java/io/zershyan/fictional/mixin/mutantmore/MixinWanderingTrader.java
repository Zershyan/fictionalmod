package io.zershyan.fictional.mixin.mutantmore;

import net.minecraft.world.entity.npc.WanderingTrader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WanderingTrader.class, priority = 999)
public class MixinWanderingTrader {
    @Inject(
            method = "updateTrades",
            at = @At("TAIL"),
            cancellable = true
    )
    private void onUpdateTrades(CallbackInfo ci) {
        ci.cancel();
    }
}
