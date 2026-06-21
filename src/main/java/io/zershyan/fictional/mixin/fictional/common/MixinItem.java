package io.zershyan.fictional.mixin.fictional.common;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.zershyan.fictional.util.mixin.data.Posture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Item.class)
public abstract class MixinItem {

    @Shadow
    public abstract boolean isEdible();

    @Unique
    private final Posture fictional$posture = Posture.of();

    @Unique
    public Posture fictional$getPosture() {
        return fictional$posture;
    }

    @Inject(
            method = "getUseDuration",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getUseDuration(ItemStack pStack, CallbackInfoReturnable<Integer> cir) {
        if(pStack.isEdible()) return;
        int maxDuration = fictional$getPosture().getMaxDuration();
        if(maxDuration != 0) cir.setReturnValue(maxDuration);
    }

    @Inject(
            method = "onUseTick",
            at = @At("HEAD")
    )
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration, CallbackInfo ci) {
        Posture.OnUseTick onUseTick = fictional$getPosture().getOnUseTick();
        if(onUseTick != null) onUseTick.accept(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Inject(
            method = "useOn",
            at = @At("HEAD"),
            cancellable = true
    )
    public void useOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir) {
        Posture.UseOn useOn = fictional$getPosture().getUseOn();
        if(useOn != null) cir.setReturnValue(useOn.apply(pContext));
    }

    @Inject(
            method = "use",
            at = @At(value = "RETURN", ordinal = 2),
            locals = LocalCapture.CAPTURE_FAILSOFT,
            cancellable = true
    )
    public void use(Level pLevel, Player pPlayer, InteractionHand pUsedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, ItemStack stack) {
        Posture.Use use = fictional$getPosture().getUse();
        if(use != null) cir.setReturnValue(use.apply(pLevel, pPlayer, pUsedHand, stack));
    }

    @Inject(
            method = "finishUsingItem",
            at = @At("RETURN"),
            cancellable = true
    )
    public void finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, CallbackInfoReturnable<ItemStack> cir) {
        if(this.isEdible()) return;
        Posture.FinishUsingItem finishUsingItem = fictional$getPosture().getFinishUsingItem();
        if(finishUsingItem != null) cir.setReturnValue(finishUsingItem.apply(pStack, pLevel, pLivingEntity));
    }

    @Inject(
            method = "releaseUsing",
            at = @At("HEAD")
    )
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged, CallbackInfo ci) {
        Posture.ReleaseUsing releaseUsing = fictional$getPosture().getReleaseUsing();
        if(releaseUsing != null) releaseUsing.accept(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @ModifyReturnValue(
            method = "useOnRelease",
            at = @At("RETURN")
    )
    public boolean useOnRelease(boolean original, ItemStack pStack) {
        Posture.UseOnRelease useOnRelease = fictional$getPosture().getUseOnRelease();
        if(useOnRelease != null) return original || useOnRelease.apply(pStack);
        return original;
    }

    @Inject(
            method = "getUseAnimation",
            at = @At("RETURN"),
            cancellable = true
    )
    public void getUseAnimation(ItemStack pStack, CallbackInfoReturnable<UseAnim> cir) {
        if(pStack.isEdible()) return;
        Posture.GetUseAnimation getUseAnimation = fictional$getPosture().getGetUseAnimation();
        if(getUseAnimation != null) cir.setReturnValue(getUseAnimation.apply(pStack));
    }
}
