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

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("io.zershyan." + Fictional.MODID + ".mixin.ba_bt.")) {
            return LoadingModList.get().getMods().stream().map(ModInfo::getModId).anyMatch(
                    s -> s.equals("ba_bt")
            );
        }
        if (mixinClassName.startsWith("io.zershyan." + Fictional.MODID + ".mixin.cataclysm.")) {
            return LoadingModList.get().getMods().stream().map(ModInfo::getModId).anyMatch(
                    s -> s.equals("cataclysm")
            );
        }
        if (mixinClassName.startsWith("io.zershyan." + Fictional.MODID + ".mixin.mutantmore.")) {
            return LoadingModList.get().getMods().stream().map(ModInfo::getModId).anyMatch(
                    s -> s.equals("mutantmore")
            );
        }
        if (mixinClassName.startsWith("io.zershyan." + Fictional.MODID + ".mixin.mutantmonsters.")) {
            List<ModInfo> list = LoadingModList.get().getMods().stream().filter(
                    modInfo -> modInfo.getModId().equals("mutantmonsters")
            ).toList();
            if (list.isEmpty()) return false;
            ArtifactVersion version = list.get(0).getVersion();
            if(version.getMajorVersion() == 8
                    && version.getMinorVersion() == 0
                    && version.getIncrementalVersion() == 7
            ) return true;
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
