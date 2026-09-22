package io.zershyan.fictional.compat.bettercombat;

import io.zershyan.fictional.compat.ICompatUtils;
import io.zershyan.fictional.compat.bettercombat.event.ClientSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class BCCompat implements ICompatUtils {
    public static final String MODID = "bettercombat";
    @Override
    public boolean isModLoaded() {
        return ModList.get().isLoaded(MODID);
    }

    @Override
    public void addClientListener(IEventBus forgeBus, IEventBus modBus) {
        modBus.register(ClientSetupEvent.class);
    }
}
