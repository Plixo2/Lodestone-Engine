package net.plixo.lodestone.client.ui.elements.canvas;

import java.util.function.BiConsumer;

public class UIContext extends UICanvas{

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
        if(context != null) {
            context.accept(mouseX,mouseY);
        }
    }

    BiConsumer<Float,Float> context;
    public void setDrawContext(BiConsumer<Float,Float> context) {
        this.context = context;
    }


}
