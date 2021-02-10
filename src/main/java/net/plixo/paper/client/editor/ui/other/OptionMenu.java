package net.plixo.paper.client.editor.ui.other;


import net.plixo.paper.client.UI.IGuiEvent;
import net.plixo.paper.client.UI.elements.UIButton;
import net.plixo.paper.client.UI.elements.UICanvas;
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
        canvas = new UICanvas(0);
        canvas.setDimensions(x-5, y-5, width+10, (options.length * 12)+10);
        canvas.setRoundness(3);
        canvas.setColor(ColorLib.getBackground(-0.1f));

        int index = 0;
        for (TxtRun str : options) {
            UIButton button = new UIButton(index) {
                @Override
                public void actionPerformed() {
                   str.run();
                }
            };
            button.setDimensions(5,5+(index*12),width,12);
            button.setRoundness(0);
            button.setDisplayName(str.txt);
            button.setColor(ColorLib.getBackground(0.1f));
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
        String txt;
        public TxtRun(String txt) {
            this.txt = txt;
        }
        protected abstract void run();

    }
}
