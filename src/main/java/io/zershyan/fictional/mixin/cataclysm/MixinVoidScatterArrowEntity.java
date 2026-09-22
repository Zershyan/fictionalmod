package io.zershyan.fictional.mixin.cataclysm;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Scatter_Arrow_Entity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Void_Scatter_Arrow_Entity.class)
public class MixinVoidScatterArrowEntity {
    @WrapOperation(
            method = "onHit",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Void_Scatter_Arrow_Entity;getOwner()Lnet/minecraft/world/entity/Entity;")
    )
    public Entity modifyGetOwner(Void_Scatter_Arrow_Entity instance, Operation<Entity> original) {
        return original.call(instance) instanceof LivingEntity living ? living : null;
    }
}
