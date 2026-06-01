package io.zershyan.fictional.common.registry.entities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.zershyan.fictional.Fictional;
import io.zershyan.fictional.client.registry.FictionalModels;
import io.zershyan.fictional.common.registry.entities.BladeBeam;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BladeBeamRenderer extends EntityRenderer<BladeBeam> {

    private final BakedModel model;
    public BladeBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = pContext.getModelManager().getModel(FictionalModels.bladeBeam);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BladeBeam pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Fictional.MODID, "models/props/blade_beam.png");
    }

    @Override
    public void render(
            @NotNull BladeBeam pEntity,
            float pEntityYaw,
            float pPartialTick,
            @NotNull PoseStack pPoseStack,
            @NotNull MultiBufferSource pBuffer,
            int pPackedLight
    ) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.0f, 0.25f, 0.0f);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
        pPoseStack.mulPose(Axis.YN.rotationDegrees(pEntity.getYRot()));
        pPoseStack.mulPose(Axis.XN.rotationDegrees(pEntity.getXRot()));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntity.getZpRotation()));
        pPoseStack.scale(4.0f, 4.0f, 4.0f);

        float[] floatColors = pEntity.getColor();
        float fadeProgress = pEntity.getFadeProgress(pPartialTick);
        float alpha = fadeProgress == -1.0f ? 1.0f : 1.0f - fadeProgress;
        alpha *= pEntity.getAlpha();
        AlphaBufferSource bufferSource = new AlphaBufferSource(pBuffer);
        bufferSource.setAlpha(alpha);
        RenderType pRenderType = RenderType.translucent();
        VertexConsumer buffer = bufferSource.getBuffer(pRenderType);
        for (BakedQuad quad : model.getQuads(null, null, RandomSource.create(), ModelData.EMPTY, null)) {
            buffer.putBulkData(pPoseStack.last(), quad, floatColors[0], floatColors[1], floatColors[2], pPackedLight, OverlayTexture.NO_OVERLAY);
        }

        pPoseStack.popPose();
    }

    public static class AlphaBufferSource implements MultiBufferSource {
        private final MultiBufferSource inner;
        private float alpha = 1.0f;
        public AlphaBufferSource(MultiBufferSource inner) {
            this.inner = inner;
        }

        @Override
        public @NotNull VertexConsumer getBuffer(@NotNull RenderType pRenderType) {
            VertexConsumer buffer = inner.getBuffer(pRenderType);
            if(alpha >= 1.0) return buffer;
            return new AlphaVertexConsumer(buffer, alpha);
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public float getAlpha() {
            return alpha;
        }

        public record AlphaVertexConsumer(VertexConsumer innerConsumer, float alpha) implements VertexConsumer {

            @Override
            public @NotNull AlphaVertexConsumer vertex(double pX, double pY, double pZ) {
                innerConsumer.vertex(pX, pY, pZ);
                return this;
            }

            @Override
            public @NotNull AlphaVertexConsumer color(int pRed, int pGreen, int pBlue, int pAlpha) {
                innerConsumer.color(pRed, pGreen, pBlue, (int) (pAlpha * alpha));
                return this;
            }

            @Override
            public @NotNull AlphaVertexConsumer color(float pRed, float pGreen, float pBlue, float pAlpha) {
                innerConsumer.color(pRed, pGreen, pBlue, (pAlpha * alpha));
                return this;
            }

            @Override
            public @NotNull AlphaVertexConsumer uv(float pU, float pV) {
                innerConsumer.uv(pU, pV);
                return this;
            }

            @Override
            public @NotNull AlphaVertexConsumer overlayCoords(int pU, int pV) {
                innerConsumer.overlayCoords(pU, pV);
                return this;
            }

            @Override
            public @NotNull AlphaVertexConsumer uv2(int pU, int pV) {
                innerConsumer.uv2(pU, pV);
                return this;
            }

            @Override
            public @NotNull AlphaVertexConsumer normal(float pX, float pY, float pZ) {
                innerConsumer.normal(pX, pY, pZ);
                return this;
            }

            @Override
            public void endVertex() {
                innerConsumer.endVertex();
            }

            @Override
            public void defaultColor(int pDefaultR, int pDefaultG, int pDefaultB, int pDefaultA) {
                innerConsumer.defaultColor(pDefaultR, pDefaultG, pDefaultB, pDefaultA);
            }

            @Override
            public void unsetDefaultColor() {
                innerConsumer.unsetDefaultColor();
            }
        }
    }
}
