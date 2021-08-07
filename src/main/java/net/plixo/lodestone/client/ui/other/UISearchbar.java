package net.plixo.lodestone.client.ui.other;

import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.resource.resource.UITextBox;

import java.util.function.Consumer;

public class UISearchbar extends UICanvas {

    UIArray array;
    UITextBox field;

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);

        field = new UITextBox();
        field.setDimensions(0, 0, 100, height);
        field.setRoundness(0);

        array = new UIArray();
        array.setDimensions(0, height, field.width, 200);

        clear();
        add(field);
        add(array);

        setRoundness(0);
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

    Consumer<String> runnable;

    public void setFilteredRunnable(Consumer<String> runnable) {
        this.runnable = runnable;
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        super.keyPressed(key, scanCode, action);
        array.clear();
        if (field.field.isFocused() && runnable != null) {
            runnable.accept(field.getText());
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        array.clear();
        if (field.field.isFocused() && runnable != null) {
            runnable.accept(field.getText());
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        array.clear();
        if (field.field.isFocused() && runnable != null) {
            runnable.accept(field.getText());
        }
    }

}
