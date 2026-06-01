package io.zershyan.fictional.common.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LeftClickInUsingEvent extends PlayerEvent {
    private final ItemStack usingItem;
    private final int usingTicks;
    private boolean stopUsing;
    public LeftClickInUsingEvent(Player player, ItemStack usingItem, int usingTicks) {
        super(player);
        this.usingItem = usingItem;
        this.usingTicks = usingTicks;
        this.stopUsing = false;
    }

    public ItemStack getUsingItem() {
        return usingItem;
    }

    public int getUsingTicks() {
        return usingTicks;
    }

    public boolean isStopUsing() {
        return stopUsing;
    }

    public void setStopUsing(boolean stopUsing) {
        this.stopUsing = stopUsing;
    }
}
