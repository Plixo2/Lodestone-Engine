package net.plixo.paper.client.editor.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class UIAccept extends Screen {

    Button.IPressable yes, no;
    String displayScreen;

    public UIAccept(Button.IPressable yes, Button.IPressable no, String displayString) {
        super(new StringTextComponent("Accept"));
        this.yes = yes;
        this.no = no;
        this.displayScreen = displayString;
    }


    @Override
    protected void init() {
        int wH = width / 2;
        int hH = (height / 2) + 30;
        int buttonWidth = 50;
        int buttonHeight = 20;

        addButton(new Button(wH - (10 + buttonWidth), hH, buttonWidth, buttonHeight, new StringTextComponent("Yes"), a -> {
            yes.onPress(a);
            Minecraft.getInstance().displayGuiScreen(new UIEditor());
        }));
        addButton(new Button(wH + (10), hH, buttonWidth, buttonHeight, new StringTextComponent("No"), b -> {
            no.onPress(b);
            Minecraft.getInstance().displayGuiScreen(new UIEditor());
        }));
        super.init();
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        Gui.drawRect(0, 0, width, height, ColorLib.getBackground(0.1f));
        Gui.drawRoundedRect(width / 2.f - 70, height / 2.f - 40, width / 2.f + 70, height / 2.f + 60, 7, ColorLib.getBackground(0.3f));
        Gui.setMatrix(p_230430_1_);
        Gui.drawCenteredStringWithShadow(displayScreen, width / 2.f, height / 2.f - 20, -1);
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
    }

}
