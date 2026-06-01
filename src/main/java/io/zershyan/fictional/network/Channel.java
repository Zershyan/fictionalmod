package io.zershyan.fictional.network;

import io.zershyan.fictional.Fictional;
import io.zershyan.fictional.network.toserver.AttackInUsingPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


public class Channel {
    private static int cid = 0;
    private static final String PROTOCOL_VERSION = ModList.get()
            .getModContainerById(Fictional.MODID)
            .map(c -> c.getModInfo().getVersion().toString())
            .orElse("unknown");
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(Fictional.MODID, Fictional.MODID),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {

        //server
        INSTANCE.messageBuilder(AttackInUsingPacket.class, cid++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(buf -> new AttackInUsingPacket())
                .encoder(((packet, buf) -> {}))
                .consumerMainThread(AttackInUsingPacket::handle)
                .add();
    }

    public static int getCid() {
        return cid++;
    }

    public static <MSG> void sendAllPlayer(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
    }
}
