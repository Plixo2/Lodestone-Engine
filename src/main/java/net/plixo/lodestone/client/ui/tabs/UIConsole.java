package net.plixo.lodestone.client.ui.tabs;

import net.plixo.lodestone.client.manager.REditor;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.MouseUtil;

import java.util.concurrent.CopyOnWriteArrayList;


public class UIConsole extends UICanvas {


    public static CopyOnWriteArrayList<String> consoleLines = new CopyOnWriteArrayList<>();

    int zoom = 0;

    public UIConsole() {
        this.setDisplayName("Console");
        REditor.console = this;
    }

    @Override
    public void init() {
       setColor(UColor.getBackground(0.3f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);

        int size = consoleLines.size();

        if (isHovered(mouseX, mouseY)) {
            int dir = Math.round(Math.signum(MouseUtil.getDWheel()));
            zoom += dir;

        }

        if(dragged) {
            float rel = (mouseY - 10) / (height - 20);
            rel = 1-rel;
            zoom = (int) (size * rel);
        }

        int displayHeight = 10;
        zoom = UMath.clamp(zoom, size - displayHeight, 0);

        int end = size - zoom;
        int end2 = size - zoom - displayHeight;

        float txtHeight = (height / displayHeight);
        if (size > 1) {
            for (int i = end2; i < end; i++) {
                float relative = (i - end2) + 0.5f;

                UGui.drawString(consoleLines.get(UMath.clamp(i,size-1,0)), 10, (txtHeight * relative), UColor.getTextColor());
            }

            UGui.drawRect(3, 0, 4, height, -1);
            float progress = 1-((float) (zoom) / (size));
            float hH = 10;
            float yS = hH + progress * (height - hH * 2);
            UGui.drawRect(2, yS - hH-1, 5, yS + hH+1, UColor.getMainColor());
        }

    }
    boolean dragged = false;

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if(isHovered(mouseX,mouseY)) {
            if(mouseX < 10) {
                dragged = true;
                System.out.println("dragh");
            }
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragged= false;
    }

    @Override
    public void onTick() {

        if (consoleLines.size() > 400) {
            for (int i = 0; i < consoleLines.size() - 400; i++) {
                consoleLines.remove(i);
            }
        }
        super.onTick();
    }
}
