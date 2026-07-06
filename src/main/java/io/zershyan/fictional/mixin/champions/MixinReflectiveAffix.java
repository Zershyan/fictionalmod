package io.zershyan.fictional.mixin.champions;

import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.ReflectiveAffix;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ReflectiveAffix.class, remap = false)
public class MixinReflectiveAffix {
    @Unique
    private static final List<Integer> fictional$HasBeenReflected = new ArrayList<>();

    @Inject(
            method = "onDamage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void addReflected(IChampion champion, DamageSource source, float amount, float newAmount, CallbackInfoReturnable<Float> cir) {
        int championId = champion.getLivingEntity().getId();
        if(fictional$HasBeenReflected.contains(championId)) {
            cir.setReturnValue(amount);
            cir.cancel();
        } else fictional$HasBeenReflected.add(championId);
    }

    @Inject(
            method = "onDamage",
            at = @At("RETURN")
    )
    public void removeReflected(IChampion champion, DamageSource source, float amount, float newAmount, CallbackInfoReturnable<Float> cir) {
        int championId = champion.getLivingEntity().getId();
        fictional$HasBeenReflected.remove(Integer.valueOf(championId));
    }
}
