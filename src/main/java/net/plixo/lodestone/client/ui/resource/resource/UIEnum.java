package net.plixo.lodestone.client.ui.resource.resource;

import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.util.UColor;

public class UIEnum extends UICanvas {

    Class<? extends Enum<?>> enumType;
    Enum<?> type;
    int index = 0;

    public UIEnum() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault( UColor.getBackground(0.3f));
        drawHoverEffect();

        if (type != null) {
            UGui.drawString(type.name(), x + 3, y + height / 2, UColor.getTextColor());
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
        button.setDisplayName("<");
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
