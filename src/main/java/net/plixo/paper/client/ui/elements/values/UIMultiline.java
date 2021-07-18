package net.plixo.paper.client.ui.elements.values;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UIArray;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.KeyboardUtil;
import net.plixo.paper.client.util.Util;

public class UIMultiline extends UIArray {

    float textHeight = 11;
    int currentLine = 0;

    public void setText(String text) {
       setLines(text.split(System.getProperty("line.separator")));
    }
    public void setLines(String... lines) {
        currentLine = 0;
        for (String s : lines) {
            addLine(s.replace("\t", "    "));
        }
    }
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (UIElement element : elements) {
            UITextbox box = (UITextbox) element;
            stringBuilder.append(box.getText() + System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }


    void addLine(String txt) {
        UITextbox text = new UITextbox() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                Gui.drawRect(x, y, x + 2, y + height, ColorLib.getMainColor());
            }
        };
        text.setDimensions(0, 0, this.width, textHeight);
        text.setText(txt);
        text.setColor(0);
        text.setRoundness(0);

        add(text);
    }
     UITextbox addLine(String txt, int index) {

        UITextbox box = new UITextbox() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                super.drawScreen(mouseX, mouseY);
                Gui.drawRect(x, y, x + 2, y + height, ColorLib.getMainColor());
            }
        };
        box.setDimensions(0, 0, width, textHeight);
        box.setText(txt);
        box.setColor(0);
        box.setRoundness(0);

        elements.add(index, box);
        sort();
        return box;
    }

    boolean hovered = false;

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
        hovered = hovered(mouseX,mouseY);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if(!hovered) return;
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        if(!hovered) return;
        if (hasValues()) {
            UITextbox lastField = (UITextbox) elements.get(Util.clamp(currentLine, elements.size() - 1, 0));
            if (KeyboardUtil.isKeyDown(264) && key == 264) {
                currentLine += 1;
            }
            if (KeyboardUtil.isKeyDown(265) && key == 265) {
                currentLine -= 1;
            }

            currentLine = Util.clamp(currentLine, elements.size() - 1, 0);

            for (int i = 0; i < elements.size(); i++) {
                UITextbox box = (UITextbox) elements.get(i);
                box.field.setFocused2(i == currentLine);
            }
            UITextbox currentBox = (UITextbox) elements.get(currentLine);
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
                        UITextbox upperBox = (UITextbox) elements.get(currentLine);
                        String txt = upperBox.getText();
                        upperBox.setText(txt + text);
                        upperBox.field.setCursorPosition(txt.length());
                        upperBox.field.setFocused2(true);
                        remove(currentBox);
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
                UITextbox box = addLine(last, currentLine);
                box.field.setCursorPosition(0);
                currentBox.field.setFocused2(false);
                box.field.setFocused2(true);
            }

        } else if (KeyboardUtil.isKeyDown(257) && key == 257) {
            addLine("");
        }
        super.keyPressed(key, scanCode, action);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        for (int i = 0; i < elements.size(); i++) {
            UITextbox box = (UITextbox) elements.get(i);
            box.field.setFocused2(false);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (int i = 0; i < elements.size(); i++) {
            UITextbox box = (UITextbox)elements.get(i);
            if (box.field.isFocused()) {
                currentLine = i;
                break;
            }
        }
    }
}
