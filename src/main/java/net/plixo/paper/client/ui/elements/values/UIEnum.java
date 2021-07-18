package net.plixo.paper.client.ui.elements.values;

import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class UIEnum extends UICanvas {

    Class<? extends Enum<?>> enumType;
    Enum<?> type;
    int index = 0;

    public UIEnum() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));
        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);

        if (type != null) {
            Gui.drawString(type.name(), x + 3, y + height / 2, -1);
        }

        super.drawScreen(mouseX, mouseY);
    }

    public void setEnumType(Enum<?> type) {
        this.type = type;
        index = type.ordinal();
        enumType = type.getDeclaringClass();
    }

    public Enum<?> getType() {
        return type;
    }




    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIButton button = new UIButton();
        button.displayName = "<";
        button.setAction(() -> {
            index += 1;
            type = enumType.getEnumConstants()[index % enumType.getEnumConstants().length];
        });
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);

        super.setDimensions(x, y, width, height);
    }


}
