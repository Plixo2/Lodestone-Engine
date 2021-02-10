package net.plixo.paper.client.UI.elements;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;


public class UIPointNumber extends UIElement {

    TextFieldWidget field;


    public UIPointNumber(int id) {
        super(id);
        color = ColorLib.getBackground(0.3f);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f));
        Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, ColorLib.utilLines(), 1);


        field.render(Gui.matrixStack, (int) mouseX, (int) mouseY, 0);

        super.drawScreen(mouseX, mouseY);
    }

    //returns the content as Double
    public double getAsDouble() {
        String txt = getText();
        if (Util.isNumeric(txt)) {
            try {
                if (txt.length() > 0)
                    return Double.parseDouble(txt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    //returns the field content
    public String getText() {
        return field.getText();
    }

    //set the field content
    public void setValue(Object value) {
        field.setText(String.valueOf(value));
    }

    //inputs
    @Override
    public void keyPressed(int key, int scanCode, int action) {
        field.keyPressed(key, scanCode, action);
        super.keyPressed(key, scanCode, action);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        field.charTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        field.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //set dimensions for text field
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        field = new TextFieldWidget(Minecraft.getInstance().fontRenderer, (int) x + 4, (int) (y + height / 2) - 4,
                (int) width - 8, (int) height / 2, new StringTextComponent(""));
        field.setEnableBackgroundDrawing(false);
        super.setDimensions(x, y, width, height);
    }


}
