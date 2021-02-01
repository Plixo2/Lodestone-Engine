package net.plixo.paper.client.UI.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class UITextbox extends UIElement {

	
	TextFieldWidget field;
	public UITextbox(int id) {
		super(id);
		
	}
	@Override
	public void draw(float mouseX, float mouseY) {
		
		Gui.drawRoundetRect(x, y, x + width, y + height, roundness, ColorLib.getBackground());
		
		int color = ColorLib.interpolateColor(0x00000000, 0x23000000, hoverProgress / 100f);
		Gui.drawRoundetRect(x, y, x + width, y + height, roundness, color);
		Gui.drawLinedRoundetRect(x, y, x + width, y + height, roundness, ColorLib.utilLines() ,1);
		
		if(field != null)
			field.render(Gui.matrixStack , (int) mouseX , (int) mouseY , 0);
		
		super.draw(mouseX, mouseY);
	}

	public String getText() {
		if(field != null)
			return field.getText();
		
		return "";
	}
	
	@Override
 	public void keyTyped(char typedChar, int keyCode) {
 		if(field != null) {
 			field.charTyped(typedChar , keyCode);
			textFieldChanged();
 		}
 		super.keyTyped(typedChar, keyCode);
 	}

	@Override
	public void keyPressed(int key, int scanCode, int action) {
		if(field != null) {
			field.keyPressed(key, scanCode, action);
			textFieldChanged();
		}

		super.keyPressed(key, scanCode, action);
	}

	public void textFieldChanged() {

	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
		if(field != null)
			field.mouseClicked((int)mouseX, (int)mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	@Override
	public void setDimensions(float x, float y, float width, float height) {
		field = new TextFieldWidget(Minecraft.getInstance().fontRenderer, (int)x+4, (int)(y + height/2)-4,(int) width-8,(int) height/2 , new StringTextComponent(""));
		field.setEnableBackgroundDrawing(false);
		super.setDimensions(x, y, width, height);
	}
 	public void setText(String txt) {
		if(field != null)
		field.setText(txt);
	}
}
