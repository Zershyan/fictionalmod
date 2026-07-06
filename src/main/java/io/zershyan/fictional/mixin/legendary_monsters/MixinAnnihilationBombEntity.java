package io.zershyan.fictional.mixin.legendary_monsters;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.AnnihilationBombEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnnihilationBombEntity.class)
public class MixinAnnihilationBombEntity {
    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true
    )
    public void testOwner(CallbackInfo ci) {
        AnnihilationBombEntity self = AnnihilationBombEntity.class.cast(this);
        if(self.getOwner() instanceof LivingEntity) return;
        self.discard();
        ci.cancel();
    }
}
