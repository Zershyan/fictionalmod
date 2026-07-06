package io.zershyan.fictional.mixin.mutantmore;

import com.alexander.mutantmore.entities.IcicleSpike;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(IcicleSpike.class)
public class MixinIcicleSpike {
    @Shadow(remap = false)
    public LivingEntity owner;

    @WrapOperation(
            method = "addAdditionalSaveData",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/CompoundTag;putUUID(Ljava/lang/String;Ljava/util/UUID;)V"
            )
    )
    public void addAdditionalSaveData(CompoundTag instance, String pKey, UUID pValue, Operation<Void> original) {
        if(this.owner != null) original.call(instance, pKey, this.owner.getUUID());
    }
}
