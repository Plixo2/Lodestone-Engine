package net.plixo.lodestone.client.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.animation.Animation;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.MouseUtil;
import org.lwjgl.opengl.GL11;

public class ScreenCanvasUI extends Screen {

    public UICanvas canvas;
    float canvasSize = 0;

    public ScreenCanvasUI() {
        super(new StringTextComponent("UI"));
        try {
            createCanvas();
        } catch (Exception e) {
            UMath.print(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void init() {
        try {
            createCanvas();
        } catch (Exception e) {
          UMath.print(e);
          e.printStackTrace();
        }
        Animation.animate(f -> canvasSize = f , () -> 0f,1f,0.2f);
        super.init();
    }

    void createCanvas() {
            float width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            float height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            canvas = new UICanvas();
            canvas.setDimensions(0, 0, width, height);
            canvas.setRoundness(0);
            canvas.setColor(UColor.getBackground(0));
    }

    public void add(UIElement element) {
        try {
            canvas.add(element);
        } catch (Exception e) {
          UMath.print(e);
          e.printStackTrace();
        }
    }

    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        try {

            UGui.setMatrix(p_230430_1_);
            GL11.glPushMatrix();
            GL11.glTranslated(width/2,height/2,0);
            float size = canvasSize;
            GL11.glScaled(size,size,1);
            GL11.glTranslated(-width/2,-height/2,0);

            canvas.drawScreen(mouseX, mouseY);

            GL11.glPopMatrix();
            MouseUtil.resetWheel();

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
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
