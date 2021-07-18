package net.plixo.paper.client.ui.other;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UIArray;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.ui.elements.visual.UILabel;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

import java.util.*;

public class UIMouseMenu extends UIArray {

    public UIMouseMenu() {
        super();
    }

    LinkedHashMap<String , Runnable> options = new LinkedHashMap<>();

    public void addOption(String name , Runnable action) {
        options.put(name,action);
    }

    public void build(float mouseX , float mouseY) {
        float max = 10;
        for (String s : options.keySet()) {
            max = Math.max(max, Gui.getStringWidth(s));
        }
        max += 5;

        UIElement e = new UIElement() {
        };
        e.height = 2;
        add(e);
        int y = 2;
        for (Map.Entry<String, Runnable> stringRunnableEntry : options.entrySet()) {
            UIButton button = new UIButton();
            button.setAction(stringRunnableEntry.getValue());
            button.setDisplayName(stringRunnableEntry.getKey());
            button.setRoundness(0);
            button.setColor(ColorLib.getMainColor());
            button.setDimensions(2,y,max,12);
            add(button);
            y += 12;
        }
        setRoundness(2);
        setColor(ColorLib.getDarker(ColorLib.getDarker(ColorLib.getMainColor())));
        setDimensions(mouseX,mouseY,max+4,Math.min(y+2,300));
    }

}
