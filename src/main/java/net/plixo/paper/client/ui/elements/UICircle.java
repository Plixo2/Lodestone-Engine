package net.plixo.paper.client.ui.elements;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class UICircle extends UIElement {

    public float radius = 0;
    public UICircle() {
        super(0);
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        radius = height/2;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawCircle(x + width / 2, y + height / 2, radius,
                color);
        Gui.drawCircle(x + width / 2, y + height / 2, radius - 0.5f,
                ColorLib.getDarker(color));

        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawCircle(x + width / 2, y + height / 2, height / 2,
                color);
        updateHoverProgress(mouseX, mouseY);
    }
}

