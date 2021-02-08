package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.UIButton;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.engine.components.timeline.Timeline;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

import java.io.File;

public class TabTimeline extends UITab {


    public Timeline currentTimeline;
    UICanvas canvas;

    public TabTimeline(int id) {
        super(id, "Timeline");
        TheEditor.timeline = this;
    }

    @Override
    public void init() {
        canvas = new UICanvas(0);
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.1f));

        UICanvas time = new UICanvas(0) {

            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                hideMenu();
                if (hovered(mouseX, mouseY) && mouseButton == 1 && currentTimeline != null) {
                    float percentX = (mouseX - x) / width;
                    float percentY = (mouseY - y) / height;
                    OptionMenu.TxtRun runnable = new OptionMenu.TxtRun("new Point") {
                        @Override
                        protected void run() {
                            if (currentTimeline != null) {
                                currentTimeline.add(new Timeline.Timepoint(percentX, percentY));
                            }
                        }
                    };
                    showMenu(0, mouseX, mouseY, runnable);

                }
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }

            @Override
            public void draw(float mouseX, float mouseY) {
                super.draw(mouseX, mouseY);
                float spacing = width / 10;
                for (int i = 0; i <= 10; i++) {
                    float lineX = i * spacing;
                    Gui.drawLine(x + lineX, y, x + lineX, y + height, ColorLib.utilLines(), 1);
                }

                if (currentTimeline != null) {
                    int size = currentTimeline.timepoints.size();
                    for (int i = 0; i < size; i++) {
                        Timeline.Timepoint point = currentTimeline.timepoints.get(i);
                        float pX = x + (width * point.progess);
                        float pY = y + (height * point.yLevel);
                        Gui.drawCircle(pX, pY, 2, ColorLib.red());
                        if (i < size - 1) {
                            Timeline.Timepoint next = currentTimeline.timepoints.get(i + 1);
                            float pnX = x + (width * next.progess);
                            float pnY = y + (height * next.yLevel);
                            Gui.drawLine(pX, pY, pnX, pnY, -1, 2);
                        }
                    }
                    Gui.drawString(currentTimeline.name, 5, height - 5, -1);
                }
                Gui.drawGradientRect(x,y,x+width,y+5,0x80000000,0);
            }
        };
        time.setDimensions(100, 10, parent.width - 120, parent.height - 20);
        time.setColor(ColorLib.getBackground(0.5f));
        time.setRoundness(1);




        canvas.add(time);
    }

    public void initTimeline(File file) {
        if(currentTimeline != null) {
            currentTimeline.saveToFile();
        }
        currentTimeline = Timeline.loadFromFile(file);
    }

    @Override
    public void close() {
        if(currentTimeline != null) {
            currentTimeline.saveToFile();
        }
    }


    @Override
    public void drawScreen(float mouseX, float mouseY) {
        canvas.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        canvas.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
