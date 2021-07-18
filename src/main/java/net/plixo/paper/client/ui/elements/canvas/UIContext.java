package net.plixo.paper.client.ui.elements.canvas;

public class UIContext extends UICanvas{

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
        if(context != null) {
            context.draw(mouseX,mouseY);
        }
    }

    DrawContext context;
    public void setDrawContext(DrawContext context) {
        this.context = context;
    }

    public static interface DrawContext {
        public void draw(float mouseX , float mouseY);
    }

}
