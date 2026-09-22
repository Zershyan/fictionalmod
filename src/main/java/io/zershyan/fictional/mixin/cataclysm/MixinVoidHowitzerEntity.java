package io.zershyan.fictional.mixin.cataclysm;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Howitzer_Entity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Void_Howitzer_Entity.class)
public class MixinVoidHowitzerEntity {
    @WrapOperation(
            method = "spawnFangs",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Void_Howitzer_Entity;getOwner()Lnet/minecraft/world/entity/Entity;")
    )
    public Entity modifyGetOwner(Void_Howitzer_Entity instance, Operation<Entity> original) {
        return original.call(instance) instanceof LivingEntity living ? living : null;
    }
}
