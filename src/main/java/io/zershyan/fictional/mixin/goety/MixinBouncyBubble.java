package io.zershyan.fictional.mixin.goety;

import com.Polarice3.Goety.common.entities.projectiles.BouncyBubble;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BouncyBubble.class)
public class MixinBouncyBubble {
    @WrapOperation(
            method = "hurt",
            at = @At(value = "INVOKE", target = "Lcom/Polarice3/Goety/common/entities/projectiles/BouncyBubble;explode()V", remap = false)
    )
    public void explode(BouncyBubble instance, Operation<Void> original, DamageSource source) {
        if(!source.is(DamageTypeTags.IS_EXPLOSION)) {
            original.call(instance);
        }
    }
}
