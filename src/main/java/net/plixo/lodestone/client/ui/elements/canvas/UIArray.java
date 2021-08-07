package net.plixo.lodestone.client.ui.elements.canvas;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.MouseUtil;
import org.lwjgl.opengl.GL11;

/**
 * UICanvas with scroll support and easy (vertical) layout handling
 */
public class UIArray extends UICanvas {


    public float space = 0;
    float percent = 0;
    float offset = 0;
    float animated = 0;
    int lineColor = UColor.orange();

    public UIArray() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {


         drawDefault();

        if (isHovered(mouseX, mouseY)) {
            float dir = Math.signum(MouseUtil.getDWheel());
            if (dir != 0) {
                float size = 30 / (getMax()-height);
                percent = percent - (size * dir);
                percent = UMath.clampFloat(percent, 1, 0);
              //  Animation.animate(f -> animated = UMath.clampFloat(f, 1, 0),() -> animated,percent,0.3f);
            }
        }

        offset = 0;
        if (getMax() > height) {
            float maxDiff = getMax() - height;
            offset = maxDiff * percent;
        }
        mouseY += offset;


        GL11.glPushMatrix();
        float[] mat = UGui.getModelViewMatrix();
        Vector2f globalPosMIN = UGui.toScreenSpace(mat,x,y);
        Vector2f globalPosMAX = UGui.toScreenSpace(mat,x + width,y + height);
        UGui.createScissorBox(globalPosMIN.x, globalPosMIN.y, globalPosMAX.x, globalPosMAX.y);
        UGui.activateScissor();
        GL11.glTranslated(x, y - offset, 0);

        for (UIElement element : elements) {
            element.drawScreen(mouseX - x, mouseY - y);
        }
        UGui.deactivateScissor();
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
