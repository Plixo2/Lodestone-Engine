package net.plixo.paper.client.ui.elements.canvas;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * for other {@code UIElement} inside a Element...
 * great for backgrounds and easy drawing, input and layout handling
 **/
public class UICanvas extends UIElement {

    //for removing while looping
    public CopyOnWriteArrayList<UIElement> elements = new CopyOnWriteArrayList<>();
    UIElement lastElement;


    //add a elements
    public void add(UIElement element) {
        lastElement = element;
        elements.add(element);
    }

    //remove a element
    public void remove(UIElement element) {
        elements.remove(element);
    }

    //clears the mod
    public void clear() {
        lastElement = null;
        elements.clear();
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {


        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, this.color);

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);
        for (UIElement element : elements) {
            element.drawScreen(mouseX - x, mouseY - y);
        }
        GL11.glPopMatrix();



        super.drawScreen(mouseX, mouseY);
    }


    //returns the list
    public CopyOnWriteArrayList<UIElement> getList() {
        return elements;
    }

    //checks if canvas has UIElements
    public boolean hasValues() {
        return elements.size() > 0;
    }

    //inherited input
    @Override
    public void keyTyped(char typedChar, int keyCode) {
        for (UIElement element : elements) {
            element.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        for (UIElement element : elements) {
            element.keyPressed(key, scanCode, action);
        }
        super.keyPressed(key, scanCode, action);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        for (UIElement element : elements) {
            element.mouseClicked(mouseX - x, mouseY - y, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        for (UIElement element : elements) {
            element.mouseReleased(mouseX - x, mouseY - y, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    //inherited tick
    @Override
    public void onTick() {
        for (UIElement element : elements) {
            element.onTick();
        }
        super.onTick();
    }
}
