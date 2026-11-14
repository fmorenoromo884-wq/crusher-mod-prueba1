package com.fer.crusher;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

/** Dibuja la rueda de molino (models/block/crusher_wheel.json) girando sobre el eje vertical. */
public class CrusherRenderer implements BlockEntityRenderer<CrusherBlockEntity> {
    public static final ModelResourceLocation WHEEL_MODEL = ModelResourceLocation.standalone(
            ResourceLocation.fromNamespaceAndPath(CrusherMod.MODID, "block/crusher_wheel"));

    public CrusherRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CrusherBlockEntity be, float partialTick, PoseStack pose, MultiBufferSource buffer,
                       int packedLight, int packedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        BakedModel model = mc.getModelManager().getModel(WHEEL_MODEL);

        boolean lit = be.getBlockState().getValue(CrusherBlock.LIT);
        float angle = be.wheelAngle + (lit ? partialTick * CrusherBlockEntity.WHEEL_SPEED : 0f);

        pose.pushPose();
        // La rueda gira sobre el centro del bloque (8, y, 8) en el eje Y
        pose.translate(0.5, 0.0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(angle));
        pose.translate(-0.5, 0.0, -0.5);
        mc.getBlockRenderer().getModelRenderer().renderModel(
                pose.last(), buffer.getBuffer(RenderType.cutout()), null, model,
                1f, 1f, 1f, packedLight, packedOverlay);
        pose.popPose();
    }
}
