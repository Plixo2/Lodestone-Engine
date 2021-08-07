package net.plixo.lodestone.client.ui.elements.other;

import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.util.UColor;

import java.util.concurrent.atomic.AtomicInteger;

public class UITable extends UICanvas {

     String[] types;
     String type;

    public UITable() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        UGui.drawRoundedRect(x, y, x + width, y + height, getRoundness(), UColor.getBackground(0.3f));
        drawHoverEffect();

        if(type != null) {
            UGui.drawString(type,x+3,y+height/2,UColor.getTextColor());
        }

        super.drawScreen(mouseX, mouseY);
    }

    public void setOptions(String... options) {
        this.types = options;
        type = types[0];
    }

    public String getType() {
        return type;
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIButton button = new UIButton();
        button.setDisplayName("^");
        AtomicInteger i = new AtomicInteger();
        button.setAction(() -> {
            type = types[i.get() % types.length];
            i.addAndGet(1);
        });
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);

        super.setDimensions(x, y, width, height);
    }


}
