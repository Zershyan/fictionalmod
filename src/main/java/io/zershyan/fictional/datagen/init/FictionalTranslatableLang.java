package io.zershyan.fictional.datagen.init;

import io.zershyan.fictional.Fictional;

public enum FictionalTranslatableLang {
    RESOURCES(new FictionalLang.LangEntity<>(
            Fictional.MODID + ".resources",
            "Resources for " + Fictional.class.getSimpleName(),
            "Resources for " + Fictional.class.getSimpleName()
    ))
    ;

    public final FictionalLang.LangEntity<String> langEntity;

    FictionalTranslatableLang(FictionalLang.LangEntity<String> lang) {
        this.langEntity = lang;
    }

    public String getKey() {
        return langEntity.key();
    }
}
