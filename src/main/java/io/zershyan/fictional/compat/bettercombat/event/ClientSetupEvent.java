package io.zershyan.fictional.compat.bettercombat.event;

import io.zershyan.fictional.compat.bettercombat.api.event.PlayerAttackStartEvent;
import net.bettercombat.api.client.BetterCombatClientEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetupEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BetterCombatClientEvents.ATTACK_START.register((localPlayer, attackHand) ->
                MinecraftForge.EVENT_BUS.post(new PlayerAttackStartEvent(localPlayer, attackHand))
        );
    }
}
