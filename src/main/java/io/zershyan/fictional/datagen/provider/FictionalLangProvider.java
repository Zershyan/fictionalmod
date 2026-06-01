package io.zershyan.fictional.datagen.provider;

import io.zershyan.fictional.Fictional;
import io.zershyan.fictional.datagen.init.FictionalLang;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;


public class FictionalLangProvider extends LanguageProvider {
    private final Lang lang;

    public FictionalLangProvider(PackOutput output, Lang lang) {
        super(output, Fictional.MODID, lang.getLangName());
        FictionalLang.initLang();
        this.lang = lang;
    }

    @Override
    protected void addTranslations() {
        switch (lang){
            case EN_US -> FictionalLang.langList.forEach(langEntity -> addTranslation(
                    langEntity.key(), langEntity.lang().enUs())
            );
            case ZH_CN -> FictionalLang.langList.forEach(langEntity -> addTranslation(
                    langEntity.key(), langEntity.lang().zhCn())
            );
        }
    }

    private <T> void addTranslation(T o, String string) {
        if(o instanceof Item object){
            add(object,string);
        }else if(o instanceof Block object){
            add(object,string);
        }else if(o instanceof String object){
            add(object,string);
        }else if(o instanceof ItemStack object){
            add(object,string);
        }else if(o instanceof Enchantment object){
            add(object,string);
        }else if(o instanceof MobEffect object){
            add(object,string);
        }else if(o instanceof EntityType<?> object) {
            add(object,string);
        }else {
            Fictional.log.error("Unknown object type: {}", o.getClass());
            add(o.toString(),string);
        }
    }

    public enum Lang{
        
        ZH_CN("zh_cn"), EN_US("en_us");

        private final String langName;
        Lang(String langName) {
            this.langName = langName;
        }
        public String getLangName() {
            return langName;
        }
    }
}
