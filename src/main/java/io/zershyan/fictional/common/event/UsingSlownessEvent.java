package io.zershyan.fictional.common.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
@OnlyIn(Dist.CLIENT)
public class UsingSlownessEvent extends Event {
    private final LocalPlayer player;
    public UsingSlownessEvent(LocalPlayer player) {
        this.player = player;
    }
    public LocalPlayer getPlayer() {
        return player;
    }
}
