package net.plixo.paper.client.engine.ecs;

import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.ui.elements.values.UIMultiline;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.ui.elements.values.*;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;
import net.plixo.paper.client.util.simple.SimpleColor;
import net.plixo.paper.client.util.simple.SimpleParagraph;

import java.io.File;

public class Resource<O> {

    String name;
    public O value;
    public Class<?> clazz;

    public Resource(String name, O value) {
        this.name = name;
        this.value = value;
        if(value == null) {
            throw new NullPointerException("Value is Null");
        }
        clazz = value.getClass();
    }


    public static UIElement getUIElwement(Resource resource, float x, float y, float width, float height) {
        if (resource.clazz == SimpleParagraph.class) {
            height *= 4;
        }
        UICanvas canvas = new UICanvas();
        canvas.setDimensions(x,y,width,height);
        canvas.setColor(0);

        float strWidth = width*0.33f;

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
        name.setDimensions(0,0,strWidth,height);
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
            /*
            element = new UILabel();
            element.setDimensions(xStart, y, width, height);
            String w = "Missing!";
            if (res.value != null) {
                w = String.valueOf(res.value);
            }
            element.setDisplayName(res.getName() + " | " + w);
            System.out.println(res.getName() + " | " + w);

             */
        }
        element.setRoundness(2);
        canvas.add(element);
        canvas.add(name);
        return canvas;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String clazzName = clazz == null ? "null" : ((clazz.isInterface() ? "interface " : (clazz.isPrimitive() ? "" : "class=")) + clazz.getName());
        return "Resource{" +
                "name='" + name + '\'' +
                ", value=" + value +", "+ clazzName+
                '}';
    }
}
