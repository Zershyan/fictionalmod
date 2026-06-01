package io.zershyan.fictional.client.registry;

import io.zershyan.fictional.common.registry.FictionalEntities;
import io.zershyan.fictional.common.registry.entities.renderer.BladeBeamRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class FictionalRenderers {
    public static void addRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(FictionalEntities.BLADE_BEAM.get(), BladeBeamRenderer::new);
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(FictionalRenderers::addRenderer);
    }
}