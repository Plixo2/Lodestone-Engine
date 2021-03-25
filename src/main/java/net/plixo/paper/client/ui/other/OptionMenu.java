package net.plixo.paper.client.ui.other;


import net.plixo.paper.client.ui.IGuiEvent;
import net.plixo.paper.client.ui.elements.UIButton;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


public class OptionMenu implements IGuiEvent {

    public int id;
    UICanvas canvas;
    public TxtRun[] runnables;

    float x, y;

    public OptionMenu(int id,float x, float y, TxtRun... options) {
        this.x = x;
        this.y = y;
        this.id = id;
        float width = 0;
        for (TxtRun str : options) {
            float w = Gui.getStringWidth(str.txt);
            if (w > width) {
                width = w;
            }
        }
        width += 5;
        canvas = new UICanvas(0) {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getDarker(this.color), 1);
            }
        };
        canvas.setDimensions(x-2, y-2, width+4, (options.length * 12)+4);
        canvas.setRoundness(2);
        canvas.setColor(ColorLib.getBackground(0.3f));

        int index = 0;
        for (TxtRun str : options) {
            UIButton button = new UIButton(index) {
                @Override
                public void actionPerformed() {
                   str.run();
                }
            };
            button.setDimensions(2,2+(index*12),width,12);
            button.setRoundness(1);
            button.setDisplayName(str.txt);
            button.setColor(ColorLib.getBackground(0.2f));
            index += 1;
            canvas.add(button);
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        canvas.drawScreen(mouseX,mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        canvas.mouseClicked(mouseX,mouseY,mouseButton);
    }

    public abstract static class TxtRun {
        public String txt;
        public TxtRun(String txt) {
            this.txt = txt;
        }
        public abstract void run();

    }
}
