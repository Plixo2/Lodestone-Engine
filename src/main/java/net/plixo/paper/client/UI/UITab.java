package net.plixo.paper.client.UI;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.editor.visualscript.Rect;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


public class UITab implements IGuiEvent {

    public static Minecraft mc = Minecraft.getInstance();
    public Rect head;
    String name;
    public TabbedUI parent;
    protected UICanvas canvas;

    public UITab(int id, String name) {
        this.head = new Rect(0, -12, TabbedUI.headWidth, 12, 0, 0);
        this.head.roundness = 0;
        this.head.id = id;
        this.head.txt = name;
        this.name = name;
    }


    @Override
    public void onTick() {
        canvas.onTick();
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        canvas.drawScreen(mouseX, mouseY);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        canvas.keyPressed(key, scanCode, action);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        canvas.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        canvas.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawOutline() {
        int lineColor = ColorLib.getMainColor();
        int width = 2;
        Gui.drawLine(0, 0, parent.width, 0, lineColor, width);
        Gui.drawLine(0, 0, 0, parent.height, lineColor, width);
        Gui.drawLine(0, parent.height, parent.width, parent.height, lineColor, width);
        Gui.drawLine(parent.width, 0, parent.width, parent.height, lineColor, width);
    }

    public void hideMenu() {
        parent.menu = null;
    }


    void setParent(TabbedUI parent) {
        this.parent = parent;
    }

    public void showMenu(int id, float x, float y, OptionMenu.TxtRun... options) {
        parent.menu = new OptionMenu(id, x, y, options);
    }


}
