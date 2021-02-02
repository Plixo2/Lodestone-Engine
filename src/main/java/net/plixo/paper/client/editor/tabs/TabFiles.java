package net.plixo.paper.client.editor.tabs;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.ui.FileIcon;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.visualscript.Canvas;
import net.plixo.paper.client.engine.buildIn.visualscript.Module;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Gui;
import org.apache.commons.io.FilenameUtils;

public class TabFiles extends UITab {

    File[] files = new File[0];

    File home;

    ArrayList<FileIcon> icons = new ArrayList<FileIcon>();

    File lastSelected = null;

    public TabFiles(int id) {
        super(id, "Files");
        TheEditor.files = this;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

     Gui.drawRect(0, 0, parent.width, parent.height, ColorLib.getBackground(0.3f));

        for (FileIcon icon : icons) {
            icon.draw(mouseX, mouseY);
        }
    //  drawOutline();
    }

    @Override
    public void init() {
        home = SaveUtil.getFolderFromName("");
        update();
        super.init();
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if (parent.menu != null) {
            hideMenu();
            return;
        }

        for (FileIcon icon : icons) {

            if (icon.mouseInside(mouseX, mouseY, mouseButton)) {

                if (icon.isFolder) {
                    if (mouseButton == 0) {
                        home = icon.file;
                        update();
                    } else if (mouseButton == 1) {
                        lastSelected = icon.file;
                        showMenu(5, mouseX, mouseY, "Open", "Explorer");
                    }
                } else {
                    String extenstion = FilenameUtils.getExtension(icon.file.getName());

                    int id = -1;

                    if (extenstion.equals(SaveUtil.FileFormat.VisualScript.format)) {
                        id = 0;
                    } else if (extenstion.equals(SaveUtil.FileFormat.Code.format)) {
                        id = 1;
                    } else if (extenstion.equals(SaveUtil.FileFormat.Hud.format)) {
                        id = 2;
                    } else if (extenstion.equals(SaveUtil.FileFormat.Other.format)) {
                        id = 3;
                    }

                    lastSelected = icon.file;
                    showMenu(id, mouseX, mouseY, "Open", "Edit" , "Explorer");
                }

                return;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    void openVs(File file) {
        String name = FilenameUtils.removeExtension(file.getName());
        Module loadedMod = new Module(name, file);
        if (TheEditor.activeMod != null) {
            TheEditor.activeMod.canvas.saveToFile();
        }
        TheEditor.activeMod = loadedMod;
        loadedMod.canvas = new Canvas(loadedMod);
        loadedMod.canvas.init();
    }

    @Override
    public void optionsSelected(int id, int option) {
        if (id >= 0 && lastSelected != null) {
            if (option == 0) {
                if (id == 0) {
                    openVs(lastSelected);
                } else if (id == 5) {
                    home = lastSelected;
                    update();
                }
            } else if (option == 1) {
                try {
                    Desktop.getDesktop().open(lastSelected);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(option == 2) {
                try {
                   // Desktop.getDesktop().open(lastSelected.getParentFile());
                    Runtime.getRuntime().exec("explorer.exe /select," + lastSelected);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        super.optionsSelected(id, option);

    }

    void update() {

        icons.clear();
        files = home.listFiles();
        int y = 0;
        int x = 20;
        int yHeight = 40;
        int xWidth = 40;

        if (home.getParentFile() != null) {
            FileIcon doe = new FileIcon(x, y, xWidth, yHeight, 0xFF2a7abf, 0XFF2a5ebf, home.getParentFile());
            doe.txt = "<";
            icons.add(doe);
            x += 60;
        }

        for (File f : files) {

            FileIcon icon = new FileIcon(x, y, xWidth, yHeight, 0xFF2a7abf, 0XFF2a5ebf, f);
            icon.txt = f.getName();
            icons.add(icon);

            x += xWidth + 20;
            if (xWidth + xWidth >= parent.width) {
                x = 0;
                y += yHeight;
            }
        }

    }

}
