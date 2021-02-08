package net.plixo.paper.client.editor.ui;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.UI.TabbedUI;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.tabs.*;
import net.plixo.paper.client.editor.tabs.TabModelViewer;
import net.plixo.paper.client.editor.ui.other.Toolbar;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import org.lwjgl.opengl.GL11;

public class UIEditor extends Screen {

	public UIEditor() {
		super(new StringTextComponent("UI"));
	}


	//static Minecraft mc = Minecraft.getInstance();

	ArrayList<TabbedUI> tabs = new ArrayList<>();
	Toolbar toolbar;

	@Override
	public boolean isPauseScreen() {
		return false;
	}


	@SuppressWarnings("NullableProblems")
	@Override
	public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float partialTicks) {

		Gui.setMatrix(p_230430_1_);

		Gui.drawRect(0,0,width,height,ColorLib.getBackground(0.2f));
		toolbar.draw(mouseX,mouseY);

		for (TabbedUI tab : tabs) {

			GL11.glPushMatrix();

			GL11.glTranslated(tab.x, tab.y, 0);

			//float newMx = mouseX - tab.x;
			//float newMy = mouseY - tab.y;

			tab.drawScreen(mouseX,mouseY);

			GL11.glPopMatrix();
		}
		MouseUtil.resetWheel();
		super.render(p_230430_1_ , mouseX , mouseY , partialTicks);
	}

	@Override
	protected void init() {


		tabs.clear();

		float side = width*0.2f;
		float heightSide = height*0.33f;

		toolbar = new Toolbar(0);
		toolbar.setDimensions(0,0,width,20);

		TabbedUI background = new TabbedUI(super.width - side, super.height - (heightSide+10), "Project");
		background.y = 30;
		background.x = side;

		background.addTab(new TabViewport(0));
		background.addTab(new TabModelViewer(1));


		TabbedUI explorer = new TabbedUI(side, (this.height/2f)-12, "Test0");
		explorer.y = 30;

		// Tab b0 = new TabEvents(0);
		UITab b2 = new TabExplorer(0);
		// explorer.addTab(b0);
		explorer.addTab(b2);

		TabbedUI console = new TabbedUI(this.width - side, heightSide-30, "Test0");
		console.y = this.height - (heightSide-30);
		console.x = side;

		UITab b3 = new TabFiles(0);
		UITab b7 = new TabConsole(1);
		UITab b9 = new TabTimeline(2);
		console.addTab(b3);
		console.addTab(b7);
		console.addTab(b9);

		TabbedUI modules = new TabbedUI(side, this.height/2.f - 30, "Test0");
		modules.y = this.height/2.f + 30;

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
		toolbar.mouseClicked((float)mouseX,(float)mouseY,mouseButton);
		if (mouseButton == 0) {
			for (TabbedUI tab : tabs) {
				float newMx = (float) (mouseX - tab.x);
				float newMy = (float) (mouseY - tab.y);

				UITab CTab = tab.getHoveredHead(newMx, newMy);

				if (CTab != null) {
					tab.selectedIndex = CTab.head.id;
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
			tab.close();
		}
		Lodestone.save();
		super.onClose();
	}

	@Override
	public void tick() {

		for (TabbedUI tab : tabs) {
			tab.onTick();
		}
		Lodestone.update("onTick", null);
		super.tick();
	}

}
