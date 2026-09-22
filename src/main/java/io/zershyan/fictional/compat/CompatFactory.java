package io.zershyan.fictional.compat;

import io.zershyan.fictional.compat.bettercombat.BCCompat;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.HashSet;
import java.util.Set;

public class CompatFactory {
    private static final Set<ICompatUtils> compatUtils = new HashSet<>();

    public static final BCCompat BCCompat = addCompat(new BCCompat());

    private static <T extends ICompatUtils> T addCompat(T utils) {
        if (compatUtils.add(utils)) return utils;
        else throw new IllegalStateException("CompatUtils already added.");
    }

    public static void register(IEventBus forgeEventBus, IEventBus modEventBus) {
        compatUtils.forEach(compatUtils -> compatUtils.initial(forgeEventBus, modEventBus));
    }
}
