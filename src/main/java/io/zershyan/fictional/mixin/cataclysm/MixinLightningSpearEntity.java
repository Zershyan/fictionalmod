package io.zershyan.fictional.mixin.cataclysm;

import com.github.L_Ender.cataclysm.entity.projectile.Lightning_Spear_Entity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Lightning_Spear_Entity.class)
public abstract class MixinLightningSpearEntity {
    @WrapOperation(
            method = "onHitBlock",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Lightning_Spear_Entity;getOwner()Lnet/minecraft/world/entity/Entity;")
    )
    public Entity modifyGetOwner(Lightning_Spear_Entity instance, Operation<Entity> original) {
        return original.call(instance) instanceof LivingEntity living ? living : null;
    }
}
