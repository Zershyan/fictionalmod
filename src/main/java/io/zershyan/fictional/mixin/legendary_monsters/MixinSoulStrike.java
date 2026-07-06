package io.zershyan.fictional.mixin.legendary_monsters;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SoulStrike;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoulStrike.class)
public class MixinSoulStrike {
    @Inject(
            method = "onUpdateInAir",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    public void onUpdateInAir(CallbackInfo ci) {
        SoulStrike self = SoulStrike.class.cast(this);
        if(self.getOwner() instanceof LivingEntity) return;
        ci.cancel();
    }
}
