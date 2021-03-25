package net.plixo.paper.client.ui.elements;


import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

/**
 * for editing and displaying a boolean in the UI
 * with different names for the two states
 **/
public class UIToggleButton extends UIElement {


    //current state
    protected boolean state;
    //default display text
    protected String StrFalse = "No";
    protected String StrTrue = "Yes";

    public UIToggleButton(int id) {
        super(id);
    }

    //triggered at mouseClicked if hovered
    @Override
    public void actionPerformed() {
        state = !state;
    }


    @Override
    public void drawScreen(float mouseX, float mouseY) {

        int buttonColor = color;
        if (!state) {
            //dark color if disabled and outline
            buttonColor = ColorLib.getBackground(0.2f);
            Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, -1, 1);
        }


        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, buttonColor);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.interpolateColorAlpha(0x00000000, 0x13000000, hoverProgress / 100f));

        //Display state as Text
        Gui.drawCenteredString(state ? StrTrue : StrFalse, x + width / 2, y + height / 2, textColor);

        super.drawScreen(mouseX, mouseY);
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
