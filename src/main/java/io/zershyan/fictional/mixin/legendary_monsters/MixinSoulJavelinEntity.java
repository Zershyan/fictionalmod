package io.zershyan.fictional.mixin.legendary_monsters;

import net.miauczel.legendary_monsters.entity.AnimatedMonster.Projectile.SoulJavelinEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulJavelinEntity.class)
public abstract class MixinSoulJavelinEntity extends AbstractArrow {

    protected MixinSoulJavelinEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity target) {
        return super.canHitEntity(target) && target instanceof LivingEntity;
    }
}
