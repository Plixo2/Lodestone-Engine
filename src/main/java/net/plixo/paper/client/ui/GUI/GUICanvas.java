package net.plixo.paper.client.ui.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;

public class GUICanvas extends Screen {

    public UICanvas canvas;

    public GUICanvas() {
        super(new StringTextComponent("UI"));
    }

    @Override
    protected void init() {
        canvas = new UICanvas();
        canvas.setDimensions(0, 0, width, height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0));
        super.init();
    }

    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        Gui.setMatrix(p_230430_1_);
        canvas.drawScreen(mouseX, mouseY);
        MouseUtil.resetWheel();
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        canvas.mouseClicked((float) mouseX, (float) mouseY, mouseButton);
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        canvas.mouseReleased((float) mouseX, (float) mouseY, state);
        return false;
    }

    @Override
    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        MouseUtil.addDWheel((float) p_231043_5_);
        return false;
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int action) {
        canvas.keyPressed(key, scanCode, action);
        return super.keyPressed(key, scanCode, action);
    }

    @Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        canvas.keyTyped(p_231042_1_, p_231042_2_);
        return false;
    }

}
