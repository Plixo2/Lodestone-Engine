package net.plixo.paper.client.engine.behaviors;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.plixo.paper.client.engine.ecs.Behavior;
import org.lwjgl.opengl.GL11;

public class Renderer extends Behavior {


    public Renderer() {
        super("Renderer");
    }

    @Override
    public void render() {
        AxisAlignedBB aabb = new AxisAlignedBB(-1,-1,-1,1,1,1);
        renderArea(aabb.offset(entity.position), 1, 0 , 0);
        super.render();
    }
    public void renderArea(AxisAlignedBB aabb, float r, float g, float b) {
        RenderSystem.pushMatrix();
        ActiveRenderInfo info = Minecraft.getInstance().getRenderManager().info;
        RenderSystem.rotatef(info.getYaw()-180, 0, 1, 0);
        RenderSystem.rotatef(info.getPitch(), (float) (-Math.sin((info.getYaw() + 90) / 360 * Math.PI * 2)), 0.0f, (float) (Math.cos((info.getYaw() + 90) / 360 * Math.PI * 2)));
        RenderSystem.translated(-info.getProjectedView().x, -info.getProjectedView().y, -info.getProjectedView().z);
        RenderSystem.lineWidth(4);
        RenderSystem.color4f(r, g, b, 1.0f);

        aabb = aabb.expand(0.001, 0.001, 0.001);
        aabb = aabb.offset(-0.0005, -0.0005, -0.0005);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        builder.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        builder.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();

        builder.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        builder.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();

        builder.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        builder.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();

        builder.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        builder.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();

        builder.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        builder.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();

        builder.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        builder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();

        builder.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        builder.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();

        builder.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        builder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();

        builder.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        builder.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();

        builder.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        builder.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();

        builder.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        builder.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();

        builder.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        builder.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        tessellator.draw();
        RenderSystem.popMatrix();
    }


}
