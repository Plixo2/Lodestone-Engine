package net.plixo.lodestone.client.ui.elements.other;


import net.plixo.lodestone.client.ui.elements.UIElement;

/**
 *  for  displaying empty space or double in the UI
 *  using Minecraft {@code TextFieldWidget}
 **/
public class UILabel extends UIElement {


    //simple space or text (without function)
    @Override
    public void drawScreen(float mouseX, float mouseY) {
        drawName(mouseX,mouseY);
        super.drawScreen(mouseX, mouseY);
    }

}
