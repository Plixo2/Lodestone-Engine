package net.plixo.paper.client.UI.elements;


import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class UIButton extends UIElement {


    String hoverName;

    public UIButton(int id) {
        super(id);
        this.setColor(ColorLib.orange());
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, this.color);
        Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getDarker(this.color), 1);
        int color = ColorLib.interpolateColor(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);
        drawStringCentered(mouseX, mouseY);
        super.draw(mouseX, mouseY);
    }

    @SuppressWarnings("unused")
	public void setHoverName(String name) {
        this.hoverName = name;
    }

    public void drawStringCentered(float mouseX, float mouseY) {

        if (hoverName != null) {
            if (hovered(mouseX, mouseY)) {
                Gui.drawCenteredString(hoverName, x + width / 2, y + height / 2, textColor);
                return;
            }
        }
        if (displayName != null) {
            Gui.drawCenteredString(displayName, x + width / 2, y + height / 2, textColor);
        }
    }
}
