package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.avs.newVersion.FunctionManager;
import net.plixo.paper.client.avs.newVersion.VisualScript;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.ui.GUI.GUIAccept;
import net.plixo.paper.client.ui.GUI.GUITextInput;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.ui.elements.UIFileIcon;
import net.plixo.paper.client.ui.other.OptionMenu;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Options;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TabFiles extends UITab {

    File[] files = new File[0];
    static File home;

    public TabFiles(int id) {
        super(id, "Files");
        EditorManager.files = this;
    }


    @Override
    public void init() {

        ArrayList<File> allFiles = new ArrayList<>();
        Util.findFiles(SaveUtil.getFolderFromName("").getAbsolutePath(), allFiles);
        for (File file : allFiles) {
            filesChanged.put(file,file.lastModified());
        }
        canvas = new UICanvas(0);
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.3f));

        if (home == null) {
            home = SaveUtil.getFolderFromName("");
        }
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
                    newRunnable(SaveUtil.FileFormat.Model));
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public OptionMenu.TxtRun newRunnable(SaveUtil.FileFormat format) {
        return new OptionMenu.TxtRun("New ." + format.format) {
            @Override
            public void run() {
                GUITextInput input = new GUITextInput((txt) -> {
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
        if (key == GLFW.GLFW_KEY_F5) {
            update();
        }

        super.keyPressed(key, scanCode, action);
    }

    void update() {
        if (home == null) {
            home = SaveUtil.getFolderFromName("");
        }

        canvas.clear();
        files = home.listFiles();
        if (!Options.showMetadata) {
            files = Arrays.stream(files).filter(file -> {
                String name = FilenameUtils.getExtension(file.getAbsolutePath());
                return !name.equals(SaveUtil.FileFormat.Meta.format);
            }).toArray(File[]::new);
        }
        Arrays.sort(files, Comparator.comparingInt(file -> file.isFile() ? 1 : 0));
        int y = 0;
        int x = 10;
        int yHeight = 50;
        int xWidth = 40;

        if (home.getParentFile() != null) {
            UIFileIcon icon = new UIFileIcon(home.getParentFile()) {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                    if (hovered(mouseX, mouseY)) {
                        if (mouseButton == 0) {
                            home = file;
                            update();
                        }
                    }
                    super.mouseClicked(mouseX, mouseY, mouseButton);
                }
            };
            icon.setDimensions(x, y, xWidth, yHeight);
            icon.getList().get(1).setDisplayName("<");
            icon.setRoundness(0);
            icon.setColor(0);
            canvas.add(icon);
            x += xWidth + 10;
        }

        for (File f : files) {
            UIFileIcon icon = new UIFileIcon(f) {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                    if (!hovered(mouseX, mouseY) || !file.exists()) {
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
                                    mc.displayGuiScreen(new GUIAccept(() -> {
                                        file.delete(); //update();
                                    }, () -> {
                                    }, "Sure?"));
                                }
                            };
                            OptionMenu.TxtRun rename = new OptionMenu.TxtRun("Rename") {
                                @Override
                                public void run() {
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
                                    mc.displayGuiScreen(input);
                                }
                            };
                            showMenu(0, mouseX, mouseY, open, explorer, delete, rename);
                        }
                    } else {

                        String extenstion = FilenameUtils.getExtension(file.getName());

                        OptionMenu.TxtRun open = new OptionMenu.TxtRun("View") {
                            @Override
                            public void run() {
                            }
                        };

                        if (extenstion.equals(SaveUtil.FileFormat.VisualScript.format)) {
                            open = new OptionMenu.TxtRun("View") {
                                @Override
                                public void run() {
                                     VisualScript script = FunctionManager.loadFromFile(file);
                                     EditorManager.viewport.load(script);
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
                                mc.displayGuiScreen(new GUIAccept(() -> {
                                    file.delete(); //update();
                                }, () -> {
                                }, "Sure?"));
                            }
                        };
                        OptionMenu.TxtRun rename = new OptionMenu.TxtRun("Rename") {
                            @Override
                            public void run() {
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
                                mc.displayGuiScreen(input);
                            }
                        };
                        try {
                                EditorManager.inspector.initInspector(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        showMenu(0, mouseX, mouseY, open, edit, explorer, delete, rename);

                    }


                    super.mouseClicked(mouseX, mouseY, mouseButton);
                }
            };
            icon.setDimensions(x, y, xWidth, yHeight);
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

    Map<File ,Long> filesChanged = new HashMap<>();
    int ticks = 0;

    @Override
    public void onTick() {
        ticks += 1;
        if(!Lodestone.lodestoneEngine.isRunning) {
            if(ticks % 80 == 0) {
                boolean shouldReload = false;
                ArrayList<File> allFiles = new ArrayList<>();
                Util.findFiles(SaveUtil.getFolderFromName("").getAbsolutePath(), allFiles);
                for (File file : allFiles) {
                    long time = filesChanged.getOrDefault(file,0l);
                    long delta = file.lastModified()-time;
                    if(delta > 5000) {
                        shouldReload = true;
                        filesChanged.put(file,file.lastModified());
                    }
                }
                if (shouldReload) {
                //    VisualScriptManager.register();
                    Util.print("All Functions got reloaded fix this pls...");
                }
            }
        }
        super.onTick();
    }
}
