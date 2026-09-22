package io.zershyan.fictional;

import io.zershyan.fictional.client.registry.FictionalModels;
import io.zershyan.fictional.client.registry.FictionalRenderers;
import io.zershyan.fictional.common.registry.FictionalEntities;
import io.zershyan.fictional.compat.CompatFactory;
import io.zershyan.fictional.example.FictionalExample;
import io.zershyan.fictional.network.Channel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Fictional.MODID)
public class Fictional {
    public static final String MODID = "fictional";
    public static final Logger log = LoggerFactory.getLogger(Fictional.class);

    public Fictional(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        Channel.register();
        FictionalEntities.register(modBus);
        CompatFactory.register(forgeBus, modBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FictionalRenderers.register(modBus);
            FictionalModels.register(modBus);
        });

        if(!FMLEnvironment.production) {
            FictionalExample.register(forgeBus);
        }
    }
}
