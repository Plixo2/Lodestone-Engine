package net.plixo.lodestone.client.ui.visualscript;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.util.io.MouseUtil;
import org.lwjgl.opengl.GL11;

public class UIGrid extends UICanvas {

    float planeX, planeY;
    float zoom = 1;
    boolean dragging = false;
    float dragX, dragY;
    float startX, startY;
    boolean shouldDrawLines = false;

    public void setShouldDrawLines(boolean shouldDrawLines) {
        this.shouldDrawLines = shouldDrawLines;
    }

    public float getZoom() {
        return zoom;
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        planeX = width / 2;
        planeY = height / 2;
        super.setDimensions(x, y, width, height);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        setColor(0);

        if (dragging) {
            planeX += (mouseX - dragX);
            planeY += (mouseY - dragY);
            dragX = mouseX;
            dragY = mouseY;
        }

        if (isHovered(mouseX, mouseY)) {
            mouse(mouseX, mouseY);
        }
        float[] modelViewMatrix = UGui.getModelViewMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(planeX, planeY, 0);
        GL11.glScaled(zoom, zoom, 1);

        if (shouldDrawLines) {
            drawLines();
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        drawScreenTranslated(mouseToWorld.x, mouseToWorld.y);
        GL11.glPopMatrix();

    }

    public void drawScreenTranslated(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
    }

    public void mouseClickedTranslated(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleasedTranslated(float globalMouseX, float globalMouseY, float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY)) {
            return;
        }
        if (mouseButton == 1) {
            startDragging(mouseX, mouseY);
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        mouseClickedTranslated(mouseToWorld.x, mouseToWorld.y, mouseButton);
    }

    public void startDragging(float mouseX, float mouseY) {
        dragging = true;
        dragX = mouseX;
        dragY = mouseY;
        startX = mouseX;
        startY = mouseY;
    }

    public boolean hasMoved(float mouseX, float mouseY) {
        return Math.abs(mouseX - startX) < 5 && Math.abs(mouseY - startY) < 5;
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        if (state == 1) {
            dragging = false;
            dragX = 0;
            dragY = 0;
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        mouseReleasedTranslated(mouseX, mouseY, mouseToWorld.x, mouseToWorld.y, state);
    }


    void mouse(float mouseX, float mouseY) {
        float dir = Math.signum(MouseUtil.getDWheel());
        if (dir != 0) {
            Vector2f pre = screenToWorld(mouseX, mouseY);
            zoom *= 1 + (0.1f * dir);
            zoom = Math.min(Math.max(zoom, 0.1f), 3);
            Vector2f post = screenToWorld(mouseX, mouseY);
            Vector2f diff = new Vector2f(pre.x - post.x, pre.y - post.y);
            planeX -= diff.x * zoom;
            planeY -= diff.y * zoom;
        }
    }

    void drawLines() {
        int i = 50;
        Vector2f top = screenToWorld(-i, -i);
        Vector2f bottom = screenToWorld(width + i, height + i);
        float x = top.x;
        float mX = x % i;
        float y = top.y;
        float mY = y % i;
        int color = 0x6F000000;

        for (float j = top.x; j <= bottom.x; j += i) {
            float off = j - mX;
            UGui.drawLine(off, top.y, off, bottom.y, color, 1);
        }
        for (float j = top.y; j <= bottom.y; j += i) {
            float off = j - mY;
            UGui.drawLine(top.x, off, bottom.x, off, color, 1);
        }

        i  = 25;
        Vector2f mid = screenToWorld(0, 0);
        //UGui.drawCircle(0, 0, 3, -1);
        UGui.drawLine(-i,0,i,0,-1,2*UGui.getScale());
        UGui.drawLine(0,-i,0,i,-1,2*UGui.getScale());
    }

    public Vector2f screenToWorld(float x, float y) {

        x -= this.planeX;
        y -= this.planeY;

        x /= zoom;
        y /= zoom;

        return new Vector2f(x, y);
    }

    public Vector2f worldToScreen(float x, float y) {

        x *= zoom;
        y *= zoom;

        x += this.planeX;
        y += this.planeY;

        return new Vector2f(x, y);
    }


}

