package net.plixo.paper.client.ui.elements;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.ui.GUI.GUITextInput;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.File;

/**
 * for displaying Files as Icon in the UI
 */
public class UIFileIcon extends UICanvas {

    protected File file;
    String extension;

    public UIFileIcon(File file) {
        this.file = file;
    }

    /**
     * used to separate Text and Icon
     */
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        setDisplayString();
        UIButton button = new UIButton() {

            /**
             * draw the Icon
             */
            @Override
            public void drawScreen(float mouseX, float mouseY) {

                super.drawScreen(mouseX,mouseY);
                int second = ColorLib.getMainColor();
                int first = ColorLib.getDarker(ColorLib.getMainColor());
                boolean hover = hovered(mouseX, mouseY);
                float y = this.y;
                if (hover) {
                    y -= 1;
                    first = ColorLib.getDarker(first);
                    second = ColorLib.getDarker(second);
                }

                if (isFolder()) {
                    Gui.drawRoundedRect(x + 5, y + 10, x + width - 5, y + height - 5, 4, first);
                    Gui.drawRoundedRect(x + 5, y + 15, x + width - 5, y + height - 5, 4, second);
                    Gui.drawRoundedRect(x + (width / 2) - 5, y + 12, x + width - 5, y + 20, 4, second);
                    Gui.drawRoundedRect(x + 5, y + 7, x + width / 2, y + 15, 4, first);
                } else {
                    Gui.drawLine(x + 10, y + 7, x + 10, y + height - 5, first, 2);
                    Gui.drawLine(x + 10, y + 7, x + width / 2, y + 7, first, 2);
                    Gui.drawLine(x + width - 10, y + (height / 2) - 4, x + width / 2, y + 7, first, 2);
                    Gui.drawLine(x + width - 10, y + (height / 2) - 4, x + width / 2, y + (height / 2) - 4, first, 2);
                    Gui.drawLine(x + width / 2, y + 7, x + width / 2, y + (height / 2) - 4, first, 2);
                    Gui.drawLine(x + width - 10, y + (height / 2) - 4, x + width - 10, y + height - 5, first, 2);
                    Gui.drawLine(x + 10, y + height - 5, x + width - 10, y + height - 5, first, 2);
                    if (extension.equals(SaveUtil.FileFormat.VisualScript.format)) {
                        long time2 = (System.currentTimeMillis() / 10)  % 360;
                        double XDiff = Math.sin(Math.toRadians(time2)) * 2;
                        double dX = Math.sin(Math.toRadians(time2 + 45)) * 1;
                        double dY = 0;
                        Gui.drawLine(x + (width / 2) - XDiff, y + (height / 2) + 8, x + (width / 2) + XDiff,
                                y + (height / 2) + 2, 0xFF8341FF, 3);
                        Gui.drawRoundedRect(x + (width / 2) - 7 + dX, y + (height / 2) - 3 + dY, x + (width / 2) + 7 + dX,
                                y + (height / 2) + 4 + dY, 3f, 0xFF101010);
                        Gui.drawRoundedRect(x + (width / 2) - 7, y + (height / 2) + 7, x + (width / 2) + 7,
                                y + (height / 2) + 14, 3f, 0xFF101010);
                    } else if (extension.equals(SaveUtil.FileFormat.Code.format)) {
                        if (System.currentTimeMillis() % 1200 < 600) {
                            Gui.drawCenteredString("<>", x + width / 2, y + height / 2 + 5, -1);
                        } else {
                            Gui.drawCenteredString("< >", x + width / 2, y + height / 2 + 5, -1);
                        }
                    }
                    else if (extension.equals(SaveUtil.FileFormat.Hud.format)) {
                        long time2 = (System.currentTimeMillis() / 100) % 180;
                        Gui.drawCircle(x + width / 2, y + height / 2 + 5, 7, -1, (int) time2 - 6, (int) time2 + 6);
                        Gui.drawCircle(x + width / 2, y + height / 2 + 5, 5, -1);
                        Gui.drawCircle(x + width / 2, y + height / 2 + 5, 4, second);
                        long time = (System.currentTimeMillis() / 10) % 180;
                        Gui.drawCircle(x + width / 2, y + height / 2 + 5, 4, -1, 0, (int) time);
                    } else if (extension.equals(SaveUtil.FileFormat.Model.format)) {
                        GL11.glPushMatrix();
                        GL11.glTranslated(x + width / 2, y + height / 2 + 5, 0);
                        long rot = System.currentTimeMillis() / 10;
                        rot = rot % 360;
                        Gui.drawRect(-5, -5, 5, 5, Color.HSBtoRGB(rot / 360f, 1, 1));
                        GL11.glRotated(rot, Math.sin(Math.toRadians(rot)), 0, 1);
                        Gui.drawRect(-5, -5, 5, 5, -1);
                        GL11.glPopMatrix();

                    } else if (extension.equals(SaveUtil.FileFormat.Other.format)) {
                        long time2 = System.currentTimeMillis() / 10;
                        time2 = time2 % 360;
                        double XDiff = Math.sin(Math.toRadians(time2)) * 2;
                        double XDiff2 = Math.sin(Math.toRadians(time2 + 180)) * 2;
                        Gui.drawCenteredString("{  ", x + width / 2 + XDiff, y + height / 2 + 2, -1);
                        Gui.drawCenteredString("  }", x + width / 2 + XDiff2, y + height / 2 + 10, -1);
                    }
                }

            }
        };
        button.setDimensions(0, 0, width, height - 10);
        button.setColor(ColorLib.getBackground(0.3f));


        /**
         * Name button with hover name
         */
        UIButton name = new UIButton() {
            /**
             * existence check or else color red
             */
            @Override
            public void onTick() {
                if (!file.exists()) {
                    textColor = 0xFFFF4455;
                } else {
                    textColor = -1;
                }
            }

            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

                if (!isFolder() && hovered(mouseX,mouseY) && mouseButton == 0) {
                    GUITextInput input = new GUITextInput((txt) -> {
                        if (!txt.isEmpty()) {
                            String fileFormat = FilenameUtils.getExtension(file.getName());
                            File oldFile = new File(file.getParent() + "/" + txt + "." + fileFormat);
                            if (!oldFile.exists()) {
                                boolean status = file.renameTo(oldFile);
                                if (!status) {
                                    Util.print("Failed to rename");
                                }
                            } else {
                                Util.print("File already exists");
                            }
                        }
                    }, (txt) -> {
                    }, FilenameUtils.removeExtension(file.getName()));
                    Minecraft.getInstance().displayGuiScreen(input);
                }
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        };
        name.setDimensions(0, height - 10, width, 10);
        name.setDisplayName(displayName);
        name.setColor(0);


        if (!isFolder())
            name.setHoverName("."+extension);


        add(button);
        add(name);
    }

    /**
     * is the File a real File or a Folder
     */
    protected boolean isFolder() {
        return !file.isFile();
    }

    /**
     * calculate Name
     */
    public void setDisplayString() {
        String fullName = file.getName();

        extension = FilenameUtils.getExtension(fullName);

        String filename = FilenameUtils.removeExtension(fullName);
        displayName = Util.displayTrim(filename, this.width - 10);

        if (!displayName.equals(filename)) {
            displayName += "...";
        }
    }

}


