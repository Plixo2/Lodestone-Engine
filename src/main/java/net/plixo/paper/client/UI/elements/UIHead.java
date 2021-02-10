package net.plixo.paper.client.UI.elements;


import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.Gui;

public class UIHead extends UIElement {



    public UIHead(int id) {
        super(id);
    }


    //simple space or text (without function)
    @Override
    public void drawScreen(float mouseX, float mouseY) {
        if (displayName != null) {
            Gui.drawCenteredString(displayName, x + width / 2, y + height / 2, textColor);
        }
        super.drawScreen(mouseX, mouseY);
    }


}
