package net.plixo.paper.client.engine.components.timeline;

import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.Gui;

public class UICircle extends UIDraggable {


    public UICircle(int id) {
        super(id);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        Gui.drawOval(x,y,width,height,color);
        super.drawScreen(mouseX, mouseY);
    }



    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
    }

}
