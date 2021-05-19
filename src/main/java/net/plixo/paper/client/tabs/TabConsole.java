package net.plixo.paper.client.tabs;

import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;


public class TabConsole extends UITab {

    public static class ConsoleLine {
        public String line;
        public String time;

        public ConsoleLine(String line) {
            this.line = line;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            this.line =  line;
            time = formatter.format(calendar.getTime());
        }
    }

    public static CopyOnWriteArrayList<ConsoleLine> consoleLines = new CopyOnWriteArrayList<>();

    float zoom = 1;

    public TabConsole(int id) {
        super(id, "Console");
        EditorManager.console = this;
    }

    @Override
    public void init() {
        canvas = new UICanvas();
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.3f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        super.drawScreen(mouseX,mouseY);
        float y = 10;
        int size = consoleLines.size();
        int aw = size * 15;
        if (aw > parent.height) {
            y = 10 + (-(aw - (parent.height - 5))) * zoom;
        }

        for (int i = 0; i < size; i++) {
            if (i < consoleLines.size()) {
                ConsoleLine line = consoleLines.get(i);
                if (line != null && line.line != null) {
                    Gui.drawString("" + line.line, 8, y, -1);
                    float strWidth = Gui.getStringWidth(line.time);
                    Gui.drawString(line.time, parent.width-strWidth-5 , y , ColorLib.yellow());
                }
            }

            y += 15;
        }

        if (parent.isMouseInside(mouseX, mouseY)) {
            float dir = Math.signum(MouseUtil.getDWheel());
            if (dir != 0) {
                zoom -= (1f * dir / size);
                zoom = Math.max(Math.min(zoom, 1), 0);
            }
        }

        float hH = 10;
        float yS = hH + zoom * (parent.height - hH * 2);

        Gui.drawRect(3, 0, 4, parent.height, -1);

        Gui.drawRect(2, yS - hH, 5, yS + hH, ColorLib.getMainColor());

    //    drawOutline();
    }

    @Override
    public void onTick() {

        if (consoleLines.size() > 400) {
            for (int i = 0; i < consoleLines.size() - 500; i++) {
                consoleLines.remove(i);
            }
        }
        super.onTick();
    }
}
