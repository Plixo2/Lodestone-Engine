package net.plixo.lodestone.client.ui.elements;


import net.minecraft.client.Minecraft;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.util.CursorObject;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;

import java.util.function.Supplier;

/**
 * Default ui class, updates
 * hoverProgress and handles simple click action with {@code actionPerformed()}
 **/
public abstract class UIElement implements IGuiEvent {

    public static Minecraft mc = Minecraft.getInstance();

    public float height, width;
    public float x, y;
    float hoverProgress = 0;
    Runnable onTick;
    Supplier<?> cursorObject;
    Runnable action;
    int alignment = 0;
    float roundness = 0;
    int color = UColor.getBackground(0.2f);
    String displayName;
    String hoverName;

    long lastMs = 0;

    public UIElement() {
        setRoundness(2);
    }

    public void drawScreen(float mouseX, float mouseY) {
        updateHoverProgress(mouseX, mouseY);
    }

    public void drawName(float mouseX, float mouseY) {
        if (hoverName != null) {
            if (isHovered(mouseX, mouseY)) {
                drawName(hoverName);
                return;
            }
        }
        if (displayName != null) {
            drawName(displayName);
        }
    }

    private void drawName(String name) {
        if (alignment == -1) {
            UGui.drawStringWithShadow(name, x + 3, y + height / 2, UColor.getTextColor());
        } else if (alignment == 1) {
            UGui.drawStringWithShadow(name, x + width - UGui.getStringWidth(name), y + height / 2, UColor.getTextColor());
        } else {
            UGui.drawCenteredStringWithShadow(name, x + width / 2, y + height / 2, UColor.getTextColor());
        }
    }

    public void drawDefault() {
        float w = 10;
        float s = w-(size*w);
        UGui.drawRoundedRect(x+s, y+s, x + width-s, y + height-s, getRoundness(), getColor());
    }

    public void drawDefault(int color) {
        UGui.drawRoundedRect(x, y, x + width, y + height, getRoundness(), color);
    }

    public void drawHoverEffect() {
        drawDefault(getHoverColor());
    }

    public void onTick() {
        if (onTick != null) {
            onTick.run();
        }
    }
    float size = 1;
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                if (action != null) {
                    action.run();
                }
            } else if (mouseButton == 1) {
                if (cursorObject != null) {
                    Object run = cursorObject.get();
                    if (run != null) {
                        ScreenMain.instance.cursorObject = new CursorObject<>(run);
                        UMath.print(run);
                    }
                }
            }
        }
    }

    public void updateHoverProgress(float mouseX, float mouseY) {
        long delta = System.currentTimeMillis() - lastMs;
        if (!isHovered(mouseX, mouseY)) {
            delta = -delta;
        }

        hoverProgress = (float) UMath.clampDouble(hoverProgress + delta, 100, 0);
        lastMs = System.currentTimeMillis();
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

    public void setHoverName(String name) {
        this.hoverName = name;
    }

    public void setRoundness(float roundness) {
        this.roundness = roundness;
    }

    public void setCursorObject(Supplier<?> getObject) {
        this.cursorObject = getObject;
    }

    public void setTickAction(Runnable runnable) {
        this.onTick = runnable;
    }

    public void setAction(Runnable runnable) {
        action = runnable;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void alignLeft() {
        alignment = -1;
    }

    public void alignMiddle() {
        alignment = 0;
    }

    public void alignRight() {
        alignment = 1;
    }


    public String getDisplayName() {
        return displayName;
    }

    public String getHoverName() {
        return hoverName;
    }

    public float getRoundness() {
        return roundness;
    }

    public int getColor() {
        return color;
    }

    public Runnable getAction() {
        return action;
    }

    public int getHoverColor() {
        return UColor.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
    }

    public float getHoverProgress() {
        return hoverProgress;
    }

    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
