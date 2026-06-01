package io.zershyan.fictional.client.registry;

import io.zershyan.fictional.Fictional;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class FictionalModels {
    public static final ResourceLocation bladeBeam = ResourceLocation.fromNamespaceAndPath(Fictional.MODID, "blade_beam/blade_beam");

    public static void registerModels(ModelEvent.RegisterAdditional event) {
        event.register(bladeBeam);
    }

    public static void register(IEventBus bus) {
        bus.addListener(FictionalModels::registerModels);
    }
}
