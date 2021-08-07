package net.plixo.lodestone.client.ui.tabs;

import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.elements.other.UIFileIcon;
import net.plixo.lodestone.client.ui.resource.resource.UIMultiline;
import net.plixo.lodestone.client.ui.screens.ScreenAccept;
import net.plixo.lodestone.client.ui.screens.ScreenCanvasUI;
import net.plixo.lodestone.client.ui.screens.ScreenTextInput;
import net.plixo.lodestone.client.manager.REditor;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.SaveUtil;
import net.plixo.lodestone.client.util.serialiable.Options;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.glfw.GLFW;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class UIFiles extends UIArray {

    File[] files = new File[0];
    static File home;

    public UIFiles() {
        setDisplayName("Files");
        REditor.files = this;
    }


    @Override
    public void init() {
        setColor(UColor.getBackground(0.1f));
        setRoundness(2);

        if (home == null) {
            home = SaveUtil.getFolderFromName("");
        }
        update();
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 1) {
            ScreenMain.instance.beginMenu();
            newRunnable(SaveUtil.FileFormat.CODE);
            newRunnable(SaveUtil.FileFormat.VISUAL_SCRIPT);
            ScreenMain.instance.showMenu();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void newRunnable(SaveUtil.FileFormat format) {
        ScreenMain.instance.addMenuOption("create", "New " + format.name(), () -> {
            ScreenTextInput input = new ScreenTextInput((txt) -> {
                File f = new File(home.getAbsolutePath() + "\\" + txt + "." + format.format);
                if (!f.exists()) {
                    SaveUtil.makeFile(f);
                } else {
                    UMath.print("File already exists");
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

        clear();
        files = home.listFiles();
        if (!Options.options.showMetadata.value) {
            files = Arrays.stream(files).filter(file -> {
                String name = FilenameUtils.getExtension(file.getAbsolutePath());
                return !name.equals(SaveUtil.FileFormat.META.format);
            }).toArray(File[]::new);
        }

        String index = "0123456789abcdefghijklmnopqrstuvwxyzöüä";
        Arrays.sort(files, Comparator.comparingInt(file -> file.isFile() ? index.indexOf(String.valueOf(file.getName().chars().toArray()[0])) : -3));


        int yHeight = 50;
        int xWidth = 40;

        UIButton back = new UIButton();
        back.setAction(() -> {
            if (home.getParentFile() != null) {
                home = home.getParentFile();
                update();
            }
        });
        back.setDimensions(0, 0, 12, 12);
        back.setDisplayName("<");
        back.setRoundness(0);
        back.setColor(0);
        add(back);

        UICanvas line = new UICanvas();
        line.setDimensions(0, 0, width, yHeight);
        line.setColor(0);

        float x = 10;
        for (File f : files) {
            UIFileIcon icon = getByFile(f);
            icon.setDimensions(x, 0, xWidth, yHeight);
            icon.setRoundness(0);
            icon.setColor(0);
            x += xWidth + 10;

            line.add(icon);

            if (x + xWidth > width) {
                x = 10;
                add(line);
                line = new UICanvas();
                line.setDimensions(0, 0, width, yHeight);
                line.setColor(0);
            }
        }
        add(line);
    }

    public UIFileIcon getByFile(File f) {
        return new UIFileIcon(f) {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                File file = getFile();
                if (!isHovered(mouseX, mouseY) || !file.exists()) {
                    return;
                }
                if (isFolder()) {
                    if (mouseButton == 0) {
                        home = file;
                        update();
                    } else if (mouseButton == 1) {
                        ScreenMain.instance.beginMenu();
                        ScreenMain.instance.addMenuOption("Open", () -> {
                            home = file;
                            update();
                        });
                        ScreenMain.instance.addMenuOption("Explorer", () -> {
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        ScreenMain.instance.addMenuOption("Delete", () -> {
                            mc.displayGuiScreen(new ScreenAccept(() -> {
                                file.delete();
                            }, () -> {
                            }, "Sure?"));
                        });
                        ScreenMain.instance.addMenuOption("Rename", () -> {
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
                            mc.displayGuiScreen(input);
                        });
                        ScreenMain.instance.showMenu();
                    }
                } else {
                    if (mouseButton == 1) {
                        String extension = FilenameUtils.getExtension(file.getName());
                        ScreenMain.instance.beginMenu();

                        if (SaveUtil.FileFormat.getFromFile(file) == SaveUtil.FileFormat.VISUAL_SCRIPT) {
                            ScreenMain.instance.addMenuOption("View", () -> REditor.viewport.load(file));
                        } else {
                            ScreenMain.instance.addMenuOption("view", () -> {

                                UIMultiline array = new UIMultiline();
                                List<String> lines = SaveUtil.loadFromFile(file);
                                if (lines.size() > 500) {
                                    UMath.print(file.getName() + " is to big");
                                    return;
                                }

                                ScreenCanvasUI canvasUI = new ScreenCanvasUI() {
                                    @Override
                                    public void onClose() {
                                        SwingUtilities.invokeLater(() -> {
                                            ArrayList<String> lines1 = new ArrayList<>();
                                            lines1.add(array.getText());
                                            SaveUtil.save(file, lines1, false);

                                            mc.displayGuiScreen(new ScreenMain());
                                        });
                                        super.onClose();
                                    }
                                };
                                mc.displayGuiScreen(canvasUI);


                                array.setDimensions(0, 0, canvasUI.width, canvasUI.height);

                                array.setLines(lines.toArray(new String[lines.size()]));

                                canvasUI.add(array);
                            });
                        }

                        ScreenMain.instance.addMenuOption("Edit", () -> {
                            try {
                                Desktop.getDesktop().open(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        ScreenMain.instance.addMenuOption("Explorer", () -> {
                            try {
                                Runtime.getRuntime().exec("explorer.exe /select," + file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        ScreenMain.instance.addMenuOption("Delete", () -> mc.displayGuiScreen(new ScreenAccept(() -> {
                            file.delete(); //update();
                        }, () -> {
                        }, "Sure?")));

                        ScreenMain.instance.addMenuOption("Rename", () -> {
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
                            mc.displayGuiScreen(input);
                        });
                        ScreenMain.instance.showMenu();
                    } else if (mouseButton == 0) {
                        try {
                            REditor.inspector.initInspector(file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                super.

                        mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

                ;
    }
}
