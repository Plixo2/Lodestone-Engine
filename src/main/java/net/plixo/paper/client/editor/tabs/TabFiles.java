package net.plixo.paper.client.editor.tabs;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.editor.ui.accept.UIAccept;
import net.plixo.paper.client.editor.ui.accept.UITextInput;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.editor.ui.other.UIFileIcon;
import net.plixo.paper.client.editor.visualscript.Canvas;
import net.plixo.paper.client.engine.components.visualscript.Module;
import net.plixo.paper.client.util.*;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.glfw.GLFW;

public class TabFiles extends UITab {

    File[] files = new File[0];
    static File home;

    public TabFiles(int id) {
        super(id, "Files");
        TheEditor.files = this;
    }


    @Override
    public void init() {


        canvas = new UICanvas(0);
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.3f));

        if (home == null) {home = SaveUtil.getFolderFromName("");}
        update();
        super.init();
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if (parent.menu != null) {
            hideMenu();
            return;
        }

        if (parent.isMouseInside(mouseX, mouseY) && mouseButton == 1) {
            showMenu(0, mouseX, mouseY,
                    newRunnable(SaveUtil.FileFormat.Code),
                    newRunnable(SaveUtil.FileFormat.VisualScript),
                    newRunnable(SaveUtil.FileFormat.Hud),
                    newRunnable(SaveUtil.FileFormat.Other),
                    newRunnable(SaveUtil.FileFormat.Model),
                    newRunnable(SaveUtil.FileFormat.Timeline));
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public OptionMenu.TxtRun newRunnable(SaveUtil.FileFormat format) {
        return new OptionMenu.TxtRun("New ." + format.format) {
            @Override
            public void run() {
                UITextInput input = new UITextInput((txt) -> {
                    File f = new File(home.getAbsolutePath() + "\\" + txt + "." + format.format);
                    if (!f.exists()) {
                        SaveUtil.makeFile(f);
                    } else {
                        Util.print("File already exists");
                    }
                }, (txt) -> {
                }, format.format);
                mc.displayGuiScreen(input);
            }
        };
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        if(key == GLFW.GLFW_KEY_F5) {
            update();
        }

        super.keyPressed(key, scanCode, action);
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


    void update() {
        if (home == null) {home = SaveUtil.getFolderFromName("");}
        canvas.clear();
        files = home.listFiles();

        Arrays.sort(files , Comparator.comparingInt(file -> file.isFile() ? 1 : 0));
        int y = 0;
        int x = 10;
        int yHeight = 50;
        int xWidth = 40;

        if (home.getParentFile() != null) {
            UIFileIcon icon = new UIFileIcon(home.getParentFile()) {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                    if(hovered(mouseX,mouseY)) {
                        if (mouseButton == 0) {
                            home = file;
                            update();
                        }
                    }
                    super.mouseClicked(mouseX, mouseY, mouseButton);
                }
            };
            icon.setDimensions(x,y,xWidth,yHeight);
            icon.getList().get(1).setDisplayName("<");
            icon.setRoundness(0);
            icon.setColor(0);
            canvas.add(icon);
            x += xWidth+10;
        }

        for (File f : files) {

            UIFileIcon icon = new UIFileIcon(f) {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                    if(!hovered(mouseX,mouseY) || !file.exists()) {
                        return;
                    }
                    if (isFolder()) {
                        if (mouseButton == 0) {
                            home = file;
                            update();
                        } else if (mouseButton == 1) {
                            OptionMenu.TxtRun open = new OptionMenu.TxtRun("Open") {
                                @Override
                                public void run() {
                                    home = file;
                                    update();
                                }
                            };
                            OptionMenu.TxtRun explorer = new OptionMenu.TxtRun("Explorer") {
                                @Override
                                public void run() {
                                    try {
                                        Desktop.getDesktop().open(file);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            OptionMenu.TxtRun delete = new OptionMenu.TxtRun("Delete") {
                                @Override
                                public void run() {
                                    mc.displayGuiScreen(new UIAccept(() -> {
                                        file.delete(); //update();
                                    }, () -> {
                                    }, "Sure?"));
                                }
                            };
                            OptionMenu.TxtRun rename = new OptionMenu.TxtRun("Rename") {
                                @Override
                                public void run() {
                                    UITextInput input = new UITextInput((txt) -> {
                                        if(!txt.isEmpty()) {
                                            String fileFormat = FilenameUtils.getExtension(file.getName());
                                            File oldFile = new File(file.getParent()+"/"+txt+"."+fileFormat);
                                            if(!oldFile.exists()) {
                                                boolean status = file.renameTo(oldFile);
                                                if(!status) {
                                                    Util.print("Failed to rename");
                                                }
                                            } else {
                                                Util.print("File already exists");
                                            }
                                        }
                                    }, (txt) -> {
                                    }, FilenameUtils.removeExtension(file.getName()));
                                    mc.displayGuiScreen(input);
                                }
                            };
                            showMenu(0, mouseX, mouseY, open, explorer , delete , rename);
                        }
                    } else {
                        String extenstion = FilenameUtils.getExtension(file.getName());

                        OptionMenu.TxtRun open = new OptionMenu.TxtRun("Open") {
                            @Override
                            public void run() {
                            }
                        };

                        if (extenstion.equals(SaveUtil.FileFormat.VisualScript.format)) {
                            open = new OptionMenu.TxtRun("Open") {
                                @Override
                                public void run() {
                                    openVs(file);
                                }
                            };
                        } else if (extenstion.equals(SaveUtil.FileFormat.Model.format)) {
                            open = new OptionMenu.TxtRun("View") {
                                @Override
                                public void run() {
                                    TheEditor.modelViewer.initViewer(file);
                                }
                            };
                        } else if (extenstion.equals(SaveUtil.FileFormat.Timeline.format)) {
                            open = new OptionMenu.TxtRun("Timeline") {
                                @Override
                                public void run() {
                                    TheEditor.timeline.initTimeline(file);
                                }
                            };
                        }
                        OptionMenu.TxtRun edit = new OptionMenu.TxtRun("Edit") {
                            @Override
                            public void run() {
                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        OptionMenu.TxtRun explorer = new OptionMenu.TxtRun("Explorer") {
                            @Override
                            public void run() {
                                try {
                                    Runtime.getRuntime().exec("explorer.exe /select," + file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        OptionMenu.TxtRun delete = new OptionMenu.TxtRun("Delete") {
                            @Override
                            public void run() {
                                mc.displayGuiScreen(new UIAccept(() -> {
                                    file.delete(); //update();
                                }, () -> {
                                }, "Sure?"));
                            }
                        };
                        OptionMenu.TxtRun rename = new OptionMenu.TxtRun("Rename") {
                            @Override
                            public void run() {
                                UITextInput input = new UITextInput((txt) -> {
                                    if(!txt.isEmpty()) {
                                        String fileFormat = FilenameUtils.getExtension(file.getName());
                                        File oldFile = new File(file.getParent()+"/"+txt+"."+fileFormat);
                                        if(!oldFile.exists()) {
                                            boolean status = file.renameTo(oldFile);
                                            if(!status) {
                                                Util.print("Failed to rename");
                                            }
                                        } else {
                                            Util.print("File already exists");
                                        }
                                    }
                                }, (txt) -> {
                                }, FilenameUtils.removeExtension(file.getName()));
                                mc.displayGuiScreen(input);
                            }
                        };

                        //lastSelected = icon.file;
                        showMenu(0, mouseX, mouseY, open, edit, explorer, delete , rename);
                    }


                    super.mouseClicked(mouseX, mouseY, mouseButton);
                }
            };
            icon.setDimensions(x,y,xWidth,yHeight);
            icon.setRoundness(0);
            icon.setColor(0);
            canvas.add(icon);

            x += xWidth + 10;
            if (xWidth + xWidth >= parent.width) {
                x = 0;
                y += yHeight;
            }
        }

    }

}
