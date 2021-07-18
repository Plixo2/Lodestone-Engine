package net.plixo.paper.client.ui.tabs;

import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import net.plixo.paper.client.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class UIConsole extends UICanvas {

    public static class ConsoleLine {
        public String line;
        public String time;

        public ConsoleLine(String line) {
            this.line = line;
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            time = formatter.format(calendar.getTime());
        }

        public ConsoleLine() {

        }
    }

    public static CopyOnWriteArrayList<ConsoleLine> consoleLines = new CopyOnWriteArrayList<>();

    int zoom = 0;

    public UIConsole() {
        this.displayName = "Console";
        EditorManager.console = this;
    }

    @Override
    public void init() {
        this.color = ColorLib.getBackground(0.3f);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);

        if (hovered(mouseX, mouseY)) {
            int dir = Math.round(Math.signum(MouseUtil.getDWheel()));
            zoom += dir;
        }
        int size = consoleLines.size();
        zoom = Util.clamp(zoom, size - 1,0);

        int amount = (int) (height/20)+1;
        int location = (size-1)-zoom;

        List<ConsoleLine> consoleLines = UIConsole.consoleLines.subList(Math.max(location-(amount*2),0),Math.max(location-amount,0));
        for (int i = 0; i < consoleLines.size(); i++) {
            ConsoleLine consoleLine = consoleLines.get(i);
            Gui.drawString(consoleLine.time + "  "+consoleLine.line,5 , i * 20 + 10,-1);
        }

        if (size > 0) {
            float progress = 1- ((float)zoom/ size);
            float hH = 10;
            float yS = hH + progress * (height - hH * 2);
            Gui.drawRect(2, yS - hH, 5, yS + hH, ColorLib.getMainColor());
        }

        Gui.drawRect(3, 0, 4, height, -1);


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
