package net.plixo.paper.client.ui.elements;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.ui.GUI.GUIAccept;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class UIColorChooser extends UICanvas {

    int customColor = 0;
    String colorName = "";
    UIButton button;

    public UIColorChooser() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));
        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);


        if (button != null) {
            button.setColor(customColor);
        }

        Gui.drawString(colorName, x+4, y + height / 2, -1);


        super.drawScreen(mouseX, mouseY);
    }

    public int getCustomColor() {
        return customColor;
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        button = new UIButton();
        button.setAction(() -> {
            float w = Minecraft.getInstance().getMainWindow().getScaledWidth();
            float h = Minecraft.getInstance().getMainWindow().getScaledHeight();

            UIButton colorIndicator = new UIButton();
            colorIndicator.setDimensions(w / 2 - 50, h * 0.25f, 100, 50);
            colorIndicator.setColor(customColor);

            UISpinner red = new UISpinner();
            red.setDimensions(w / 2 - 75, h / 2 - 44, 150, 20);
            red.setNumber(ColorLib.getRed(customColor));

            UISpinner green = new UISpinner();
            green.setDimensions(w / 2 - 75, h / 2 - 22, 150, 20);
            green.setNumber(ColorLib.getGreen(customColor));

            UISpinner blue = new UISpinner();
            blue.setDimensions(w / 2 - 75, h / 2, 150, 20);
            blue.setNumber(ColorLib.getBlue(customColor));

            UIButton nameLabel = new UIButton();
            nameLabel.setTextColor(0xFF000000);
            nameLabel.setColor(-1);
            nameLabel.setDimensions(0, 0, w, 20);
            nameLabel.setDisplayName(colorName);
            GUIAccept guiAccept = new GUIAccept(() -> {
                setCustomColor(ColorLib.fromInts(red.getNumber(), green.getNumber(), blue.getNumber()));
            }, () -> {
            }, null) {
                @Override
                public boolean charTyped(char p_231042_1_, int p_231042_2_) {
                    boolean result = super.charTyped(p_231042_1_, p_231042_2_);
                    setCustomColor(ColorLib.fromInts(red.getNumber(), green.getNumber(), blue.getNumber()));
                    nameLabel.setDisplayName(colorName);
                    colorIndicator.setColor(customColor);
                    return result;
                }

                @Override
                public boolean keyPressed(int key, int scanCode, int action) {
                    boolean result = super.keyPressed(key, scanCode, action);
                    setCustomColor(ColorLib.fromInts(red.getNumber(), green.getNumber(), blue.getNumber()));
                    nameLabel.setDisplayName(colorName);
                    colorIndicator.setColor(customColor);
                    return result;
                }

                @Override
                public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
                    boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);
                    setCustomColor(ColorLib.fromInts(red.getNumber(), green.getNumber(), blue.getNumber()));
                    nameLabel.setDisplayName(colorName);
                    colorIndicator.setColor(customColor);
                    return result;
                }
            };
            guiAccept.SYes = "ok";
            guiAccept.SNo = "cancel";
            Minecraft.getInstance().displayGuiScreen(guiAccept);
            guiAccept.canvas.setColor(ColorLib.getBackground(0));
            guiAccept.canvas.add(colorIndicator);
            guiAccept.canvas.add(red);
            guiAccept.canvas.add(green);
            guiAccept.canvas.add(blue);
            guiAccept.canvas.add(nameLabel);
        });
        button.displayName = ">";
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }

    void updateColorName() {
        colorName = ColorLib.getColorName(customColor);
    }

    public void setCustomColor(int color) {
        this.customColor = color;
        updateColorName();
    }

}
