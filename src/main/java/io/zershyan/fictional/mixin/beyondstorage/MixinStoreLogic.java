package io.zershyan.fictional.mixin.beyondstorage;

import com.example.beyondstorage.network.StoreLogic;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(StoreLogic.class)
public class MixinStoreLogic {
    @Inject(
            method = "transferItemsToNetwork",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/Slot;remove(I)Lnet/minecraft/world/item/ItemStack;",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void transferItemsToNetwork(ServerPlayer player, AbstractContainerMenu container, CallbackInfo ci, @Local(name = "slot") Slot slot) {
        slot.setChanged();
    }
}
