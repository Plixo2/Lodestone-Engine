package net.plixo.paper.client.editor.ui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.Paper;
import net.plixo.paper.client.UI.TabbedUI;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.tabs.*;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import net.minecraft.client.Minecraft;

public class UIEditor extends Screen {

	public UIEditor() {
		super(new StringTextComponent("UI"));
	}


	static Minecraft mc = Minecraft.getInstance();

	ArrayList<TabbedUI> tabs = new ArrayList<TabbedUI>();

	@Override
	public boolean isPauseScreen() {
		return false;
	}


	@Override
	public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float partialTicks) {

		Gui.setMatrix(p_230430_1_);



		for (TabbedUI tab : tabs) {

			GL11.glPushMatrix();

			GL11.glTranslated(tab.x, tab.y, 0);

			float newMx = mouseX - tab.x;
			float newMy = mouseY - tab.y;

			tab.draw(newMx, newMy);

			GL11.glPopMatrix();
		}
		MouseUtil.resetWheel();
		super.render(p_230430_1_ , mouseX , mouseY , partialTicks);
	}

	@Override
	protected void init() {


		tabs.clear();

		TabbedUI background = new TabbedUI(super.width - 200, super.height - 200, "Project");
		background.y = 20;
		background.x = 200;

		background.addTab(new TabViewport(0));
		background.addTab(new TabHud(1));

		TabbedUI explorer = new TabbedUI(200, this.height /2 -10, "Test0");
		explorer.y = 20;

		// Tab b0 = new TabEvents(0);
		UITab b2 = new TabExplorer(0);
		// explorer.addTab(b0);
		explorer.addTab(b2);

		TabbedUI console = new TabbedUI(this.width - 200, 160, "Test0");
		console.y = this.height - 160;
		console.x = 200;

		UITab b3 = new TabFiles(0);
		UITab b7 = new TabConsole(1);
		UITab b8 = new TabFunction(2);
		console.addTab(b3);
		console.addTab(b7);
		console.addTab(b8);

		TabbedUI modules = new TabbedUI(200, this.height/2 - 30, "Test0");
		modules.x = 0;
		modules.y = this.height/2 + 30;

		UITab b4 = new TabInspector(0);
		modules.addTab(b4);

		tabs.add(background);
		tabs.add(explorer);
		tabs.add(console);
		tabs.add(modules);



		super.init();
	}


	@Override
	public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {

		MouseUtil.addDWheel((float) p_231043_5_);
		return false;
	}

	@Override
	public boolean charTyped(char typedChar, int keyCode) {

		for (TabbedUI tab : tabs) {
			tab.keyTyped(typedChar, keyCode);
		}

		return false;
	}

	@Override
	public boolean keyPressed(int key, int scanCode, int action) {
		for (TabbedUI tab : tabs) {
			tab.keyPressed(key , scanCode , action);
		}
		return super.keyPressed(key, scanCode, action);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (mouseButton == 0) {
			for (TabbedUI tab : tabs) {
				float newMx = (float) (mouseX - tab.x);
				float newMy = (float) (mouseY - tab.y);

				UITab Ctab = tab.getHoveredHead(newMx, newMy);

				if (Ctab != null) {
					tab.selectedIndex = Ctab.head.id;
				}
			}
		}

		for (TabbedUI tab : tabs) {
			float newMx = (float) (mouseX - tab.x);
			float newMy = (float) (mouseY - tab.y);

			tab.mouseClicked(newMx, newMy, mouseButton);

		}
		return false;
	}


	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int state) {
		for (TabbedUI tab : tabs) {
			float newMx = (float) (mouseX - tab.x);
			float newMy = (float) (mouseY - tab.y);
			tab.mouseReleased(newMx, newMy, state);
		}
		return false;
	}

	@Override
	public void onClose() {
		for (TabbedUI tab : tabs) {
			tab.exit();
		}
		Paper.save();
		super.onClose();
	}

	@Override
	public void tick() {

		for (TabbedUI tab : tabs) {
			tab.updateScreen();
		}
		Paper.update("onTick", null);
		super.tick();
	}

}