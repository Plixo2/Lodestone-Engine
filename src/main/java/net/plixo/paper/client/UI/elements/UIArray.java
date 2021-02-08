package net.plixo.paper.client.UI.elements;

import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import net.plixo.paper.client.util.Util;

public class UIArray extends UICanvas {


    public float arrayheight = 20;
    float yOffset = 0;
    int lineColor = ColorLib.orange();

    public UIArray(int id) {
        super(id);
    }


    @Override
    public void draw(float mouseX, float mouseY) {


        if (hovered(mouseX, mouseY)) {
            mouse();
        }



        sort();

        super.draw(mouseX, mouseY);

        int size = elements.size();
        float maxOffset = ((size*arrayheight)+10f)-height;
        if(maxOffset > 0) {
            Gui.drawLine(width-5,0,width-5,height,-1,2);
            float percent = -(yOffset/maxOffset);
            float min = 7;
            float max = height-7;
            float diff = max-min;
            float y = min+diff*percent;
            Gui.drawLine(width-5,y-7,width-5,y+7,lineColor,4);

        }
    }

    void mouse() {
        float dir = Math.signum(MouseUtil.getDWheel());
        if (dir != 0) {
            yOffset += (20f * dir);
            int size = elements.size();
            float maxOffset = ((size*arrayheight)+10f)-height;
            if(maxOffset > 0) {
                yOffset = Math.min(0,yOffset);
              if(yOffset < -maxOffset) {
                  yOffset = -maxOffset;
              }
            } else {
                yOffset = 0;
            }
        }
    }

    @Override
    public void add(UIElement element) {
        element.setDimensions(0, 0, width, arrayheight);
        super.add(element);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        sort();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void remove(UIElement element) {
        elements.remove(element);
    }

    public void sort() {

        int index = 0;
        for (UIElement element : elements) {
            element.y = yOffset + (index * arrayheight);
            index += 1;
        }
    }

}
