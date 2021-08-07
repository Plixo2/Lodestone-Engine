package net.plixo.lodestone.client.ui.resource.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;

/**
 *  for editing and displaying a integer in the UI
 *  using Minecraft {@code TextFieldWidget} and up and down buttons
 **/
public class UISpinner extends UICanvas {

    TextFieldWidget field;

    public UISpinner() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

       drawDefault( UColor.getBackground(0.3f));
        drawHoverEffect();


        field.render(UGui.getMatrixStack(), (int) mouseX, (int) mouseY, 0);

        super.drawScreen(mouseX, mouseY);
    }

    //returns the field content as number
    public int getNumber() {
        String txt = field.getText();
        if (UMath.isNumeric(txt)) {
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


    //set dimensions of + and - button
    @Override
    public void setDimensions(float x, float y, float width, float height) {

        UIButton up = new UIButton();
        up.setAction(() -> setNumber(getNumber() + 1));

        UIButton down = new UIButton();
        down.setAction(() ->  setNumber(getNumber() - 1));

        up.setDisplayName("+");
        up.setRoundness(getRoundness());
        up.setDimensions(width - height, 0, height, height / 2);

        down.setDisplayName("-");
        down.setRoundness(getRoundness());
        down.setDimensions(width - height, height / 2, height, height / 2);

        clear();
        add(up);
        add(down);


        field = new TextFieldWidget(Minecraft.getInstance().fontRenderer, (int) x + 4, (int) (y + height / 3), (int) (width - height)-4,
                (int) height / 2, new StringTextComponent(""));
        field.setEnableBackgroundDrawing(false);
        field.setTextColor(UColor.getTextColor());
        field.setMaxStringLength(100);

        super.setDimensions(x, y, width, height);
    }


}
