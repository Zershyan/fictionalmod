package io.zershyan.fictional.common.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

@OnlyIn(Dist.CLIENT)
public class SprintWithUsingItemEvent extends Event {
    private final LocalPlayer player;
    private boolean canSpirit = false;
    public SprintWithUsingItemEvent(LocalPlayer player) {
        this.player = player;
    }

    public LocalPlayer getPlayer() {
        return player;
    }

    public boolean canSpirit() {
        return canSpirit;
    }

    public void setCanSpirit(boolean canSpirit) {
        this.canSpirit = canSpirit;
    }
}
