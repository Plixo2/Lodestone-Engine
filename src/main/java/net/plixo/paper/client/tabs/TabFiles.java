package net.plixo.paper.client.tabs;

import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.ui.GUI.GUIAccept;
import net.plixo.paper.client.ui.GUI.GUIMain;
import net.plixo.paper.client.ui.GUI.GUITextInput;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.canvas.UIArray;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.visual.UIFileIcon;
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

@Deprecated
public class TabFiles extends UITab {

    File[] files = new File[0];
    static File home;

    public TabFiles(int id) {
        super(id, "Files");
       // EditorManager.files = this;
    }


    @Override
    public void init() {
        canvas = new UIArray();
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
        if (parent.isMouseInside(mouseX, mouseY) && mouseButton == 1) {
            GUIMain.instance.beginMenu();
            newRunnable(SaveUtil.FileFormat.Code);
            newRunnable(SaveUtil.FileFormat.VisualScript);
            GUIMain.instance.showMenu();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void newRunnable(SaveUtil.FileFormat format) {
        GUIMain.instance.addMenuOption("New " + format.name(), () -> {
            GUITextInput input = new GUITextInput((txt) -> {
                File f = new File(home.getAbsolutePath() + "\\" + txt + "." + format.format);
                if (!f.exists()) {
                    SaveUtil.makeFile(f);
                } else {
                    Util.print("File already exists");
                }
            }, (txt) -> {
            }, format.format);
            input.Syes = "Create";
            mc.displayGuiScreen(input);
        });
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
        //if (!Options.options.showMetadata) {
            files = Arrays.stream(files).filter(file -> {
                String name = FilenameUtils.getExtension(file.getAbsolutePath());
                return !name.equals(SaveUtil.FileFormat.Meta.format);
            }).toArray(File[]::new);
      //  }

        String index = "0123456789abcdefghijklmnopqrstuvwxyzöüä";
        Arrays.sort(files, Comparator.comparingInt(file -> file.isFile() ? index.indexOf(String.valueOf(file.getName().chars().toArray()[0])) : -3));


        int yHeight = 50;
        int xWidth = 40;

        UIButton back = new UIButton() {
            @Override
            public void actionPerformed() {
                if (home.getParentFile() != null) {
                    home = home.getParentFile();
                    update();
                }
            }
        };
        back.setDimensions(0, 0, 12, 12);
        back.setDisplayName("<");
        back.setRoundness(0);
        back.setColor(0);
        canvas.add(back);


        UICanvas line = new UICanvas();
        line.setDimensions(0, 0, parent.width, yHeight);
        line.setColor(0);

        float x = 10;
        for (File f : files) {
            UIFileIcon icon = getByFile(f);
            icon.setDimensions(x, 0, xWidth, yHeight);
            icon.setRoundness(0);
            icon.setColor(0);
            x += xWidth + 10;

            line.add(icon);

            if (x + xWidth > parent.width) {
                x = 10;
                canvas.add(line);
                line = new UICanvas();
                line.setDimensions(0, 0, parent.width, yHeight);
                line.setColor(0);
            }
        }
        canvas.add(line);

    }

    public UIFileIcon getByFile(File f) {
        return new UIFileIcon(f) {
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
                        GUIMain.instance.beginMenu();
                        GUIMain.instance.addMenuOption("Open", () -> {
                            home = file;
                            update();
                        });
                        GUIMain.instance.addMenuOption("Explorer", () -> {
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        GUIMain.instance.addMenuOption("Delete", () -> {
                            mc.displayGuiScreen(new GUIAccept(() -> {
                                file.delete(); //update();
                            }, () -> {
                            }, "Sure?"));
                        });
                        GUIMain.instance.addMenuOption("Rename", () -> {
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
                        });
                        GUIMain.instance.showMenu();
                    }
                } else {
                    if (mouseButton == 1) {
                        String extenstion = FilenameUtils.getExtension(file.getName());
                        GUIMain.instance.beginMenu();

                        if (SaveUtil.FileFormat.getFromFile(file) == SaveUtil.FileFormat.VisualScript) {
                            GUIMain.instance.addMenuOption("View", () -> {
                                EditorManager.viewport.load(file);
                            });
                        }
                        if (SaveUtil.FileFormat.getFromFile(file) == SaveUtil.FileFormat.Code) {
                            GUIMain.instance.addMenuOption("View", () -> {

                            });
                        }

                        GUIMain.instance.addMenuOption("Edit", () -> {
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        GUIMain.instance.addMenuOption("Explorer", () -> {
                            try {
                                Runtime.getRuntime().exec("explorer.exe /select," + file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        GUIMain.instance.addMenuOption("Delete", () -> {
                            mc.displayGuiScreen(new GUIAccept(() -> {
                                file.delete(); //update();
                            }, () -> {
                            }, "Sure?"));
                        });
                        GUIMain.instance.addMenuOption("Rename", () -> {
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
                        });
                        GUIMain.instance.showMenu();
                    } else if (mouseButton == 0) {
                        try {
                            EditorManager.inspector.initInspector(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        };
    }
}
