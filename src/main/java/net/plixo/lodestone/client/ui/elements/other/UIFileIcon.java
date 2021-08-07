package net.plixo.lodestone.client.ui.elements.other;

import net.minecraft.client.Minecraft;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.screens.ScreenTextInput;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.SaveUtil;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;

import java.io.File;

/**
 * for displaying Files as Icon in the UI
 */
public class UIFileIcon extends UICanvas {

    File file;
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

                super.drawScreen(mouseX, mouseY);
                int second = UColor.getMainColor();
                int first = UColor.getDarker(UColor.getMainColor());
                boolean hover = isHovered(mouseX, mouseY);
                float y = this.y;
                if (hover) {
                    y -= 1;
                    first = UColor.getDarker(first);
                    second = UColor.getDarker(second);
                }

                if (isFolder()) {
                    UGui.drawRoundedRect(x + 5, y + 10, x + width - 5, y + height - 5, 4, first);
                    UGui.drawRoundedRect(x + 5, y + 15, x + width - 5, y + height - 5, 4, second);
                    UGui.drawRoundedRect(x + (width / 2) - 5, y + 12, x + width - 5, y + 20, 4, second);
                    UGui.drawRoundedRect(x + 5, y + 7, x + width / 2, y + 15, 4, first);
                } else {
                    UGui.drawLine(x + 10, y + 7, x + 10, y + height - 5, first, 2);
                    UGui.drawLine(x + 10, y + 7, x + width / 2, y + 7, first, 2);
                    UGui.drawLine(x + width - 10, y + (height / 2) - 4, x + width / 2, y + 7, first, 2);
                    UGui.drawLine(x + width - 10, y + (height / 2) - 4, x + width / 2, y + (height / 2) - 4, first, 2);
                    UGui.drawLine(x + width / 2, y + 7, x + width / 2, y + (height / 2) - 4, first, 2);
                    UGui.drawLine(x + width - 10, y + (height / 2) - 4, x + width - 10, y + height - 5, first, 2);
                    UGui.drawLine(x + 10, y + height - 5, x + width - 10, y + height - 5, first, 2);
                    if (extension.equals(SaveUtil.FileFormat.VISUAL_SCRIPT.format)) {
                        long time2 = (System.currentTimeMillis() / 10) % 360;
                        float XDiff = (float) (Math.sin(Math.toRadians(time2)) * 2);
                        float dX = (float) (Math.sin(Math.toRadians(time2 + 45)) * 1);
                        float dY = 0;
                        UGui.drawLine(x + (width / 2) - XDiff, y + (height / 2) + 8, x + (width / 2) + XDiff,
                                y + (height / 2) + 2, 0xFF8341FF, 3);
                        UGui.drawRoundedRect(x + (width / 2) - 7 + dX, y + (height / 2) - 3 + dY, x + (width / 2) + 7 + dX,
                                y + (height / 2) + 4 + dY, 3f, 0xFF101010);
                        UGui.drawRoundedRect(x + (width / 2) - 7, y + (height / 2) + 7, x + (width / 2) + 7,
                                y + (height / 2) + 14, 3f, 0xFF101010);
                    } else if (extension.equals(SaveUtil.FileFormat.CODE.format)) {
                        long time = System.currentTimeMillis() % 1800;
                        String code = "</>";
                        UGui.drawCenteredString(code.substring(0, 1 + (int) (time / 600)), x + width / 2, y + height / 2 + 5, UColor.getTextColor());
                    } else if (extension.equals(SaveUtil.FileFormat.HUD.format)) {
                        long time2 = (System.currentTimeMillis() / 100) % 180;
                        UGui.drawCircle(x + width / 2, y + height / 2 + 5, 7, -1, (int) time2 - 6, (int) time2 + 6);
                        UGui.drawCircle(x + width / 2, y + height / 2 + 5, 5, -1);
                        UGui.drawCircle(x + width / 2, y + height / 2 + 5, 4, second);
                        long time = (System.currentTimeMillis() / 10) % 180;
                        UGui.drawCircle(x + width / 2, y + height / 2 + 5, 4, -1, 0, (int) time);
                    } else if (extension.equals(SaveUtil.FileFormat.MODEL.format)) {
                        GL11.glPushMatrix();
                        GL11.glTranslated(x + width / 2, y + height / 2 + 5, 0);
                        long rot = System.currentTimeMillis() / 10;
                        rot = rot % 360;
                        UGui.drawRect(-5, -5, 5, 5, java.awt.Color.HSBtoRGB(rot / 360f, 1, 1));
                        GL11.glRotated(rot, Math.sin(Math.toRadians(rot)), 0, 1);
                        UGui.drawRect(-5, -5, 5, 5, -1);
                        GL11.glPopMatrix();

                    } else if (extension.equals(SaveUtil.FileFormat.OTHER.format)) {
                        long time2 = System.currentTimeMillis() / 10;
                        time2 = time2 % 360;
                        float XDiff = (float) (Math.sin(Math.toRadians(time2)) * 2);
                        float XDiff2 = (float) (Math.sin(Math.toRadians(time2 + 180)) * 2);
                        UGui.drawCenteredString("{  ", x + width / 2 + XDiff, y + height / 2 + 2, UColor.getTextColor());
                        UGui.drawCenteredString("  }", x + width / 2 + XDiff2, y + height / 2 + 10, UColor.getTextColor());
                    } else if (extension.equals(SaveUtil.FileFormat.META.format)) {
                        long time = System.currentTimeMillis() % 1800;
                        UGui.drawCenteredString("?", x + width / 2, y + height / 2 + 5, UColor.getTextColor());
                    }
                }

            }
        };
        button.setDimensions(0, 0, width, height - 10);
        button.setColor(UColor.getBackground(0.3f));

        UIButton name = new UIButton();

        if (!isFolder()) {
            setCursorObject(() -> file);
            name.setHoverName("." + extension);
            name.setAction(() -> {
                ScreenTextInput input = new ScreenTextInput((txt) -> {
                    if (!txt.isEmpty()) {
                        String fileFormat = FilenameUtils.getExtension(file.getName());
                        File oldFile = new File(file.getParent() + "/" + txt + "." + fileFormat);
                        if (!oldFile.exists()) {
                            boolean status = file.renameTo(oldFile);
                            if (!status) {
                                UMath.print("Failed to rename");
                            }
                        } else {
                            UMath.print("File already exists");
                        }
                    }
                }, (txt) -> {
                }, FilenameUtils.removeExtension(file.getName()));
                Minecraft.getInstance().displayGuiScreen(input);
            });
        }
        name.setDimensions(0, height - 10, width, 10);
        name.setDisplayName(getDisplayName());
        name.setColor(0);


        clear();
        add(button);
        add(name);
    }


    protected boolean isFolder() {
        return !file.isFile();
    }


    public void setDisplayString() {
        String fullName = file.getName();

        extension = FilenameUtils.getExtension(fullName);

        String filename = FilenameUtils.removeExtension(fullName);
        setDisplayName(UMath.displayTrim(filename, this.width - 10));

        if (!getDisplayName().equals(filename)) {
            setDisplayName(getDisplayName() + "...");
        }
    }

    public File getFile() {
        return file;
    }
}


