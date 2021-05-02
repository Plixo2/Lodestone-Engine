package net.plixo.paper.client.ui.elements;


import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.util.Gui;

/**
 *  for  displaying empty space or double in the UI
 *  using Minecraft {@code TextFieldWidget}
 **/
public class UILabel extends UIElement {



    //simple space or text (without function)
    @Override
    public void drawScreen(float mouseX, float mouseY) {
      drawDisplayString();
        super.drawScreen(mouseX, mouseY);
    }
    public void drawDisplayString() {
        if (displayName != null) {
            Gui.drawCenteredString(displayName, x + width / 2, y + height / 2, textColor);
        }
    }

}
