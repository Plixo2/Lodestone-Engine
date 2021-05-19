package net.plixo.paper.client.ui.elements;

import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

import java.util.concurrent.atomic.AtomicInteger;

public class UITable extends UICanvas {

     String[] types;
     String type;

    public UITable() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));
        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);

        if(type != null) {
            Gui.drawString(type,x+3,y+height/2,-1);
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
        button.displayName = "^";
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
