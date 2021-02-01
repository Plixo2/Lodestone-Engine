package net.plixo.paper.toDelete;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.blueprint.Canvas;
import net.plixo.paper.client.engine.buildIn.blueprint.BlueprintManager;
import net.plixo.paper.client.engine.buildIn.blueprint.Module;
import net.plixo.paper.client.engine.buildIn.blueprint.function.Function;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;

public class TabFunctions extends UITab {

	boolean dragedSlider = false;
	TextFieldWidget searchField;
	float yStart = 20;

	public TabFunctions(int id) {
		super(id, "Functions");
	}

	@Override
	public void draw(float mouseX, float mouseY) {

		Gui.drawRect(0, 0, parent.width, parent.height, 0xFF1B1F26);

		int index = 0;
		float y = yStart;

		for (Function function : BlueprintManager.allFunctions) {

			String name = function.name;
			int color = 0xFF3F403C;
			int txtColor = -1;

			if (hovered(mouseX, mouseY, index) && !dragedSlider) {
				color = ColorLib.getBrighter(color);
			}

			if (!name.toLowerCase().contains(searchField.getText().toLowerCase())
					&& !name.toUpperCase().contains("CUSTOM")) {
				continue;
			}

			Gui.drawRect(5, y, parent.width, y + 20, color);
			Gui.drawString(name, 12, y + 10, txtColor);
			index += 1;
			y += 20;
		}

		if (dragedSlider) {
			float w = (parent.height / 20) - 1;
			float max = -(BlueprintManager.allFunctions.size() - w) * 20;

			float h = parent.height - 70;
			float sy = (mouseY - 35) / (parent.height - 40);
			float yS = 20 + (sy * (max - 40));

			yS = Math.min(20, Math.max(max, yS));
			yStart = yS;
		}

		float w = (parent.height / 20) - 1;
		float max = -(BlueprintManager.allFunctions.size() - w) * 20;

		float h = parent.height - 70;
		float sy = (((yStart - 20) / (max - 20)));
		float yS = 35 + (sy * h);

		Gui.drawRect(2, 20, 3, parent.height - 20, -1);
		Gui.drawRect(1, yS - 15, 4, yS + 15, ColorLib.getMainColor());

		if (parent.isMouseInside(mouseX, mouseY)) {

			float dir = Math.signum(MouseUtil.getDWheel());
			if (dir != 0) {
				yStart += 15f * dir;
				yStart = Math.max(Math.min(yStart, 20), max);
			}
		}
		searchField.render(Gui.matrixStack , (int) mouseX , (int) mouseY , 0);
	}

	boolean hovered(float mouseX, float mouseY, int index) {
		float y = yStart + (index * 20);
		return mouseX >= 5 && mouseX <= parent.width && mouseY >= y && mouseY < y + 20;
	}

	@Override
	public void init() {


		searchField = new TextFieldWidget(Minecraft.getInstance().fontRenderer, 0, 0, (int) parent.width,
				(int) yStart , new StringTextComponent(""));
		searchField.setCanLoseFocus(true);
		// searchField.set
		super.init();
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {

		searchField.charTyped(typedChar, keyCode);
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void keyPressed(int key, int scanCode, int action) {
		searchField.keyPressed(key , scanCode , action);
		super.keyPressed(key, scanCode, action);
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

		searchField.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
		if (!parent.isMouseInside(mouseX, mouseY) || mouseY <= 20) {
			return;
		}

		if (mouseButton == 0 && mouseX < 7) {
			dragedSlider = true;
			return;
		}

		// searchField.mouseClicked((int) mouseX, (int) mouseY, mouseButton);

		int index = 0;
		for (Function function : BlueprintManager.allFunctions) {

			String name = function.name;

			if (!name.toLowerCase().contains(searchField.getText().toLowerCase())
					&& !name.toUpperCase().contains("CUSTOM")) {
				continue;
			}

			if (hovered(mouseX, mouseY, index)) {
				if (mouseButton == 0) {
					Function func = BlueprintManager.getFromList(name, searchField.getText());
					Module mod = TheEditor.activeMod;
					if (mod != null) {
						Canvas tab = mod.getTab();
						if (tab != null) {

							/*
							Vector3d toWorld = TheEditor.viewport.screenToWorld(TheEditor.viewport.parent.width / 2,
									TheEditor.viewport.parent.height / 2);
						
							if (func != null) {
								tab.addFunction(func, (float) toWorld.x, (float) toWorld.y);
							}
							*/
							return;
						}
					}
				}
			}
			index += 1;
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void mouseReleased(float mouseX, float mouseY, int state) {
		if (state == 0) {
			dragedSlider = false;
		}
		super.mouseReleased(mouseX, mouseY, state);
	}
}
