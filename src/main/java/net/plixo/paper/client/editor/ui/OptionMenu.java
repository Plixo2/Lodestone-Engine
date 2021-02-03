package net.plixo.paper.client.editor.ui;



import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.visualscript.Rect;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;


public class OptionMenu {

	public int id;
	public String[] options;
	ArrayList<Rect> rects = new ArrayList<>();
	public UITab tab;
	
	float x, y;
	public OptionMenu(int id , UITab tab , float x , float y, String... options) {
		int index = 0;
		this.tab = tab;
		this.x = x;
		this.y = y;
		this.id = id;
		float width = 0;
		this.options = options;
		for (String str : options) {
			float w = Gui.getStringWidth(str);
			if(w > width) {
				width = w;
			}
		}
		rects.clear();
		for (String str : options) {
			Rect rect = new Rect(0, index * 12, width + 5, 12, ColorLib.getBackground(0.4f), ColorLib.getBackground(0.3f));
			rect.roundness = 0;
			rect.setTxt(str, Rect.Alignment.LEFT);
			rects.add(rect);
			index += 1;
		}
	}

	public void draw(float mouseX, float mouseY) {
		GL11.glTranslated(x, y, 0);
		mouseX -= x;
		mouseY -= y;
		for (Rect r : rects) {
			r.draw(mouseX, mouseY);
		}
		GL11.glTranslated(-x, -y, 0);
	}

	public int getIndex(float mouseX, float mouseY, int mouseButton) {
		mouseX -= x;
		mouseY -= y;
		int index = 0;
		for (Rect r : rects) {
			if (r.mouseInside(mouseX, mouseY, mouseButton)) {
				return index;
			}
			index += 1;
		}
		return -1;
	}
}
