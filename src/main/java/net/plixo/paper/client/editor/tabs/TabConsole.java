package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TabConsole extends UITab {

    public static class ConsoleLine {
        public String line;
        long timeMS;

        public ConsoleLine(String line) {
            this.line = line;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            this.line = formatter.format(calendar.getTime()) + (":" + System.currentTimeMillis() % 1000) + " " + line;
            timeMS = System.currentTimeMillis();
        }
    }

    public static ArrayList<ConsoleLine> consoleLines = new ArrayList<ConsoleLine>();

    float zoom = 1;

    public TabConsole(int id) {
        super(id, "Console");
        TheEditor.console = this;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRect(0, 0, parent.width, parent.height, ColorLib.getBackground(0.3f));
        float y = 10;
        int size = consoleLines.size();
        int aw = size * 15;
        if (aw > parent.height) {
            y = 10 + (-(aw - (parent.height - 5))) * zoom;
        }
        int index = 0;

        for (int i = 0; i < size; i++) {
            index = i;
            if (i < consoleLines.size()) {
                ConsoleLine line = consoleLines.get(i);
                if (line != null && line.line != null) {
                    Gui.drawString("" + line.line, 8, y, -1);
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
        ArrayList<ConsoleLine> toRemove = new ArrayList<>();

        if (consoleLines.size() > 400) {
            for (int i = 0; i < consoleLines.size() - 500; i++) {
                toRemove.add(consoleLines.get(i));
            }
        }

        for (ConsoleLine line : toRemove) {

            consoleLines.remove(line);
        }
    }
}
