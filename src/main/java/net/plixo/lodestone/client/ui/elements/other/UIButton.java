package net.plixo.lodestone.client.ui.elements.other;


import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.util.UColor;

/**
 * default button to interact with the UI
 **/
public class UIButton extends UIElement {

    public UIButton() {
        this.setColor(UColor.getMainColor());
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        drawDefault();
        UGui.drawLinedRoundedRect(x, y, x + width, y + height, getRoundness(), UColor.getDarker(getColor()), 1);
         drawHoverEffect();
        drawName(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY);
    }

}
