package net.plixo.paper.client.ui;


import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Util;

/**
 * Default ui class, updates
 * hoverProgress and handles simple click action with {@code actionPerformed()}
 **/
public abstract class UIElement implements IGuiEvent {


    public String displayName;
    public float height , width;
    protected float hoverProgress = 0;


    protected int id;
    long lastMs = 0;


    protected float roundness = 0;

    protected int textColor = -1;
    public int color = ColorLib.getBackground(0.5f);

    public float x, y;

    public UIElement(int id) {
        this.id = id;
        setRoundness(2);
    }

    public void actionPerformed() {
    }

    public void drawScreen(float mouseX, float mouseY) {
        updateHoverProgress(mouseX, mouseY);
    }

    public int getId() {
        return id;
    }

    public boolean hovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
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


    public void updateHoverProgress(float mouseX, float mouseY) {
        long delta = System.currentTimeMillis() - lastMs;
        if (!hovered(mouseX, mouseY)) {
            delta = -delta;
        }

        hoverProgress = (float) Util.clampDouble(hoverProgress + delta, 100, 0);
        lastMs = System.currentTimeMillis();
    }
}
