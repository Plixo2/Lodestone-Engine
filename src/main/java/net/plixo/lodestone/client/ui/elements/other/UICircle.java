package net.plixo.lodestone.client.ui.elements.other;

import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;

public class UICircle extends UIElement {

    float radius = 0;


    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        radius = height / 2;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        UGui.drawCircle(x + width / 2, y + height / 2, radius + 1, getHoverColor());
        UGui.drawCircle(x + width / 2, y + height / 2, radius, getColor());



        updateHoverProgress(mouseX, mouseY);

        if (getHoverName() != null && isHovered(mouseX, mouseY)) {
            UGui.deactivateScissor();
            drawName(mouseX, mouseY);
            UGui.activateScissor();
        }
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}

