package net.plixo.paper.client.ui.elements.canvas;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import net.plixo.paper.client.util.Util;
import org.lwjgl.opengl.GL11;

/**
 * UICanvas with scroll support and easy (vertical) layout handling
 */
public class UIArray extends UICanvas {


    public float space = 0;
    float percent = 0;
    float offset = 0;
    int lineColor = ColorLib.orange();

    public UIArray() {
        this.color = 0;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {


        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, this.color);

        if (hovered(mouseX, mouseY)) {
            float dir = Math.signum(MouseUtil.getDWheel());
            if (dir != 0) {
                float size = 20 / (getMax()-height);
                percent -= size * dir;
                percent = Util.clampFloat(percent, 1, 0);
            }
        }

        offset = 0;
        if (getMax() > height) {
            float maxDiff = getMax() - height;
            offset = maxDiff * percent;
        }
        mouseY += offset;


        GL11.glPushMatrix();
        float[] mat = Gui.getModelViewMatrix();
        Vector2f globalPosMIN = Gui.toScreenSpace(mat,x,y);
        Vector2f globalPosMAX = Gui.toScreenSpace(mat,x + width,y + height);
        Gui.createScissorBox(globalPosMIN.x, globalPosMIN.y, globalPosMAX.x, globalPosMAX.y);
        Gui.activateScissor();
        GL11.glTranslated(x, y - offset, 0);

        for (UIElement element : elements) {
            element.drawScreen(mouseX - x, mouseY - y);
        }
        Gui.deactivateScissor();
        GL11.glPopMatrix();

    }

    @Override
    public void onTick() {
        sort();
        super.onTick();
    }


    //set right dimensions
    @Override
    public void add(UIElement element) {
        super.add(element);
        sort();
    }

    @Override
    public void remove(UIElement element) {
        super.remove(element);
        sort();
    }

    public float getMax() {
        float y = 0;
        if (elements.size() > 0) {
            UIElement element = elements.get(elements.size() - 1);
            y = element.y + element.height+space;
        }
        return y;
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY+offset, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX, mouseY+offset, state);
    }

    //set the right height for each element
    public void sort() {
        float y = 0;
        for (UIElement element : elements) {
            element.y = y;
            y += element.height + space;
        }
    }

}
