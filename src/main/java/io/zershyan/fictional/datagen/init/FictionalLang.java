package io.zershyan.fictional.datagen.init;

import io.zershyan.fictional.Fictional;
import io.zershyan.fictional.common.registry.FictionalEntities;

import java.util.ArrayList;
import java.util.List;

public class FictionalLang {
	public record Lang(String zhCn, String enUs) {}
	public record LangEntity<T>(T key, Lang lang) {
		public LangEntity(T key, String zhCn, String enUs) {
			this(key, new Lang(zhCn, enUs));
		}
	}
	public static final List<LangEntity<?>> langList = new ArrayList<>();
	public final static String translationString = "translation." + Fictional.MODID;

	public static void initLang() {
		langList.clear();

		//entity
		langList.add(new LangEntity<>(
				FictionalEntities.BLADE_BEAM.get(),
				"剑气",
				"Blade Beam"
		));

		for (FictionalTranslatableLang value : FictionalTranslatableLang.values()) {
			FictionalLang.langList.add(value.langEntity);
		}
	}
}
