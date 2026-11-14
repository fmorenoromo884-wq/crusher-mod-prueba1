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

/** Dibuja el rodillo dentado (models/block/crusher_roller.json) girando sobre el eje X. */
public class CrusherRenderer implements BlockEntityRenderer<CrusherBlockEntity> {
    public static final ModelResourceLocation ROLLER_MODEL = ModelResourceLocation.standalone(
            ResourceLocation.fromNamespaceAndPath(CrusherMod.MODID, "block/crusher_roller"));

    public CrusherRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CrusherBlockEntity be, float partialTick, PoseStack pose, MultiBufferSource buffer,
                       int packedLight, int packedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        BakedModel model = mc.getModelManager().getModel(ROLLER_MODEL);

        boolean lit = be.getBlockState().getValue(CrusherBlock.LIT);
        float angle = be.rollerAngle + (lit ? partialTick * CrusherBlockEntity.ROLLER_SPEED : 0f);

        pose.pushPose();
        // Centro del rodillo en el modelo: (8, 12, 8) / 16
        pose.translate(0.5, 0.75, 0.5);
        pose.mulPose(Axis.XP.rotationDegrees(angle));
        pose.translate(-0.5, -0.75, -0.5);
        mc.getBlockRenderer().getModelRenderer().renderModel(
                pose.last(), buffer.getBuffer(RenderType.cutout()), null, model,
                1f, 1f, 1f, packedLight, packedOverlay);
        pose.popPose();
    }
}
