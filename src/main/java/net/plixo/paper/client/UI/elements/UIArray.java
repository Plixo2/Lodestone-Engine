package net.plixo.paper.client.UI.elements;

import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;

/**
 * UICanvas with scroll support and easy (vertical) layout handling
 */
public class UIArray extends UICanvas {


    //fixed array height
    public float arrayheight = 20;
    float yOffset = 0;
    int lineColor = ColorLib.orange();

    public UIArray(int id) {
        super(id);
    }
    
    @Override
    public void drawScreen(float mouseX, float mouseY) {

        if (hovered(mouseX, mouseY)) {
            mouse();
        }

        sort();

        super.drawScreen(mouseX, mouseY);

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

    //Scroll support
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

    //set right dimensions
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

    //set the right height for each element
    public void sort() {

        int index = 0;
        for (UIElement element : elements) {
            element.y = yOffset + (index * arrayheight);
            index += 1;
        }
    }

}
