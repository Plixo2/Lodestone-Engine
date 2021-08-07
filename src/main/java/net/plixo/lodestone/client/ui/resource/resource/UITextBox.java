package net.plixo.lodestone.client.ui.resource.resource;

import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.util.UColor;
import org.lwjgl.opengl.GL11;


/**
 *  for editing and displaying text in the UI
 *  using Minecraft {@code TextFieldWidget}
 **/
public class UITextBox extends UIElement {


    Runnable textChanged;
    public UITextBox() {
        super();
        setColor(UColor.getBackground(0.3f));
    }
    public TextFieldWidget field;

    @Override
    public void drawScreen(float mouseX, float mouseY) {

       drawDefault();
       drawHoverEffect();

        GL11.glPushMatrix();
        GL11.glTranslated(x,y,0);
        if(field.isFocused()) {
            field.render(UGui.getMatrixStack(), (int) (mouseX-x), (int) (mouseY-y), 0);
        } else {
            UGui.drawString(field.getText(),2,height/2,UColor.getTextColor());
        }

        GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY);
    }

    //get the field context
    public String getText() {
        return field.getText();
    }

    //inputs
    @Override
    public void keyTyped(char typedChar, int keyCode) {
        field.charTyped(typedChar, keyCode);
        if(textChanged != null)  {
            textChanged.run();
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        field.keyPressed(key, scanCode, action);
        if(textChanged != null)  {
            textChanged.run();
        }
        super.keyPressed(key, scanCode, action);
    }

    public void setTextChanged(Runnable textChanged) {
        this.textChanged = textChanged;
    }

    public Runnable getTextChanged() {
        return textChanged;
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        field.mouseClicked((int) mouseX-x, (int) mouseY-y, mouseButton);
        if(isHovered(mouseX,mouseY)) {
            field.setFocused2(true);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    //set text field dimensions
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        field = new TextFieldWidget(UGui.mc.fontRenderer, 4, (int) (height / 2) - 4, (int) width - 8, (int) height / 2, new StringTextComponent(""));
        //disable background
        field.setEnableBackgroundDrawing(false);
        field.setTextColor(UColor.getTextColor());
        field.setMaxStringLength(10000);
        super.setDimensions(x, y, width, height);
    }

    public void setText(String txt) {
        try {
            field.setText(txt);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
