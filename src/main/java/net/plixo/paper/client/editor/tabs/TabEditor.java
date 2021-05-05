package net.plixo.paper.client.editor.tabs;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.ui.GUI.GUIEditor;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.*;
import net.plixo.paper.client.util.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class TabEditor extends UITab {
    public TabEditor(int id) {
        super(id, "Editor");
        EditorManager.editor = this;
    }

    UIArray array;
    UIArray propositions;
    int currentLine = 0;

    String[] other = {
            "nFunction",
            "public",
            "class",
            "extends",
            "void",
            "import",
            "@Override",

            "set() {}",
            "set(inputs,outputs,links);",

            "input(index)",
            "output(index,object);",
            "hasInput(index)",

            "pullInputs();",
            "calculate() {}",
            "run() {}",
            "execute();",

            "float",
            "int",
            "boolean",
            "Number",
            ".floatValue()",
            ".intValue()",

            "if",
            "else",
            "true",
            "false",
            "instanceof",
            "equals",
            "&&",
            "==",
            "||",
            "!=",

    };

    public void load(File file) {
        this.file = file;
        Util.print("load file " + file);
        init();
    }

    File file;

    @Override
    public void init() {
        currentLine = 0;
        canvas = new UICanvas() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                Gui.drawLine(parent.width - 240, 0, parent.width - 240, parent.height, 0x35FFFFFF, 1);
            }
        };
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);


        array = new UIArray();
        array.setDimensions(0, 0, parent.width - 240, parent.height);


        if (file != null && file.exists()) {
            ArrayList<String> list = SaveUtil.loadFromFile(file);
            for (String s : list) {
                addLine(s.replace("\t", "    "));
            }
        }

        propositions = new UIArray();
        propositions.setDimensions(parent.width - 240, 0, 240, parent.height);

        canvas.add(array);
        canvas.add(propositions);

        super.init();
    }

    public void updateImports() {
        if (propositions != null && array.hasValues()) {
            propositions.clear();
            UITextbox lastField = (UITextbox) array.elements.get(currentLine);
            String txt = lastField.getText();
            if (txt.startsWith("import ")) {
                String classPath = txt.substring(7);

                int i = 0;
                for (String name : ClassPaths.names) {
                    if (name.startsWith(classPath)) {
                        i += 1;
                        addProposition(name.substring(classPath.length()),() -> lastField.setText("import " + name + ";"));
                        if (i > 100) {
                            break;
                        }
                    }
                }
            } else {
                if(txt.isEmpty()) {
                    for (String s1 : other) {
                            addProposition(s1,null);
                    }
                } else
                if (txt.contains(" ")) {
                    String[] first = txt.split(" ");
                    if (first != null) {
                        for (String s : first) {
                            if (s.length() > 2) {
                                int i = 0;
                                for (String s1 : other) {
                                    if (s1.startsWith(s)) {
                                        addProposition(s1,null);
                                    }
                                }
                                for (String name : ClassPaths.names) {
                                    if (name.endsWith(s)) {
                                        i += 1;
                                        addProposition(name,() ->  addLine("import " + name + ";",0));
                                        if (i > 100) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    public void addProposition(String name , Runnable action) {
        UILabel label = new UILabel() {
            @Override
            public void drawDisplayString() {
                if (displayName != null) {
                    Gui.drawString(displayName, x + 1, y + height / 2, textColor);
                }
            }
            @Override
            public void actionPerformed() {
                if (action != null) {
                    action.run();
                }
            }
        };
        label.setDisplayName(name);
        label.setDimensions(0, 0, 240, 12);
        propositions.add(label);
    }


    public void addLine(String txt) {
        UITextbox text = new UITextbox() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                Gui.drawRect(x, y, x + 2, y + height, ColorLib.getMainColor());
            }
        };
        text.setDimensions(0, 0, array.width, 11);
        text.setText(txt);
        text.setColor(0);
        text.setRoundness(0);
        text.setOutlineColor(0);
        array.add(text);
    }

    public UITextbox addLine(String txt , int index) {

        UITextbox box = new UITextbox() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                Gui.drawRect(x, y, x + 2, y + height, ColorLib.getMainColor());
            }
        };
        box.setDimensions(0, 0, array.width, 11);
        box.setText(txt);
        box.setColor(0);
        box.setOutlineColor(0);
        box.setRoundness(0);

        array.elements.add(index,box);
        array.sort();
        return box;
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        if (array == null) {
            return;
        }
        if (array.hasValues()) {

            UITextbox lastField = (UITextbox) array.elements.get(Util.clamp(currentLine, array.elements.size() - 1, 0));
            if (KeyboardUtil.isKeyDown(264) && key == 264) {
                currentLine += 1;
            }
            if (KeyboardUtil.isKeyDown(265) && key == 265) {
                currentLine -= 1;
            }

            currentLine = Util.clamp(currentLine, array.elements.size() - 1, 0);

            for (int i = 0; i < array.elements.size(); i++) {
                UITextbox box = (UITextbox) array.elements.get(i);
                box.field.setFocused2(i == currentLine);
            }
            UITextbox currentBox = (UITextbox) array.elements.get(currentLine);
            currentBox.field.setCursorPosition(lastField.field.getCursorPosition());
            boolean tab = KeyboardUtil.isKeyDown(258) && key == 258;
            boolean remove = KeyboardUtil.isKeyDown(259) && key == 259;
            boolean add = KeyboardUtil.isKeyDown(257) && key == 257;
            if (tab) {
                int pos = currentBox.field.getCursorPosition();
                String last = currentBox.getText().substring(pos);
                String first = currentBox.getText().substring(0, pos);
                currentBox.field.setText(first + "    " + last);
                currentBox.field.setCursorPosition(first.length() + 4);
            }
            if (remove) {
                if (currentBox.field.getCursorPosition() == 0) {
                    String text = currentBox.getText();
                    if (currentLine >= 1) {
                        currentLine -= 1;
                        UITextbox upperBox = (UITextbox) array.elements.get(currentLine);
                        String txt = upperBox.getText();
                        upperBox.setText(txt + text);
                        upperBox.field.setCursorPosition(txt.length());
                        upperBox.field.setFocused2(true);
                        array.remove(currentBox);
                        return;
                    }
                }
            }
            if (add) {
                int pos = currentBox.field.getCursorPosition();
                String text = currentBox.getText();
                String last = currentBox.getText().substring(pos, text.length());
                String first = currentBox.getText().substring(0, pos);

                currentBox.setText(first);

                currentLine += 1;
                UITextbox box = addLine(last,currentLine);
                box.field.setCursorPosition(0);
                currentBox.field.setFocused2(false);
                box.field.setFocused2(true);

            }
            /*
            GUIEditor.instance.hideMenu();
            String txt = currentBox.getText();
            if(txt.startsWith("import")) {
                String packag = Util.replace(txt,"import","");
                packag = Util.replace(packag," ","");
                Util.print(packag);
                if(packag.endsWith(".")) {
                    packag = packag.substring(0,packag.length()-1);
                }


                GUIEditor.instance.beginMenu();
                try {
                    for (String nx : names) {
                        if(nx.contains("$")) {
                            continue;
                        }
                        String name = nx;
                        if(name.contains(packag)) {
                            String finalName = name;
                            GUIEditor.instance.addMenuOption(name.replace(packag,""),() -> {
                                currentBox.setText("import " + nx);
                            });
                        }

                    }
                } catch (Exception e) {
                    Util.print(e.getMessage());
                    e.printStackTrace();
                }
                GUIEditor.instance.showMenu();
            }

             */

        } else if (KeyboardUtil.isKeyDown(257) && key == 257) {
            addLine("");
        }

        super.keyPressed(key, scanCode, action);
        updateImports();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        updateImports();
    }

    @Override
    public void close() {
        if (file != null && file.exists()) {
            ArrayList<String> list = new ArrayList<>();
            for (UIElement element : array.elements) {
                UITextbox box = (UITextbox) element;
                list.add(box.getText());
            }
            SaveUtil.save(file, list, true);
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        for (int i = 0; i < array.elements.size(); i++) {
            UITextbox box = (UITextbox) array.elements.get(i);
            box.field.setFocused2(false);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (int i = 0; i < array.elements.size(); i++) {
            UITextbox box = (UITextbox) array.elements.get(i);
            if (box.field.isFocused()) {
                currentLine = i;
                break;
            }
        }
    }
}
