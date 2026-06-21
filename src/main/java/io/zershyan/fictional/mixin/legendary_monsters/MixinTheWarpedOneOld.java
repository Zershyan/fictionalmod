package io.zershyan.fictional.mixin.legendary_monsters;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.miauczel.legendary_monsters.entity.AnimatedMonster.Chorusling.TheWarpedOne.TheWarpedOneOld;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TheWarpedOneOld.class)
public abstract class MixinTheWarpedOneOld extends LivingEntity {
    protected MixinTheWarpedOneOld(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyReturnValue(
            method = "addEffect",
            at = @At("RETURN")
    )
    public boolean addEffect(boolean original, MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return super.addEffect(pEffectInstance, pEntity);
    }
}
