package net.plixo.paper.client.UI.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.util.Gui;

public class RadialTextBox extends RadialMenu {

	
	TextFieldWidget field;
	
	public RadialTextBox(String name, float radiusMin, float radiusMax, String... options) {
		super(name, radiusMin, radiusMax, options);
		field = new TextFieldWidget(Minecraft.getInstance().fontRenderer, 0, 0, (int) (radiusMin*2f)-10, 20 , new StringTextComponent(""));
		field.setText(name);
		field.setCanLoseFocus(false);
		field.setFocused2(true);
	}




	@Override
	public void draw(float mouseX, float mouseY) {
	
		
		
		super.draw(mouseX, mouseY);
		
		
		if (shown) {
			
			field.x = (int) (x-radiusMin)+5;
			field.y = (int) (y-10);
			field.render(Gui.matrixStack , (int) mouseX , (int) mouseY , 0);
		}
		
	}
	public void keyTyped(char typedChar, int keyCode) {
		if (shown) {
		field.charTyped(typedChar, keyCode);
		//TODO add keyPressed if needed...
		}
		this.name = field.getText();
	}



	@Override
	public int mouseClicked(float mouseX, float mouseY, int mouseButton) {
		if (shown) {
			field.mouseClicked((int)mouseX, (int)mouseY, mouseButton);
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
}
