package net.plixo.paper.client.UI;

import net.minecraft.client.Minecraft;

import net.plixo.paper.client.UI.other.OptionMenu;
import net.plixo.paper.client.editor.blueprint.Rect;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


public class UITab {

	public static Minecraft mc = Minecraft.getInstance();
	public Rect head;
	String name;
	public TabbedUI parent;
	public UITab(int id , String name) {
		this.head = new Rect(0, -20, TabbedUI.headWidth, 20, ColorLib.getBackground(), ColorLib.getDarker(ColorLib.getBackground()));
		this.head.roundness = 0;
		this.head.id = id;
		this.head.txt = name;
		this.name = name;
	}
	
	public void draw(float mouseX, float mouseY) {
		int lineColor = ColorLib.getMainColor();
		int width = 2;
		Gui.drawLine(0, 0, parent.width, 0, lineColor, width);
		Gui.drawLine(0, 0, 0, parent.height, lineColor, width);
		Gui.drawLine(0, parent.height, parent.width, parent.height, lineColor, width);
		Gui.drawLine(parent.width, 0, parent.width, parent.height, lineColor, width);
	}
	
	public void exit() {}
	
	
	public void hideMenu() {
		parent.menu = null;
	}
	
	public void init() {}
	
	public void keyPressed(int key , int scanCode , int action) {}
	public void keyTyped(char typedChar, int keyCode) {}
	public void mouseClicked(float mouseX, float mouseY, int mouseButton) {}
	public void mouseReleased(float mouseX, float mouseY, int state) {}	
	public void optionsSelected(int id , int option) {}
	void setParent(TabbedUI parent) {
		this.parent = parent;
	}
	public void showMenu(int id , float x , float y , String... options) {
		parent.menu = new OptionMenu(id, this, x, y, options);
	}
	public void updateScreen() {}
	
	
}