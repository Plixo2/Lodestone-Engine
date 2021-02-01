package net.plixo.paper.client.UI.elements;

import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;



public abstract class UIMultiButton extends UIElement {

	
	UIButton[] others;
 	public UIMultiButton(int id , UIButton... others) {
		super(id);
		this.others =others;
	}
 	
 	@Override
 	public void draw(float mouseX, float mouseY) {
 		
 	
 		Gui.drawLinedRoundetRect(x, y, x + width, y + height, roundness, ColorLib.utilLines(),1);
 		super.draw(mouseX, mouseY);
 		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
 		for(UIButton button : others) {
 			button.draw(mouseX-x, mouseY-y);
 		}
 		GL11.glPopMatrix();
 	
 	}
 	@Override
 	public boolean hovered(float mouseX, float mouseY) {
 	
 		if(!super.hovered(mouseX, mouseY)) {
 			return false;
 		}
 		
 		for(UIButton button : others) {
 			if(button.hovered(mouseX-x, mouseY-y)) {
 				return false;
 			}
 		}
 		
 		return true;
 	}
 	@Override
 	public void mouseClicked(float mouseX, float mouseY ,int mouseButton) {
 		
 		for(UIButton button : others) {
 			button.mouseClicked(mouseX-x, mouseY-y , mouseButton);
 			if(button.hovered(mouseX-x, mouseY-y)) {
 				otherButton(button.getId());
 			}
 		}
 		super.mouseClicked(mouseX, mouseY , mouseButton);
 	}
	
 	public abstract void otherButton(int id);
}
