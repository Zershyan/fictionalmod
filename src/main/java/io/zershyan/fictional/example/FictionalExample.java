package io.zershyan.fictional.example;

import io.zershyan.fictional.example.handler.PlayerAttackHandler;
import net.minecraftforge.eventbus.api.IEventBus;

public class FictionalExample {
    public static void register(IEventBus forgeBus) {
        forgeBus.addListener(PlayerAttackHandler::attack);
    }
}
