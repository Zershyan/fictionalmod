package io.zershyan.fictional.mixin.goety;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "com.Polarice3.Goety.common.entities.hostile.servants.Malghast$MoveHelperController")
public class MixinMalghastMoveHelperController {
    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;ceil(D)I")
    )
    public int callD0(double pValue, Operation<Integer> original) {
        Integer call = original.call(pValue);
        return call > 128 ? 128 : call;
    }
}
