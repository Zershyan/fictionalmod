package io.zershyan.fictional.mixin.goetyawaken;

import com.k1sak1.goetyawaken.common.entities.projectiles.TrackingFireball;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TrackingFireball.class)
public class MixinTrackingFireball {
    @WrapOperation(
            method = "onHitEntity",
            at = @At(value = "INVOKE", target = "Lcom/k1sak1/goetyawaken/common/entities/projectiles/TrackingFireball;getOwner()Lnet/minecraft/world/entity/Entity;")
    )
    public Entity modifyOwner(TrackingFireball instance, Operation<Entity> original) {
        Entity owner = original.call(instance);
        if (!(owner instanceof LivingEntity)) return null;
        return owner;
    }
}