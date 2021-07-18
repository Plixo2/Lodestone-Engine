package net.plixo.paper.client.ui;


import net.minecraft.client.Minecraft;
import net.plixo.paper.client.ui.GUI.GUIMain;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.CursorObject;
import net.plixo.paper.client.util.Util;

/**
 * Default ui class, updates
 * hoverProgress and handles simple click action with {@code actionPerformed()}
 **/
public abstract class UIElement implements IGuiEvent {

    public static Minecraft mc = Minecraft.getInstance();
    public String displayName;
    public float height, width;
    protected float hoverProgress = 0;
    public transient Runnable onTick;
    public transient Util.IGetObject<?> cursorObject;

    long lastMs = 0;

    protected float roundness = 0;

    protected int textColor = -1;
    public int color = ColorLib.getBackground(0.5f);

    public float x, y;

    public UIElement() {
        setRoundness(2);
    }

    public void actionPerformed() {
    }

    public void drawScreen(float mouseX, float mouseY) {
        updateHoverProgress(mouseX, mouseY);
    }


    public boolean hovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }


    public void setColor(int color) {
        this.color = color;
    }


    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (hovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                actionPerformed();
            } else if (mouseButton == 1) {
                if (cursorObject != null) {
                    Object run = cursorObject.run();
                    if (run != null) {
                        GUIMain.instance.cursorObject = new CursorObject<>(run);
                        Util.print(run);
                    }
                }
            }
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

    @Override
    public void onTick() {
        if (onTick != null) {
            onTick.run();
        }
    }

    public void setCursorObject(Util.IGetObject<?> getObject) {

        this.cursorObject = getObject;
    }

    public void setTickAction(Runnable runnable) {
        this.onTick = runnable;
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
