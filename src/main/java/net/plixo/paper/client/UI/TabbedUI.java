package net.plixo.paper.client.UI;

import java.util.ArrayList;

import net.plixo.paper.client.UI.other.OptionMenu;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;


public class TabbedUI implements IGuiEvent {


	static float headWidth = 60;
	public OptionMenu menu;
	String name;
	public int selectedIndex = 0;
	public ArrayList<UITab> tabs = new ArrayList<>();

	//TODO redo option tab system

	public float width, height;
	public float x, y;

	public TabbedUI(float width, float height, String name) {
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public void addTab(UITab tab) {
		tab.setParent(this);
		tab.init();
		tabs.add(tab);
	}

	@Override
	public void drawScreen(float mouseX, float mouseY) {

	for (int i = 0; i < tabs.size(); i++) {
			UITab tab = tabs.get(i);
			tab.head.x = i * headWidth;

			tab.head.color = ColorLib.getBackground(0);
			tab.head.hoverColor = ColorLib.getBrighter(tab.head.color);

			if (selectedIndex == i) {

				GL11.glPushMatrix();
				Gui.createScissorBox(x, y, x + width, y + height + 1);
				Gui.activateScissor();

				tab.drawScreen(mouseX-x,mouseY-y);
				Gui.deactivateScissor();

				GL11.glPopMatrix();

				tab.head.color = ColorLib.orange();
				tab.head.hoverColor = ColorLib.getDarker(tab.head.color);
			}
			tab.head.draw(mouseX, mouseY);
		}
		//Gui.drawRect(tabs.size()*headWidth,-12,width, 0 , ColorLib.getBackground(0));


		if (menu != null) {
			menu.draw(mouseX, mouseY);
		}

	}

	@Override
	public void close() {
		tabs.get(selectedIndex).close();
	}

	public UITab getHoveredHead(float mouseX, float mouseY) {
		for (UITab tab : tabs) {
			if (tab.head.mouseInside(mouseX, mouseY , -1)) {
				return tab;
			}
		}
		return null;
	}
	
	public boolean isMouseInside(float mouseX, float mouseY) {
		return mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height;
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		tabs.get(selectedIndex).keyTyped(typedChar, keyCode);
	}

	@Override
	public void keyPressed(int key, int scanCode, int action) {
		tabs.get(selectedIndex).keyPressed(key , scanCode , action);
	}


	@Override
	public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
		optionsSelected(mouseX, mouseY, mouseButton);
		tabs.get(selectedIndex).mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void mouseReleased(float mouseX, float mouseY, int state) {
		tabs.get(selectedIndex).mouseReleased(mouseX, mouseY, state);
	}

	public void optionsSelected(float mouseX, float mouseY, int mouseButton) {

		UITab tab = tabs.get(selectedIndex);
		if (menu != null && tab == menu.tab) {

			int index = menu.getIndex(mouseX, mouseY, mouseButton);
			if (index >= 0)
				tab.optionsSelected(menu.id , index);
		}
	}

	@Override
	public void onTick() {
		tabs.get(selectedIndex).onTick();
	}

}
