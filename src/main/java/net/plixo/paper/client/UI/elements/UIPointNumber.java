package net.plixo.paper.client.UI.elements;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;
import org.lwjgl.glfw.GLFW;

public class UIPointNumber extends UIElement {

	TextFieldWidget field;

	long lastComMS = 0;

	String lastComputedValue = "";

	public UIPointNumber(int id) {
		super(id);

	}

	@Override
	public void draw(float mouseX, float mouseY) {

		Gui.drawRoundetRect(x, y, x + width, y + height, roundness, ColorLib.getBackground());

		int color = ColorLib.interpolateColor(0x00000000, 0x23000000, hoverProgress / 100f);
		Gui.drawRoundetRect(x, y, x + width, y + height, roundness, color);
		Gui.drawLinedRoundetRect(x, y, x + width, y + height, roundness, ColorLib.utilLines(), 1);

		String toDraw = lastComputedValue;
		toDraw = Util.displayTrim(toDraw, 40);
		Gui.drawString(toDraw, x + width - Gui.getStringWidth(toDraw), y + height - 4, textColor);

		long ms = System.currentTimeMillis();
		if (ms - lastComMS > 1000) {
			lastComputedValue = getAsDouble() + "";
			lastComMS = ms;
		}

		if (field != null)
			field.render(Gui.matrixStack , (int) mouseX , (int) mouseY , 0);

		super.draw(mouseX, mouseY);
	}
	public double getAsDouble() {
		String txt = getText();
		if (Util.isNumeric(txt)) {
			try {
				if (txt.length() > 0)
					return Double.valueOf(txt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		} else {
			/*
			Argument arg = new Argument("val", txt);
			double val = arg.getArgumentValue();
			String newVal = "" + arg.getArgumentValue();
			if (Util.isNumeric(newVal)) {
				try {
					if (newVal.length() > 0)
						return Double.valueOf(newVal);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			*/
			return 0;
		}

	}

	public String getText() {
		if (field != null) {
			return field.getText();
		}
		return "";
	}

	@Override
	public void keyPressed(int key, int scanCode, int action) {
		if (field != null) {
			field.keyPressed(key , scanCode , action);
		}
		super.keyPressed(key, scanCode, action);
	}

	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if (field != null) {
			field.charTyped(typedChar, keyCode);
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
		if (field != null)
			field.mouseClicked((int) mouseX, (int) mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void setDimensions(float x, float y, float width, float height) {
		field = new TextFieldWidget( Minecraft.getInstance().fontRenderer, (int) x + 4, (int) (y + height / 2) - 4,
				(int) width - 8, (int) height/2 , new StringTextComponent(""));
		field.setEnableBackgroundDrawing(false);
		super.setDimensions(x, y, width, height);
	}

	public void setText(String txt) {
		if (field != null)
			field.setText(txt);
	}

	public void setValue(Object value) {
		if (field != null)
			field.setText(String.valueOf(value));
	}
}
