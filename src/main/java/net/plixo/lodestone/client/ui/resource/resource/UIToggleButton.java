package net.plixo.lodestone.client.ui.resource.resource;


import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.util.UColor;

/**
 * for editing and displaying a boolean in the UI
 * with different names for the two states
 **/
public class UIToggleButton extends UIElement {


    //current state
    boolean state;
    //default display text
    String StrFalse = "False";
    String StrTrue = "True";


    @Override
    public void drawScreen(float mouseX, float mouseY) {

        int buttonColor = UColor.getBackground(0.6f);
        int textColor = UColor.red();
        if (state) {
            textColor = UColor.green();
            buttonColor = UColor.getBackground(0.4f);
            //   UGui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, -1, 2);
        }

        drawDefault(buttonColor);
        drawDefault(UColor.interpolateColorAlpha(0x00000000, 0x13000000, getHoverProgress() / 100f));
        //Display state as Text
        UGui.drawCenteredString(state ? StrTrue : StrFalse, x + width / 2, y + height / 2, textColor);

        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            state = !state;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //returns the state
    public boolean getState() {
        return state;
    }

    //set current pos
    public void setState(boolean state) {
        this.state = state;
    }

    //set different display texts
    public void setYesNo(String yes, String no) {
        this.StrTrue = yes;
        this.StrFalse = no;
    }
}
