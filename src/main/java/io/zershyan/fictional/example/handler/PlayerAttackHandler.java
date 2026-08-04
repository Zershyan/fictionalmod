package io.zershyan.fictional.example.handler;

import io.zershyan.fictional.common.registry.entities.BladeBeam;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.LogicalSide;

public class PlayerAttackHandler {
    public static void attack(AttackEntityEvent event) {
        Player entity = event.getEntity();
        if(entity instanceof ServerPlayer player) {
            BladeBeam.spawn(player, 10.0f)
                    .color(0xFFFFFF)
                    .damage(100)
                    .custom(bladeBeam -> bladeBeam.setDeltaMovement(
                            bladeBeam.getDeltaMovement().scale(1.5))
                    ).build();
        }
    }

    public static void shootBeam(TickEvent.PlayerTickEvent event) {
        if(event.side == LogicalSide.CLIENT) return;
        BladeBeam.spawn(event.player, 10.0f)
                .color(0xFFFFFF)
                .damage(100)
                .custom(bladeBeam -> bladeBeam.setDeltaMovement(
                        bladeBeam.getDeltaMovement().scale(1.5))
                ).build();
    }
}
