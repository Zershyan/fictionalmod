package io.zershyan.fictional.compat;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.concurrent.Callable;

public interface ICompatUtils {
    default boolean testLoadedAndRun(Runnable runnable){
        if(isModLoaded()) runnable.run();
        else return false;
        return true;
    }

    default <T> T testLoadedAndCall(Callable<T> callable, T errorResult) {
        try {
            if(isModLoaded()) return callable.call();
        } catch (Exception ignored) {}
        return errorResult;
    }

    default <T> T testLoadedAndCall(Callable<T> callable, Callable<T> elseCall, T errorResult) {
        try {
            if(isModLoaded()) return callable.call();
            else return elseCall.call();
        }catch(Exception e) {
            return errorResult;
        }
    }

    default void addCommonListener(IEventBus forgeBus, IEventBus modBus){}
    default void addClientListener(IEventBus forgeBus, IEventBus modBus){}
    default void addListener(IEventBus forgeBus, IEventBus modBus) {
        addCommonListener(forgeBus, modBus);
        if(FMLLoader.getDist() == Dist.CLIENT){
            addClientListener(forgeBus, modBus);
        }
    }

    default void init(IEventBus forgeBus, IEventBus modBus){
        addListener(forgeBus, modBus);
    }

    default void initial(IEventBus forgeBus, IEventBus modBus) {
        try { testLoadedAndRun(() -> init(forgeBus, modBus)); }
        catch (Exception e) { LogUtils.getLogger().error(e.getMessage()); }
    }

    boolean isModLoaded();
}
