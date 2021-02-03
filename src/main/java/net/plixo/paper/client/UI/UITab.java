package net.plixo.paper.client.UI;

import net.minecraft.client.Minecraft;

import net.plixo.paper.client.editor.ui.OptionMenu;
import net.plixo.paper.client.editor.visualscript.Rect;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


public class UITab implements IGuiEvent {

	public static Minecraft mc = Minecraft.getInstance();
	public Rect head;
	String name;
	public TabbedUI parent;
	public UITab(int id , String name) {
		this.head = new Rect(0, -12, TabbedUI.headWidth, 12, 0, 0);
		this.head.roundness = 0;
		this.head.id = id;
		this.head.txt = name;
		this.name = name;
	}
	


	public void drawOutline() {
		int lineColor = ColorLib.getMainColor();
		int width = 2;
		Gui.drawLine(0, 0, parent.width, 0, lineColor, width);
		Gui.drawLine(0, 0, 0, parent.height, lineColor, width);
		Gui.drawLine(0, parent.height, parent.width, parent.height, lineColor, width);
		Gui.drawLine(parent.width, 0, parent.width, parent.height, lineColor, width);
	}
	
	public void hideMenu() {
		parent.menu = null;
	}
	public void optionsSelected(int id , int option) {}
	void setParent(TabbedUI parent) {
		this.parent = parent;
	}
	public void showMenu(int id , float x , float y , String... options) {
		parent.menu = new OptionMenu(id, this, x, y, options);
	}
	
	
}
