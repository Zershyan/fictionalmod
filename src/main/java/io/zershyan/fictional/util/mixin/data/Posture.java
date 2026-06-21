package io.zershyan.fictional.util.mixin.data;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class Posture {
    private int maxDuration = 0;
    private OnUseTick onUseTick = null;
    private UseOn useOn = null;
    private Use use = null;
    private FinishUsingItem finishUsingItem = null;
    private ReleaseUsing releaseUsing = null;
    private UseOnRelease useOnRelease = null;
    private GetUseAnimation getUseAnimation = null;

    public static Posture of() {
        return new Posture();
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public Posture maxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
        return this;
    }

    public OnUseTick getOnUseTick() {
        return onUseTick;
    }

    public Posture onUseTick(OnUseTick onUseTick) {
        this.onUseTick = onUseTick;
        return this;
    }

    public UseOn getUseOn() {
        return useOn;
    }

    public Posture useOn(UseOn useOn) {
        this.useOn = useOn;
        return this;
    }

    public Use getUse() {
        return use;
    }

    public Posture use(Use use) {
        this.use = use;
        return this;
    }

    public FinishUsingItem getFinishUsingItem() {
        return finishUsingItem;
    }

    public Posture finishUsingItem(FinishUsingItem finishUsingItem) {
        this.finishUsingItem = finishUsingItem;
        return this;
    }

    public ReleaseUsing getReleaseUsing() {
        return releaseUsing;
    }

    public Posture releaseUsing(ReleaseUsing releaseUsing) {
        this.releaseUsing = releaseUsing;
        return this;
    }

    public UseOnRelease getUseOnRelease() {
        return useOnRelease;
    }

    public Posture useOnRelease(UseOnRelease useOnRelease) {
        this.useOnRelease = useOnRelease;
        return this;
    }

    public GetUseAnimation getGetUseAnimation() {
        return getUseAnimation;
    }

    public Posture getUseAnimation(GetUseAnimation getUseAnimation) {
        this.getUseAnimation = getUseAnimation;
        return this;
    }

    public void copyOf(Posture posture) {
        this.maxDuration = posture.getMaxDuration();
    }

    public interface UseOn extends Function<UseOnContext, InteractionResult>{}
    public interface OnUseTick extends QuadConsumer<Level, LivingEntity, ItemStack, Integer>{}
    public interface Use extends Function4<Level, Player, InteractionHand, ItemStack, InteractionResultHolder<ItemStack>>{}
    public interface FinishUsingItem extends Function3<ItemStack, Level, LivingEntity, ItemStack>{}
    public interface ReleaseUsing extends QuadConsumer<ItemStack, Level, LivingEntity, Integer>{}
    public interface UseOnRelease extends Function<ItemStack, Boolean>{}
    public interface GetUseAnimation extends Function<ItemStack, UseAnim>{}

    @FunctionalInterface
    public interface QuadConsumer<A, B, C, D> {
        void accept(A a, B b, C c, D d);
    }
}
