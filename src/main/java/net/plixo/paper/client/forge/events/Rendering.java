package net.plixo.paper.client.forge.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Rendering {

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent event) {
        if (true) {
            return;
        }
        //  System.out.println("Render");

        // Get instances of the classes required for a block render.
        World world = Minecraft.getInstance().player.getEntityWorld();
        MatrixStack matrixStack = event.getMatrixStack();

        // Get the projected view coordinates.
        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();

        // Choose obsidian as the arbitrary block.
        BlockState blockState = Blocks.OBSIDIAN.getDefaultState();

        // Begin rendering the block.
        IRenderTypeBuffer.Impl renderTypeBuffer = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());

        renderBlock(matrixStack, renderTypeBuffer, world, blockState, new BlockPos(1, 1, 1), projectedView, new Vector3d(0.0, 128.0, 0.0));

        renderTypeBuffer.finish();
    }

    @SuppressWarnings("deprecation")
    public static void renderBlock(MatrixStack matrixStack, IRenderTypeBuffer.Impl renderTypeBuffer, World world, BlockState blockState, BlockPos logicPos, Vector3d projectedView, Vector3d renderCoordinates) {
        BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        int i = OverlayTexture.NO_OVERLAY;

        matrixStack.push();
        matrixStack.translate(-projectedView.x + renderCoordinates.x, -projectedView.y + renderCoordinates.y, -projectedView.z + renderCoordinates.z);

        for (RenderType renderType : RenderType.getBlockRenderTypes()) {
            if (RenderTypeLookup.canRenderInLayer(blockState, renderType)) {
                blockRendererDispatcher.getBlockModelRenderer().renderModel(world, blockRendererDispatcher.getModelForState(blockState), blockState, logicPos, matrixStack, renderTypeBuffer.getBuffer(renderType), true, new Random(), blockState.getPositionRandom(logicPos), i);
                //       System.out.println(renderType);
            }
        }

        matrixStack.pop();
    }
}
