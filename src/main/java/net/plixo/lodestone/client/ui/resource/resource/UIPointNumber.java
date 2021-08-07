package net.plixo.lodestone.client.ui.resource.resource;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.util.UColor;

/**
 *  for editing and displaying a float or double in the UI
 *  using Minecraft {@code TextFieldWidget}
 **/
public class UIPointNumber extends UIElement {
    TextFieldWidget field;


    public UIPointNumber() {
        setColor(UColor.getBackground(0.3f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault();
        drawDefault(UColor.interpolateColorAlpha(0x00000000, 0x23000000, getHoverProgress() / 100f));
        UGui.drawLinedRoundedRect(x, y, x + width, y + height, getRoundness(), 0x6F000000, 1);


        field.render(UGui.getMatrixStack(), (int) mouseX, (int) mouseY, 0);

        super.drawScreen(mouseX, mouseY);
    }

    //make this better lololol
    public double getAsDouble() {
        String txt = getText();
        try {
            if(!txt.isEmpty())
            return Double.parseDouble(txt);
        } catch (Exception e) {
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
    public void setNumber(float number) {
        field.setText(String.valueOf(number));
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
        field.setTextColor(UColor.getTextColor());
        super.setDimensions(x, y, width, height);
    }


}
