package net.plixo.paper.client.forge.events;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.plixo.paper.client.ui.elements.UICanvas;
import org.lwjgl.opengl.GL11;

public class Render2D {
    public static UICanvas canvas = new UICanvas();

    @SubscribeEvent
    public void doOverlay(RenderGameOverlayEvent event) {
        if (event instanceof RenderGameOverlayEvent.Post) {
            float width = event.getWindow().getScaledWidth();
            float height = event.getWindow().getScaledHeight();
            try {
                GL11.glPushMatrix();
                canvas.setDimensions(0, 0, width, height);
                canvas.setColor(0);
                canvas.drawScreen(-1, -1);
                RenderSystem.shadeModel(7424);
                RenderSystem.disableBlend();
                RenderSystem.enableTexture();
                GL11.glPopMatrix();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
