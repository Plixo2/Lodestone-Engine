package net.plixo.paper.client.ui.elements;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;

/**
 * please use now {@code UICanvas} for multiple UIElement support
 **/
@Deprecated
public abstract class UIMultiButton extends UIElement {


    //array of other Elements
    UIElement[] others;

    public UIMultiButton(UIElement... others) {
        this.others = others;
    }


    //for drawing relative to the main button;
    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness,0x6F000000, 1);
        super.drawScreen(mouseX, mouseY);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);
        for (UIElement button : others) {
            button.drawScreen(mouseX - x, mouseY - y);
        }
        GL11.glPopMatrix();

    }


    //hovering fix
    @Override
    public boolean hovered(float mouseX, float mouseY) {

        if (!super.hovered(mouseX, mouseY)) {
            return false;
        }

        for (UIElement button : others) {
            if (button.hovered(mouseX - x, mouseY - y)) {
                return false;
            }
        }

        return true;
    }

    //input
    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        int i = 0;
        for (UIElement button : others) {
            button.mouseClicked(mouseX - x, mouseY - y, mouseButton);
            if (button.hovered(mouseX - x, mouseY - y)) {
                otherButton(i);
            }
            i += 1;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //for triggering other buttons
    public abstract void otherButton(int id);
}
