package io.zershyan.fictional.mixin.legendary_monsters;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.IAnimatedBoss.CloudGolem.Cloud_GolemEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(Cloud_GolemEntity.class)
public abstract class MixinCloud_GolemEntity extends LivingEntity {
    protected MixinCloud_GolemEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyReturnValue(
            method = "addEffect",
            at = @At("RETURN")
    )
    public boolean addEffect(boolean original, MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return super.addEffect(pEffectInstance, pEntity);
    }

    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Ljava/util/Collection;clear()V")
    )
    public void clearEffect(Collection<?> instance, Operation<Void> original) {}
}
