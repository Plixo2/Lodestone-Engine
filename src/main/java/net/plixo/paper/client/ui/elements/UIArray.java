package net.plixo.paper.client.ui.elements;

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


    float percent = 0;
    float offset = 0;
    int lineColor = ColorLib.orange();

    public UIArray(int id) {
        super(id);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

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
        GL11.glTranslated(x, y - offset, 0);
        for (UIElement element : elements) {
            element.drawScreen(mouseX - x, mouseY - y);
        }
        GL11.glPopMatrix();



        /*
        float maxOffset = (getMax() + 20f) - height;
        if (maxOffset > 0) {
            Gui.drawLine(x + width - 5, y, x + width - 5, y + height, -1, 2);
            float percent = -(yOffset / maxOffset);
            float min = 7;
            float max = height - 7;
            float diff = max - min;
            float y = min + diff * percent;
            Gui.drawLine(x + width - 5, this.y + y - 7, x + width - 5, this.y + y + 7, lineColor, 4);
        }

         */
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

    public float getMax() {
        float y = 0;
        if (elements.size() > 0) {
            UIElement element = elements.get(elements.size() - 1);
            y = element.y + element.height;
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
            y += element.height;
        }
    }

    @Override
    public boolean hovered(float mouseX, float mouseY) {
        for (UIElement element : elements) {
            if(element.hovered(mouseX-this.x,mouseY+offset-this.y)) {
                return true;
            }
        }
        return false;
    }
}
