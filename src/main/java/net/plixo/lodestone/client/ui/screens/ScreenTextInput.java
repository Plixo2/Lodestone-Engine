package net.plixo.lodestone.client.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.util.UMath;
import java.util.function.Consumer;

public class ScreenTextInput extends ScreenAccept {

    TextFieldWidget widget;
    public String Syes = "Rename", Sno = "Cancel";

    public ScreenTextInput(Consumer<String> yes, Consumer<String> no, String displayString) {
        super(null, null, displayString);
        super.yes = () -> yes.accept(widget.getText());
        super.no = () -> no.accept(widget.getText());
    }

    @Override
    protected void init() {
        super.init();
        int wH = width / 2;
        int hH = (height / 2);
        int buttonWidth = 70;
        int buttonHeight = 20;
        widget = new TextFieldWidget(Minecraft.getInstance().fontRenderer, wH - buttonWidth / 2, hH, buttonWidth, buttonHeight, new StringTextComponent(displayString));
        widget.setText(displayString);
        addListener(widget);


        addButton(new Button(wH - (10 + 50), hH + 30, 50, buttonHeight, new StringTextComponent(Syes), a -> {
            try {
                yes.run();
            } catch (Exception e) {
                UMath.print(e);
                e.printStackTrace();
            }
            Minecraft.getInstance().displayGuiScreen(new ScreenMain());
        }));
        addButton(new Button(wH + (10), hH + 30, 50, buttonHeight, new StringTextComponent(Sno), b -> {
            try {
                no.run();
            } catch (Exception e) {
                UMath.print(e);
                e.printStackTrace();
            }
            Minecraft.getInstance().displayGuiScreen(new ScreenMain());
        }));


        //super.init();
    }

    @Override
    public void render(MatrixStack p_230430_1_, int mouseX, int mouseY, float p_230430_4_) {
        super.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
        widget.render(p_230430_1_, mouseX, mouseY, p_230430_4_);
    }


}
