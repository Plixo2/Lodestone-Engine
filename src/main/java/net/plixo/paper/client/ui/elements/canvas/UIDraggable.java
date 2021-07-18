package net.plixo.paper.client.ui.elements.canvas;

import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;

/**
 * UIElement that can be dragged (while leftclicking)
 */
public class UIDraggable extends UICanvas {

    public boolean isSelected = false;
    public float dragX = 0;
    public float dragY = 0;
    public boolean dragging = false;

    /**
     * for calculation the offset
     */
    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (dragging) {
            x += (mouseX - dragX);
            y += (mouseY - dragY);
            dragX = mouseX;
            dragY = mouseY;
        }
    }


    public void beginSelect(float mouseX, float mouseY, int mouseButton) {
        if(!KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            isSelected = false;
        }
        if(hovered(mouseX , mouseY) && mouseButton == 0) {
            isSelected = true;
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if(hovered(mouseX , mouseY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX;
            dragY = mouseY;
            isSelected = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX,mouseY,state);
    }
}
