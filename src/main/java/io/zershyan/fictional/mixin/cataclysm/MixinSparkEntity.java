package io.zershyan.fictional.mixin.cataclysm;

import com.github.L_Ender.cataclysm.entity.projectile.Spark_Entity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Spark_Entity.class)
public class MixinSparkEntity {
    @WrapOperation(
            method = "onHitBlock",
            at = @At(value = "INVOKE", target = "Lcom/github/L_Ender/cataclysm/entity/projectile/Spark_Entity;getOwner()Lnet/minecraft/world/entity/Entity;")
    )
    public Entity modifyGetOwner(Spark_Entity instance, Operation<Entity> original) {
        return original.call(instance) instanceof LivingEntity living ? living : null;
    }
}
