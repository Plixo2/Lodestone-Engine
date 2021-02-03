package net.plixo.paper.client.UI.elements;


import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class UIToggleButton extends UIElement {


    boolean state;
    String StrFalse = "No";

    String StrTrue = "Yes";

    public UIToggleButton(int id) {
        super(id);
    }

    @Override
    public void actionPerformed() {
        state = !state;
    }


    @Override
    public void draw(float mouseX, float mouseY) {

        int BColor = color;
        if (!state) {
            BColor = ColorLib.getBackground(0.2f);
            Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, -1, 1);
        }

        @SuppressWarnings("unused") int color = ColorLib.interpolateColor(0x00000000, 0x33000000, hoverProgress / 100f);

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, BColor);

        Gui.drawCenteredString(state ? StrTrue : StrFalse, x + width / 2, y + height / 2, textColor);

        super.draw(mouseX, mouseY);
    }

    public boolean getState() {
        return state;
    }


    public void setState(boolean state) {
        this.state = state;
    }

    public void setYesNo(String yes, String no) {
        this.StrTrue = yes;
        this.StrFalse = no;
    }
}
