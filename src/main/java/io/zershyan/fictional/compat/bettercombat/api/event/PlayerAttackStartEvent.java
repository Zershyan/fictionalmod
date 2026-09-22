package io.zershyan.fictional.compat.bettercombat.api.event;

import net.bettercombat.api.AttackHand;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.eventbus.api.Event;

public class PlayerAttackStartEvent extends Event {
    private final LocalPlayer player;
    private final AttackHand attackHand;

    public PlayerAttackStartEvent(LocalPlayer player, AttackHand attackHand) {
        this.player = player;
        this.attackHand = attackHand;
    }

    public LocalPlayer getPlayer() {
        return player;
    }

    public AttackHand getAttackHand() {
        return attackHand;
    }
}
