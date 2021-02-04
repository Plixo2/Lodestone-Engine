package net.plixo.paper.client.editor.ui.accept;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

public class UITextInput extends  UIAccept {

    TextFieldWidget widget;
    public UITextInput(TxtRun yes, TxtRun no, String displayString) {
        super(null, null, displayString);
        super.yes = () -> yes.run(getText());
        super.no = () -> no.run(getText());
    }

    @Override
    protected void init() {
        int wH = width/2;
        int hH = (height/2);
        int buttonWidth = 70;
        int buttonHeight = 20;
        widget = new TextFieldWidget(Minecraft.getInstance().fontRenderer,wH -buttonWidth/2, hH , buttonWidth , buttonHeight,new StringTextComponent(displayString));
        widget.setText(displayString);
        addListener(widget);
        super.init();
    }

    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
        widget.render(p_230430_1_ , mouseX,mouseY , p_230430_4_);
    }

    public String getText() {
        return widget.getText();
    }
    public interface TxtRun {
        void run(String txt);
    }

}
