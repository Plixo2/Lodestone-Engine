package net.plixo.paper.client.UI.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;
import org.lwjgl.glfw.GLFW;

/**
 *  for editing and displaying a integer in the UI
 *  using Minecraft {@code TextFieldWidget} and up and down buttons
 **/
public class UISpinner extends UIMultiButton {

    public TextFieldWidget field;


    public UISpinner(int id) {
        super(id, new UIButton(0), new UIButton(1));
        others[0].displayName = "+";
        others[0].setRoundness(1);
        others[1].displayName = "-";
        others[1].setRoundness(1);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));
        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);


        field.render(Gui.matrixStack, (int) mouseX, (int) mouseY, 0);

        super.drawScreen(mouseX, mouseY);
    }

    //returns the field content as number
    public int getNumber() {
        String txt = field.getText();
        if (Util.isNumeric(txt)) {
            try {
                return Integer.parseInt(txt);
            } catch (Exception e) {
                setNumber(0);
                e.printStackTrace();
            }
        }
        return 0;
    }
    //set the value
    public void setNumber(int value) {
        field.setText(value + "");
    }

    //inputs
    @Override
    public void keyPressed(int key, int scanCode, int action) {
        field.keyPressed(key, scanCode, action);
        super.keyPressed(key, scanCode, action);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (Character.isDigit(typedChar)) {
            field.charTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        field.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //triggered from UIMultiButton for the + and - button
    @Override
    public void otherButton(int id) {
        if (id == 0) {
            setNumber(getNumber() + 1);
        } else {
            setNumber(getNumber() - 1);
        }
    }

    //set dimensions of + and - button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        others[0].setDimensions(width - height, 0, height, height / 2);
        others[1].setDimensions(width - height, height / 2, height, height / 2);
        field = new TextFieldWidget(Minecraft.getInstance().fontRenderer, (int) x + 2, (int) (y + height / 3), (int) (width - height),
                (int) height / 2, new StringTextComponent(""));
        field.setEnableBackgroundDrawing(false);
        super.setDimensions(x, y, width, height);
    }


}
