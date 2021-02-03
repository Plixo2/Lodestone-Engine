package net.plixo.paper.client.UI.elements;


import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.Gui;

public class UIHead extends UIElement {


    public UIHead(int id) {
        super(id);
    }


    @Override
    public void draw(float mouseX, float mouseY) {

        drawStringCentered();
        super.draw(mouseX, mouseY);
    }

    public void drawStringCentered() {
        if (displayName != null) {
            Gui.drawCenteredString(displayName, x + width / 2, y + height / 2, textColor);
        }
    }

}
