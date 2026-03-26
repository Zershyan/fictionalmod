package io.zershyan.fictional.mixin.mutantmonsters;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fuzs.mutantmonsters.world.entity.mutant.AbstractMutantMonster;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(MutantZombie.class)
public abstract class MixinMutantZombie extends AbstractMutantMonster {
    protected MixinMutantZombie(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(
            method = "tickDeath",
            at = @At(
                    value = "INVOKE",
                    target = "Lfuzs/mutantmonsters/world/entity/mutant/MutantZombie;discard()V"
            )
    )
    public void tickDeath(MutantZombie instance, Operation<Void> original) {
        if(!this.level().isClientSide()) original.call(instance);
    }
}
