package net.plixo.paper.client.ui.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.ui.elements.UIArray;
import net.plixo.paper.client.ui.elements.UIButton;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.ui.elements.UIToggleButton;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import net.plixo.paper.client.util.Options;

public class GUICanvas extends Screen {

    UICanvas mainCanvas;

    public GUICanvas() {
        super(new StringTextComponent("UI"));
    }

    @Override
    protected void init() {
        mainCanvas = new UICanvas(0);
        mainCanvas.setDimensions(0, 0, width, height);
        mainCanvas.setRoundness(0);
        mainCanvas.setColor(ColorLib.getBackground(0));
        super.init();
    }

    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        Gui.setMatrix(p_230430_1_);
        mainCanvas.drawScreen(mouseX, mouseY);
        MouseUtil.resetWheel();
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        mainCanvas.mouseClicked((float) mouseX, (float) mouseY, mouseButton);
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        mainCanvas.mouseReleased((float) mouseX, (float) mouseY, state);
        return false;
    }

    @Override
    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        MouseUtil.addDWheel((float) p_231043_5_);
        return false;
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int action) {
        mainCanvas.keyPressed(key, scanCode, action);
        return super.keyPressed(key, scanCode, action);
    }

    @Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        mainCanvas.keyTyped(p_231042_1_, p_231042_2_);
        return false;
    }

}
