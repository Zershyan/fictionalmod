package io.zershyan.fictional.mixin;

import io.zershyan.fictional.Fictional;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class FictionalMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    private static final String[] MixinMods = {
            "ba_bt", "cataclysm", "mutantmore", "legendary_monsters", "champions",
            "beyondstorage"
    };

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        List<ModInfo> modInfos = LoadingModList.get().getMods();
        List<String> modList = modInfos.stream().map(ModInfo::getModId).toList();

        for (String modid : MixinMods) {
            if (mixinClassName.startsWith(this.getClass().getPackageName() + "." + modid + ".")) {
                return modList.contains(modid);
            }
        }
        if (mixinClassName.startsWith("io.zershyan." + Fictional.MODID + ".mixin.mutantmonsters.")) {
            ModInfo info = modInfos.stream().filter(modInfo -> modInfo.getModId().equals("mutantmonsters")).findAny().orElse(null);
            if(info == null) return false;
            ArtifactVersion version = info.getVersion();
            return version.getMajorVersion() == 8 && version.getMinorVersion() == 0 && version.getIncrementalVersion() == 7;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
