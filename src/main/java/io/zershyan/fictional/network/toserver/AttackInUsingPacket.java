package io.zershyan.fictional.network.toserver;

import io.zershyan.fictional.common.event.LeftClickInUsingEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record AttackInUsingPacket() {
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            context.setPacketHandled(true);
            ServerPlayer sender = context.getSender();
            if(sender == null) return;
            if(!sender.isUsingItem()) return;
            ItemStack useItem = sender.getUseItem();
            int ticksUsingItem = sender.getTicksUsingItem();
            LeftClickInUsingEvent event = new LeftClickInUsingEvent(sender, useItem, ticksUsingItem);
            MinecraftForge.EVENT_BUS.post(event);
            if(event.isStopUsing()) sender.stopUsingItem();
        });
    }
}
