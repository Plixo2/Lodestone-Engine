package net.plixo.lodestone.client.ui.elements.other;

import net.plixo.animation.Animation;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.util.UColor;

import javax.swing.*;
import java.util.*;

public class UIMouseMenu extends UIArray {

    public UIMouseMenu() {
        super();
    }

    LinkedHashMap<String , ArrayList<UIButton>> options = new LinkedHashMap<>();

    public void addOption(String group,String name , Runnable action) {

        ArrayList<UIButton> buttons = options.getOrDefault(group, new ArrayList<>());


        UIButton button = new UIButton();
        button.setAction(action);
        button.setDisplayName(name);
        button.setRoundness(0);
        button.setColor(UColor.getMainColor());

        buttons.add(button);


        options.put(group,buttons);


       // options.put(name,action);
    }

    public void build(float mouseX , float mouseY) {
        float max = 10;
        for (ArrayList<UIButton> buttons : options.values()) {
            if(buttons.size() == 1)  {
                max = Math.max(max, UGui.getStringWidth(buttons.get(0).getDisplayName()));
            }
        }
        for (String names : options.keySet()) {
            max = Math.max(max, UGui.getStringWidth(names+" >"));
        }
        max += 5;

        UIElement element = new UIElement() {
        };
        element.height = 2;
        add(element);
        float y = 2;

        for (int i = 0; i < options.entrySet().size(); i++) {
            Map.Entry<String, ArrayList<UIButton>> stringArrayListEntry = new ArrayList<>(options.entrySet()).get(i);

            if(stringArrayListEntry.getValue().size() > 1 ) {
                UIButton button = new UIButton();
                button.setAction(() -> SwingUtilities.invokeLater(() -> {
                    ScreenMain.instance.beginMenu();
                    for (UIButton uiButton : stringArrayListEntry.getValue()) {
                        ScreenMain.instance.addMenuOption(uiButton.getDisplayName(),uiButton.getAction());
                    }
                    ScreenMain.instance.showMenu(mouseX,mouseY);
                }));
                button.setDisplayName(stringArrayListEntry.getKey()+" >");
                button.setRoundness(0);
                button.setDimensions(2,0,max,12);
                button.setColor(UColor.getMainColor());
                add(button);
                y += 12;
            } else {
                UIButton button = stringArrayListEntry.getValue().get(0);
                button.setDimensions(2,0,max,12);
                y += 12;
                add(button);
            }

            if(stringArrayListEntry.getValue().size() > 1 && i < options.entrySet().size()-1) {
                UIButton line = new UIButton();
                line.setDimensions(2, 0, max, 1);
                line.setColor(Integer.MAX_VALUE);
                y += 1;
                add(line);
            }
        }

        setRoundness(2);
        setColor(UColor.getDarker(UColor.getDarker(UColor.getMainColor())));

        float height = Math.min(y+2,300);
        Animation.animate(f -> this.height = f,() -> 1f,height,0.2f);
        setDimensions(mouseX,mouseY,max+4,1);
    }

}
