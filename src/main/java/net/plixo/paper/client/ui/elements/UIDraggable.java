package net.plixo.paper.client.ui.elements;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.util.KeyboardUtil;
import net.plixo.paper.client.util.Util;
import org.lwjgl.glfw.GLFW;

/**
 * UIElement that can be dragged (while leftclicking)
 */
public class UIDraggable extends UICanvas {

    public boolean isSelected = false;
    float dragX = 0;
    float dragY = 0;
    boolean dragging = false;

    /**
     * for calculation the offset
     */
    @Override
    public void drawScreen(float mouseX, float mouseY) {
        if (dragging) {
            x += (mouseX - dragX);
            y += (mouseY - dragY);
            dragX = mouseX;
            dragY = mouseY;
        }
        super.drawScreen(mouseX, mouseY);
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
