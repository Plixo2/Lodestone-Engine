package net.plixo.paper.client.UI;


import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;

public abstract class UIElement {


    public String displayName;
    protected float height;
    protected float hoverProgress = 0;


    protected int id;
    long lastMs = 0;


    UIElement parent;
    protected float roundness = 0;

    protected int textColor = -1;
    public int color = ColorLib.getBackground(0.5f);
    protected float width;


    protected float x, y;

    public UIElement(int id) {
        this.id = id;
        setRoundness(2);
    }

    public void actionPerformed() { }

    public void draw(float mouseX, float mouseY) {
        updateHoverProgress(mouseX, mouseY);

        /*
        if (hovered(mouseX, mouseY) && displayName != null) {
            String txt = displayName;
           // Gui.drawLinedRoundetRect(mouseX + 8, mouseY - 5, mouseX + Gui.getStringWidth(txt) + 16, mouseY + 5, 3, ColorLib.utilLines(), 2);
          //  Gui.drawRoundetRect(mouseX + 8, mouseY - 5, mouseX + Gui.getStringWidth(txt) + 16, mouseY + 5, 3, ColorLib.getBackground(0.4f));
          //  Gui.drawString(txt, mouseX + 12, mouseY, textColor);
        }
        */
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean hovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public void keyPressed(int key, int scanCode, int action) {
    }


    public void setColor(int color) {
        this.color = color;
    }


    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (hovered(mouseX, mouseY)) {
            actionPerformed();
        }
    }

    public void setDimensions(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setDisplayName(String name) {
        this.displayName = name;
    }

    public void setRoundness(float roundness) {
        this.roundness = roundness;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void update() {

    }

//	0xFF1f2d40
//	0xFF17212f

    public void updateHoverProgress(float mouseX, float mouseY) {
        long delta = System.currentTimeMillis() - lastMs;
        if (!hovered(mouseX, mouseY)) {
            delta = -delta;
        }

        hoverProgress = Util.clampFloat(hoverProgress + delta, 100, 0);

        lastMs = System.currentTimeMillis();
    }
}
