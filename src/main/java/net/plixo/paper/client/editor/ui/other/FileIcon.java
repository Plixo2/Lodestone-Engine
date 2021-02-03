package net.plixo.paper.client.editor.ui.other;

import java.io.File;

import net.plixo.paper.client.editor.visualscript.Rect;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FilenameUtils;

public class FileIcon extends Rect {

    public File file;
    public boolean isFolder;

    public FileIcon(float x, float y, float width, float height, int color, int hoverColor, File file) {
        super(x, y, width, height, color, hoverColor);
        this.isFolder = file.isDirectory();
        this.file = file;
    }

    @Override
    public void draw(float mouseX, float mouseY) {

        boolean hover = mouseInside(mouseX, mouseY, -1);
        drawIcon(hover);
        drawMuliLineText(txt, width, hover);
    }

    void drawIcon(boolean hover) {

        int second = ColorLib.getMainColor();
        int first = ColorLib.getDarker(ColorLib.getMainColor());

        float y = this.y;

        if (hover) {
            y -= 1;
            first = ColorLib.getDarker(first);
            second = ColorLib.getDarker(second);
        }

        if (isFolder) {
            Gui.drawRoundetRect(x + 5, y + 10, x + width - 5, y + height - 5, 4, first);
            Gui.drawRoundetRect(x + 5, y + 15, x + width - 5, y + height - 5, 4, second);

            Gui.drawRoundetRect(x + (width / 2) - 5, y + 12, x + width - 5, y + 20, 4, second);
            Gui.drawRoundetRect(x + 5, y + 7, x + width / 2, y + 15, 4, first);
        } else {

            Gui.drawLine(x + 10, y + 7, x + 10, y + height - 5, first, 2);
            Gui.drawLine(x + 10, y + 7, x + width / 2, y + 7, first, 2);
            Gui.drawLine(x + width - 10, y + (height / 2) - 4, x + width / 2, y + 7, first, 2);

            Gui.drawLine(x + width - 10, y + (height / 2) - 4, x + width / 2, y + (height / 2) - 4, first, 2);

            Gui.drawLine(x + width / 2, y + 7, x + width / 2, y + (height / 2) - 4, first, 2);

            Gui.drawLine(x + width - 10, y + (height / 2) - 4, x + width - 10, y + height - 5, first, 2);
            Gui.drawLine(x + 10, y + height - 5, x + width - 10, y + height - 5, first, 2);

            String extenstion = FilenameUtils.getExtension(txt);
            if (extenstion.equals(SaveUtil.FileFormat.VisualScript.format)) {

                long time2 = System.currentTimeMillis() / 10;
                time2 = time2 % 360;

                double XDiff = Math.sin(Math.toRadians(time2)) * 2;

                double dX = Math.sin(Math.toRadians(time2 + 45)) * 1;
                double dY = 0;

                Gui.drawLine(x + (width / 2) - XDiff, y + (height / 2) + 8, x + (width / 2) + XDiff,
                        y + (height / 2) + 2, 0xFF8341FF, 3);

                Gui.drawRoundetRect(x + (width / 2) - 7 + dX, y + (height / 2) - 3 + dY, x + (width / 2) + 7 + dX,
                        y + (height / 2) + 4 + dY, 3f, 0xFF101010);

                Gui.drawRoundetRect(x + (width / 2) - 7, y + (height / 2) + 7, x + (width / 2) + 7,
                        y + (height / 2) + 14, 3f, 0xFF101010);

            } else if (extenstion.equals(SaveUtil.FileFormat.Code.format)) {
                if (System.currentTimeMillis() % 1200 < 600) {
                    Gui.drawCenteredString("<>", x + width / 2, y + height / 2 + 5, -1);
                } else {
                    Gui.drawCenteredString("< >", x + width / 2, y + height / 2 + 5, -1);
                }
            } else if (extenstion.equals(SaveUtil.FileFormat.Hud.format)) {

                long time2 = System.currentTimeMillis() / 100;
                time2 = time2 % 180;
                Gui.drawCircle(x + width / 2, y + height / 2 + 5, 7, -1, (int) time2 - 6, (int) time2 + 6);

                Gui.drawCircle(x + width / 2, y + height / 2 + 5, 5, -1);
                Gui.drawCircle(x + width / 2, y + height / 2 + 5, 4, second);

                long time = System.currentTimeMillis() / 10;
                time = time % 180;

                Gui.drawCircle(x + width / 2, y + height / 2 + 5, 4, -1, 0, (int) time);

            } else if (extenstion.equals(SaveUtil.FileFormat.Other.format)) {

                long time2 = System.currentTimeMillis() / 10;
                time2 = time2 % 360;

                double XDiff = Math.sin(Math.toRadians(time2)) * 2;
                double XDiff2 = Math.sin(Math.toRadians(time2 + 180)) * 2;

                Gui.drawCenteredString("{  ", x + width / 2 + XDiff, y + height / 2 + 2, -1);
                Gui.drawCenteredString("  }", x + width / 2 + XDiff2, y + height / 2 + 10, -1);
            }

        }

    }

    void drawMuliLineText(String toDraw, float maxWidth, boolean hover) {




        String name = FilenameUtils.removeExtension(toDraw);
        String in = name;
        String extension = FilenameUtils.getExtension(toDraw);

        name = Util.displayTrim(name, maxWidth);

        if(!name.equals(in)) {
            name += "...";
        }
        Gui.drawCenteredStringwithShadow(name, (x + width / 2), (this.y + this.height),
                -1);
        if(!extension.isEmpty())
        Gui.drawCenteredStringwithShadow("."+extension, (x + width / 2), (this.y + this.height + 10),
                -1);


    }
}
