package io.zershyan.fictional.datagen;

import io.zershyan.fictional.Fictional;
import io.zershyan.fictional.datagen.provider.FictionalLangProvider;
import io.zershyan.fictional.datagen.provider.FictionalPackMetadataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fictional.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();

		generator.addProvider(event.includeServer(), new FictionalPackMetadataProvider(packOutput));
		generator.addProvider(event.includeClient(), new FictionalLangProvider(packOutput, FictionalLangProvider.Lang.EN_US));
		generator.addProvider(event.includeClient(), new FictionalLangProvider(packOutput, FictionalLangProvider.Lang.ZH_CN));
	}
}
