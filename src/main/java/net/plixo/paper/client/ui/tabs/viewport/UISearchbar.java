package net.plixo.paper.client.ui.tabs.viewport;

import net.plixo.paper.client.ui.elements.canvas.UIArray;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.ui.elements.values.UITextbox;
import net.plixo.paper.client.util.Util;

public class UISearchbar extends UICanvas {

    UIArray array;
    UITextbox field;

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        clear();

        field = new UITextbox();
        field.setDimensions(0, 0, 80, height);
        field.setRoundness(0);

        array = new UIArray();
        array.setDimensions(0, height, field.width, 200);

        add(field);
        add(array);

        setRoundness(0);
        // setColor(ColorLib.getBackground(-0.2f));
        setColor(0);
    }

    public String getText() {
        return field.getText();
    }

    public void addFilteredOption(String name, Runnable runnable) {
        UIButton button = new UIButton();
        button.setDimensions(0, 0, array.width, this.height);
        button.setDisplayName(name);
        button.setRoundness(0);
        button.setAction(runnable);
        array.add(button);
    }

    Util.IObject<String> runnable;

    public void setFilteredRunnable(Util.IObject<String> runnable) {
        this.runnable = runnable;
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        super.keyPressed(key, scanCode, action);
        array.clear();
        if (field.field.isFocused() && runnable != null) {
            runnable.run(field.getText());
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        array.clear();
        if (field.field.isFocused() && runnable != null) {
            runnable.run(field.getText());
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        array.clear();
        if (field.field.isFocused() && runnable != null) {
            runnable.run(field.getText());
        }
    }

}
