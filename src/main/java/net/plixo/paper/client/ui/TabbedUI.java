package net.plixo.paper.client.ui;

import net.plixo.paper.client.ui.other.OptionMenu;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * holds a array of {@code UITab}
 * handles input for the current tab;
 **/
public class TabbedUI implements IGuiEvent {


    static float headWidth = 60;
    String name;
    public int selectedIndex = 0;
    public ArrayList<UITab> tabs = new ArrayList<>();

    //TODO redo option tab system

    public float width, height;
    public float x, y;

    public TabbedUI(float width, float height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public void addTab(UITab tab) {
        tab.setParent(this);
        tab.init();
        tabs.add(tab);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        Gui.drawRect(0, -12, width, 0, 0x55AAAAAA);
        Gui.drawRect(0.5f, -11.5f, width - 0.5f, -0.5f, ColorLib.getBackground(0));
        for (int i = 0; i < tabs.size(); i++) {
            UITab tab = tabs.get(i);
            tab.head.x = i * headWidth;

            tab.head.color = 0;
            tab.head.hoverColor = 0;

            if (selectedIndex == i) {
                GL11.glPushMatrix();
                Gui.createScissorBox(x, y, x + width, y + height + 1);
                Gui.activateScissor();

                tab.drawScreen(mouseX - x, mouseY - y);
                Gui.deactivateScissor();

                GL11.glPopMatrix();

                if (tabs.size() >= 2) {
                    tab.head.color = 0x15000000;
                    tab.head.hoverColor = 0x25000000;
                }
            }

            tab.head.draw(mouseX, mouseY);
        }
        Gui.drawGradientRect(0, 0, width, 5, 0x80000000, 0);
        Gui.drawRect(width, 0, width + 0.5f, height, 0x55AAAAAA);

    }

    @Override
    public void close() {
        tabs.get(selectedIndex).close();
    }

    public void save() {
        for (UITab tab : tabs) {
            tab.save();
        }
    }

    public UITab getHoveredHead(float mouseX, float mouseY) {
        for (UITab tab : tabs) {
            if (tab.head.mouseInside(mouseX, mouseY, -1)) {
                return tab;
            }
        }
        return null;
    }

    public boolean isMouseInside(float mouseX, float mouseY) {
        return mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        tabs.get(selectedIndex).keyTyped(typedChar, keyCode);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        tabs.get(selectedIndex).keyPressed(key, scanCode, action);
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        tabs.get(selectedIndex).mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        tabs.get(selectedIndex).mouseReleased(mouseX, mouseY, state);
    }


    @Override
    public void onTick() {
        for (UITab tab : tabs) {
            tab.onTick();
        }
    }

}