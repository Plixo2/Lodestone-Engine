package net.plixo.paper.client.ui.GUI;


import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class GUIAccept extends GUICanvas {

    Runnable yes, no;
    String displayString;

    public String SYes = "Yes";
    public String SNo = "No";
    Screen currentScreen;
    public GUIAccept(Runnable yes, Runnable no, String displayString) {
        this.yes = yes;
        this.no = no;
        this.displayString = displayString;
        currentScreen = Minecraft.getInstance().currentScreen;
    }


    @Override
    protected void init() {
        super.init();
        if(getClass() != GUIAccept.class) {
            return;
        }
        int wH = width / 2;
        int hH = (height / 2) + 30;
        int buttonWidth = 50;
        int buttonHeight = 20;

        addButton(new Button(wH - (10 + buttonWidth), hH, buttonWidth, buttonHeight, new StringTextComponent(SYes), a -> {
            yes.run();
            Minecraft.getInstance().displayGuiScreen(new GUIMain());
        }));
        addButton(new Button(wH + (10), hH, buttonWidth, buttonHeight, new StringTextComponent(SNo), b -> {
            no.run();
            Minecraft.getInstance().displayGuiScreen(new GUIMain());
        }));

        canvas.setColor(0);
    }

    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        Gui.setMatrix(p_230430_1_);
        Gui.drawRect(0, 0, width, height, ColorLib.getBackground(0.1f));
        Gui.drawRoundedRect(width / 2 - 71, height / 2 - 41, width / 2 + 71, height / 2 + 61, 7, ColorLib.getBackground(-0.1f));
        Gui.drawRoundedRect(width / 2 - 70, height / 2 - 40, width / 2 + 70, height / 2 + 60, 7, ColorLib.getBackground(0.3f));
        if(displayString != null)
        Gui.drawCenteredStringWithShadow(displayString,width/2, height / 2 - 20, -1);
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
    }

}
