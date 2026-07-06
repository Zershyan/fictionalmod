package io.zershyan.fictional.mixin.goety;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Apostle.class)
public class MixinApostle {
    @Inject(
            method = "teleportTowards",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    public void teleportTowards(Entity entity, CallbackInfo ci) {
        if(Apostle.class.cast(this).level() == entity.level()) return;
        ci.cancel();
    }

    @WrapOperation(
            method = "teleport",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getY()D")
    )
    public double teleport(LivingEntity instance, Operation<Double> original) {
        Apostle apostle = Apostle.class.cast(this);
        if(instance.level() == apostle.level()) return original.call(instance);
        return apostle.getY();
    }
}
