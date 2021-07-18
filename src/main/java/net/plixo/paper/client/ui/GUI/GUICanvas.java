package net.plixo.paper.client.ui.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import net.plixo.paper.client.util.Util;

public class GUICanvas extends Screen {

    public UICanvas canvas;

    public GUICanvas() {
        super(new StringTextComponent("UI"));
        try {
            createCanvas();
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void init() {
        try {
            createCanvas();
        } catch (Exception e) {
          Util.print(e);
          e.printStackTrace();
        }
        super.init();
    }

    void createCanvas() {
            float width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            float height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            canvas = new UICanvas();
            canvas.setDimensions(0, 0, width, height);
            canvas.setRoundness(0);
            canvas.setColor(ColorLib.getBackground(0));
    }

    public void add(UIElement element) {
        try {
            canvas.add(element);
        } catch (Exception e) {
          Util.print(e);
          e.printStackTrace();
        }
    }

    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        try {
            Gui.setMatrix(p_230430_1_);
            canvas.drawScreen(mouseX, mouseY);
            MouseUtil.resetWheel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        try {
            canvas.mouseClicked((float) mouseX, (float) mouseY, mouseButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        try {
            canvas.mouseReleased((float) mouseX, (float) mouseY, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean mouseScrolled(double p_231043_1_, double p_231043_3_, double p_231043_5_) {
        try {
            MouseUtil.addDWheel((float) p_231043_5_);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.mouseScrolled(p_231043_1_, p_231043_3_, p_231043_5_);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int action) {
        try {
            canvas.keyPressed(key, scanCode, action);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.keyPressed(key, scanCode, action);
    }

    @Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        try {
            canvas.keyTyped(p_231042_1_, p_231042_2_);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.charTyped(p_231042_1_, p_231042_2_);
    }

    @Override
    public void tick() {
        try {
            canvas.onTick();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.tick();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
