package net.plixo.paper.client.avs.newVersion;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.util.Util;

/**
 * UIElement that can be dragged (while leftclicking)
 */
public class UIDraggable extends UICanvas {

    float dragX = 0;
    float dragY = 0;
    boolean dragging = false;
    public UIDraggable(int id) {
        super(id);
    }

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

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if(hovered(mouseX , mouseY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX;
            dragY = mouseY;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        dragging = false;
        super.mouseReleased(mouseX,mouseY,state);
    }
}
