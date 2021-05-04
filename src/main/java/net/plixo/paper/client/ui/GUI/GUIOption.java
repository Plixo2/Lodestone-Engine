package net.plixo.paper.client.ui.GUI;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.util.Options;
import net.plixo.paper.client.ui.elements.UIArray;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.ui.elements.UIToggleButton;
import net.plixo.paper.client.util.*;

public class GUIOption extends Screen {

    UICanvas mainCanvas;

    public GUIOption() {
        super(new StringTextComponent("Options"));
    }

    @Override
    protected void init() {

        mainCanvas = new UICanvas();
        mainCanvas.setDimensions(0, 0, width, height);
        mainCanvas.setRoundness(0);
        mainCanvas.setColor(ColorLib.getBackground(0));


        UIArray settingsArray = new UIArray();
        settingsArray.setDimensions(0, 0, 140, 40);

       // settingsArray.arrayheight = 20;


        UIToggleButton unicode = new UIToggleButton() {
            @Override
            public void actionPerformed() {
                super.actionPerformed();
                Options.useUnicode = getState();
            }
        };
        unicode.setYesNo("true", "false");
        unicode.setState(Options.useUnicode);
        unicode.setDimensions(0,0,140,30);

        UIToggleButton showMetadata = new UIToggleButton() {
            @Override
            public void actionPerformed() {
                super.actionPerformed();
                Options.showMetadata = getState();
            }
        };
        showMetadata.setYesNo("true", "false");
        showMetadata.setState(Options.showMetadata);
        showMetadata.setDimensions(0,0,140,30);

        UIToggleButton button = new UIToggleButton() {
            @Override
            public void actionPerformed() {
                super.actionPerformed();
                Options.usePreEvent = getState();
            }
        };
        button.setYesNo("Pre","Post");
        button.setState(Options.usePreEvent);
        button.setDimensions(0,0,140,30);



        settingsArray.add(unicode);
        settingsArray.add(showMetadata);
        settingsArray.add(button);

        mainCanvas.add(settingsArray);

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
//  VisualScriptManager.register();