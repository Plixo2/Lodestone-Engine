package net.plixo.lodestone.client.ui.screens;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.elements.other.UILabel;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.util.UColor;

public class ScreenAccept extends ScreenCanvasUI {

    Runnable yes, no;
    String displayString;

    public String SYes = "Yes";
    public String SNo = "No";
    Screen currentScreen;

    public ScreenAccept(Runnable yes, Runnable no, String displayString) {
        this.yes = yes;
        this.no = no;
        this.displayString = displayString;
        currentScreen = Minecraft.getInstance().currentScreen;
    }


    @Override
    protected void init() {
        super.init();
        if(getClass() != ScreenAccept.class) {
            return;
        }
        int wH = width / 2;
        int hH = (height / 2) + 30;
        int buttonWidth = 50;
        int buttonHeight = 20;


        canvas.setColor(0);

        UILabel label = new UILabel();
        label.alignMiddle();
        label.setDisplayName(displayString);
        label.setDimensions(width/2-40,height/2 - 50,80,12);

        UIButton ok = new UIButton();
        ok.setDimensions(wH - (10 + buttonWidth), hH, buttonWidth, buttonHeight);
        ok.setDisplayName(SYes);
        ok.setAction(() -> {
            yes.run();
            Minecraft.getInstance().displayGuiScreen(new ScreenMain());
        });


        UIButton nope = new UIButton();
        nope.setDimensions(wH + (10), hH, buttonWidth, buttonHeight);
        nope.setDisplayName(SNo);
        nope.setAction(() -> {
            no.run();
            Minecraft.getInstance().displayGuiScreen(new ScreenMain());
        });

        UICanvas middle = new UICanvas();
        middle.setDimensions(width/2 - 80 , height/2 - 60 , 160,120);
        middle.setRoundness(10);
        middle.setColor(UColor.getBackground(0));

        canvas.add(middle);
        canvas.add(ok);
        canvas.add(nope);
        canvas.add(label);
        canvas.setColor(UColor.getBackground(0.2f));


    }

}
