package net.plixo.paper.client.editor.tabs;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.ui.accept.UIAccept;
import net.plixo.paper.client.editor.ui.accept.UITextInput;
import net.plixo.paper.client.editor.ui.other.FileIcon;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.editor.visualscript.Canvas;
import net.plixo.paper.client.engine.buildIn.visualscript.Module;
import net.plixo.paper.client.util.*;
import org.apache.commons.io.FilenameUtils;

public class TabFiles extends UITab {

    File[] files = new File[0];

    File home;

    ArrayList<FileIcon> icons = new ArrayList<FileIcon>();

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

                File file = icon.file;
                if (icon.isFolder) {
                    if (mouseButton == 0) {
                        home = icon.file;
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
                        showMenu(0, mouseX, mouseY, open, explorer);


                        //
                    }
                } else {
                    String extenstion = FilenameUtils.getExtension(icon.file.getName());

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

                    //lastSelected = icon.file;
                    showMenu(0, mouseX, mouseY, open, edit, explorer, delete);
                }

                return;
            }
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
    /*

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
            } else if (option == 2) {
                try {
                    // Desktop.getDesktop().open(lastSelected.getParentFile());
                    Runtime.getRuntime().exec("explorer.exe /select," + lastSelected);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (option == 3) {

            }

        } else if (id == -1) {


            UITextInput input = new UITextInput((txt) -> {
                File f = new File(home.getAbsolutePath() + "\\" + txt + "." + SaveUtil.FileFormat.values()[option].format);
                if (!f.exists()) {
                    if (SaveUtil.FileFormat.values()[option] == SaveUtil.FileFormat.Code) {
                        ArrayList<String> lines = new ArrayList<>();
                        lines.add("var output = \"float\";");
                        lines.add("var input = [\"float\" , \"float\"];");
                        lines.add("var execution = false;");
                        lines.add("");
                        lines.add("//multiplies two floating point numbers together");
                        lines.add("function execute(m1, m2) {");
                        lines.add("    return m1 * m2;");
                        lines.add("}");
                        SaveUtil.save(f, lines, true);
                    } else {
                        SaveUtil.makeFile(f);
                    }
                }
            }, (txt) -> {
            }, SaveUtil.FileFormat.values()[option].format);

            mc.displayGuiScreen(input);

        }


        super.optionsSelected(id, option);

    }

     */


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
