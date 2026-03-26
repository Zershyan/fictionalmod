package io.zershyan.fictional.mixin.ba_bt;

import com.brass_amber.ba_bt.worldGen.structures.LandTower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(LandTower.class)
public class MixinLandTower {
    @Shadow(remap = false)
    private int towerType;

    @Inject(
            method = "checkVariant",
            at =@At("TAIL"),
            remap = false
    )
    private void checkVariant(Structure.GenerationContext context, BlockPos blockpos, CallbackInfo ci) {
        if(this.towerType == 1) this.towerType = context.random().nextInt(50) > 7 ? 0 : 4;
    }
}
