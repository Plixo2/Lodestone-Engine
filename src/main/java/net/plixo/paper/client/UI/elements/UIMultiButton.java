package net.plixo.paper.client.UI.elements;

import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;


public abstract class UIMultiButton extends UIElement {


    //array of other Elements
    UIElement[] others;

    public UIMultiButton(int id, UIElement... others) {
        super(id);
        this.others = others;
    }


    //for drawing relative to the main button;
    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, ColorLib.utilLines(), 1);
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

        for (UIElement button : others) {
            button.mouseClicked(mouseX - x, mouseY - y, mouseButton);
            if (button.hovered(mouseX - x, mouseY - y)) {
                otherButton(button.getId());
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //for triggering other buttons
    public abstract void otherButton(int id);
}
