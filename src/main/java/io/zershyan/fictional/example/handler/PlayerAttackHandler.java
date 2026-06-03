package io.zershyan.fictional.example.handler;

import io.zershyan.fictional.common.registry.entities.BladeBeam;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class PlayerAttackHandler {
    public static void attack(AttackEntityEvent event) {
        Player entity = event.getEntity();
        if(entity instanceof ServerPlayer player) {
            BladeBeam.spawn(player, 10.0f)
                    .color(0xFFFFFF)
                    .custom(bladeBeam -> bladeBeam.setDeltaMovement(
                            bladeBeam.getDeltaMovement().scale(1.5))
                    ).build();
        }
    }
}
