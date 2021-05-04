package net.plixo.paper.client.ui;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.ui.other.OptionMenu;
import net.plixo.paper.client.avs.old.ui.Rect;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


/**
 * a Class for handling the drawing of a tab
 * with right offset and clipping (using {@code GL11.glScissor()})
 * UITab is inside of {@code TabbedUI}
 **/
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


    //Event passing for the default UICanvas
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

    public void save() {};

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        canvas.mouseReleased(mouseX, mouseY, state);
    }


    void setParent(TabbedUI parent) {
        this.parent = parent;
    }

    
}
