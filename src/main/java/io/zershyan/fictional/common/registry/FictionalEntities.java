package io.zershyan.fictional.common.registry;

import io.zershyan.fictional.Fictional;
import io.zershyan.fictional.common.registry.entities.BladeBeam;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FictionalEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Fictional.MODID);

    public static final RegistryObject<EntityType<BladeBeam>> BLADE_BEAM = register(
            "blade_beam", EntityType.Builder
                    .<BladeBeam>of(BladeBeam::new, MobCategory.MISC)
                    .sized(3.0f, 0.5f)
                    .fireImmune()
                    .updateInterval(3)
                    .setShouldReceiveVelocityUpdates(true)
    );

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(name));
    }

    public static void register(IEventBus modBus){
        REGISTER.register(modBus);
    }
}
