package io.zershyan.fictional;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Fictional.MODID)
public class Fictional {
    public static final String MODID = "fictional";
    public static final Logger log = LoggerFactory.getLogger(Fictional.class);

    public Fictional(FMLJavaModLoadingContext context) {

    }
}
