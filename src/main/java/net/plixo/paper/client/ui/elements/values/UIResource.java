package net.plixo.paper.client.ui.elements.values;

import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.ui.GUI.GUIMain;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;
import net.plixo.paper.client.util.simple.SimpleColor;
import net.plixo.paper.client.util.simple.SimpleParagraph;

import java.io.File;

public class UIResource extends UICanvas {

    Resource resource;

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        clear();
        if (resource.clazz == SimpleParagraph.class) {
            height *= 4;
        }
        super.setDimensions(x, y, width, height);
        setColor(0);

        setCursorObject(() -> resource);

        float strWidth = width * 0.33f;

        UIButton name = new UIButton() {
            @Override
            public void drawStringCentered(float mouseX, float mouseY) {
                if (displayName != null) {
                    Gui.drawString(displayName, x + 4, y + height / 2, textColor);
                }
            }
        };
        name.setAction(() -> Util.print(resource));
        name.setDisplayName(resource.getName());
        name.setDimensions(0, 0, strWidth, height);
        name.setRoundness(2);
        name.setColor(0);

        strWidth += 2;

        float xStart = strWidth;
        width -= strWidth;
        y = 0;

        UIElement element;
        if (resource.clazz == File.class) {
            UIFileChooser chooser = new UIFileChooser();
            chooser.setDimensions(xStart, y, width, height);
            chooser.setFile(((Resource<File>) resource).value);
            chooser.onTick = () -> ((Resource<File>) resource).value = chooser.getFile();
            element = chooser;
        } else if (resource.clazz == Integer.class || resource.clazz == int.class) {
            UISpinner spinner = new UISpinner();
            spinner.setDimensions(xStart, y, width, height);
            spinner.setNumber(((Resource<Integer>) resource).value);
            spinner.onTick = () -> resource.value = spinner.getNumber();
            element = spinner;
        } else if (resource.clazz == Float.class || resource.clazz == float.class) {
            UIPointNumber number = new UIPointNumber();
            number.setDimensions(xStart, y, width, height);
            number.setNumber(((Resource<Float>) resource).value);
            number.onTick = () -> resource.value = (float) number.getAsDouble();
            element = number;
        } else if (resource.clazz == Boolean.class || resource.clazz == boolean.class) {
            UIToggleButton toggleButton = new UIToggleButton();
            toggleButton.setDimensions(xStart, y, width, height);
            toggleButton.setState(((Resource<Boolean>) resource).value);
            toggleButton.onTick = () -> resource.value = toggleButton.getState();
            element = toggleButton;
        } else if (resource.clazz == String.class) {
            UITextbox textbox = new UITextbox();
            textbox.setDimensions(xStart, y, width, height);
            textbox.setText(((Resource<String>) resource).value);
            textbox.onTick = () -> resource.value = textbox.getText();
            element = textbox;
        } else if (resource.clazz == Vector3d.class) {
            UIVector vector = new UIVector();
            vector.setDimensions(xStart, y, width, height);
            vector.setVector(((Resource<Vector3d>) resource).value);
            vector.onTick = () -> resource.value = vector.getAsVector();
            element = vector;
        } else if (resource.clazz == SimpleParagraph.class) {
            UIMultiline textbox = new UIMultiline();
            textbox.setDimensions(xStart, y, width, height);
            textbox.setText(((Resource<SimpleParagraph>) resource).value.value);
            textbox.onTick = () -> resource.value = new SimpleParagraph(textbox.getText());
            element = textbox;
        } else if (resource.clazz == SimpleColor.class) {
            UIColorChooser chooser = new UIColorChooser();
            chooser.setDimensions(xStart, y, width, height);
            chooser.setCustomColor(((Resource<SimpleColor>) resource).value.value);
            chooser.onTick = () -> resource.value = new SimpleColor(chooser.getCustomColor());
            element = chooser;
        } else if (resource.clazz.isEnum()) {
            UIEnum uiEnum = new UIEnum();
            uiEnum.setDimensions(xStart, y, width, height);
            uiEnum.setEnumType(((Resource<Enum<?>>) resource).value);
            uiEnum.onTick = () -> resource.value = uiEnum.getType();
            element = uiEnum;
        } else {
            UIObject uiObject = new UIObject();
            uiObject.setObject(resource.value);
            uiObject.setDimensions(xStart, y, width, height);
            element = uiObject;
        }
        element.setRoundness(this.roundness);
        add(element);
        add(name);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if(hovered(mouseX, mouseY) && resource.clazz == File.class) {
            File file = GUIMain.instance.cursorObject.getAs(File.class);
            if(file != null) {
                resource.value = new File(file.getAbsolutePath());
                setDimensions(x,y,width,height);
            }
        }
    }
}
