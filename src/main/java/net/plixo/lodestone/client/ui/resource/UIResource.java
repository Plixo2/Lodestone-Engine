package net.plixo.lodestone.client.ui.resource;

import net.minecraft.util.math.vector.Vector3d;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.resource.resource.*;
import net.plixo.lodestone.client.ui.resource.util.SimpleColor;
import net.plixo.lodestone.client.ui.resource.util.SimpleParagraph;
import net.plixo.lodestone.client.ui.resource.util.SimpleSlider;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.SaveUtil;

import java.io.File;

public class UIResource extends UICanvas {

    Resource resource;

    public void setResource(Resource<?> resource) {
        this.resource = resource;
    }

    boolean shouldDisplayName = true;

    public void hideName() {
        shouldDisplayName = false;
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        clear();
        if (resource.clazz == SimpleParagraph.class) {
            height *= 4;
        }
        super.setDimensions(x, y, width, height);

        setCursorObject(() -> resource);

        float strWidth = width / 3f;
        if (!shouldDisplayName) {
            strWidth = -2;
        }

        String displayName = UMath.displayTrim(resource.getName(), strWidth - 12);
        if (!displayName.equals(resource.getName())) {
            displayName += "...";
        }

        UIButton name = new UIButton();
        name.alignLeft();
        name.setAction(() -> UMath.print(resource));
        name.setDisplayName(displayName);
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
            chooser.setTickAction(() -> ((Resource<File>) resource).value = chooser.getFile());
            element = chooser;
        } else if (resource.clazz == Integer.class || resource.clazz == int.class) {
            UISpinner spinner = new UISpinner();
            spinner.setDimensions(xStart, y, width, height);
            spinner.setNumber(((Resource<Integer>) resource).value);
            spinner.setTickAction(() -> resource.value = spinner.getNumber());
            element = spinner;
        } else if (resource.clazz == Float.class || resource.clazz == float.class) {
            UIPointNumber number = new UIPointNumber();
            number.setDimensions(xStart, y, width, height);
            number.setNumber(((Resource<Float>) resource).value);
            number.setTickAction(() -> resource.value = (float) number.getAsDouble());
            element = number;
        } else if (resource.clazz == Boolean.class || resource.clazz == boolean.class) {
            UIToggleButton toggleButton = new UIToggleButton();
            toggleButton.setDimensions(xStart, y, width, height);
            toggleButton.setState(((Resource<Boolean>) resource).value);
            toggleButton.setTickAction(() -> resource.value = toggleButton.getState());
            element = toggleButton;
        } else if (resource.clazz == String.class) {
            UITextBox textbox = new UITextBox();
            textbox.setDimensions(xStart, y, width, height);
            textbox.setText(((Resource<String>) resource).value);
            textbox.setTickAction(() -> resource.value = textbox.getText());
            element = textbox;
        } else if (resource.clazz == Vector3d.class) {
            UIVector vector = new UIVector();
            vector.setDimensions(xStart, y, width, height);
            vector.setVector(((Resource<Vector3d>) resource).value);
            vector.setTickAction(() -> resource.value = vector.getAsVector());
            element = vector;
        } else if (resource.clazz == SimpleParagraph.class) {
            UIMultiline textbox = new UIMultiline();
            textbox.setDimensions(xStart, y, width, height);
            textbox.setText(((Resource<SimpleParagraph>) resource).value.value);
            textbox.setTickAction(() -> resource.value = new SimpleParagraph(textbox.getText()));
            element = textbox;
        } else if (resource.clazz == Class.class) {
            UIClass clazz = new UIClass();
            clazz.setDimensions(xStart, y, width, height);
            clazz.setReference((Resource<Class<?>>) resource);
            clazz.setTickAction(() -> resource.value = clazz.getClazz());
            element = clazz;
        } else if (resource.clazz == SimpleColor.class) {
            UIColorChooser chooser = new UIColorChooser();
            chooser.setDimensions(xStart, y, width, height);
            chooser.setCustomColor(((Resource<SimpleColor>) resource).value.value);
            chooser.setTickAction(() -> resource.value = new SimpleColor(chooser.getCustomColor()));
            element = chooser;
        } else if (resource.clazz == SimpleSlider.class) {
            UISlider uiSlider = new UISlider();
            uiSlider.setDimensions(xStart, y, width, height);
            Resource<SimpleSlider> resource = this.resource;
            uiSlider.setSimpleSlider(resource.value);
            uiSlider.setTickAction(() -> this.resource.value = resource.value);
            element = uiSlider;
        } else if (resource.clazz.isEnum()) {
            UIEnum uiEnum = new UIEnum();
            uiEnum.setDimensions(xStart, y, width, height);
            uiEnum.setEnumType(((Resource<Enum<?>>) resource).value);
            uiEnum.setTickAction(() -> resource.value = uiEnum.getType());
            element = uiEnum;
        } else {
            UIObject uiObject = new UIObject();
            uiObject.setObject(resource.value);
            uiObject.setDimensions(xStart, y, width, height);
            element = uiObject;
        }
        element.setRoundness(getRoundness());
        if (shouldDisplayName) {
            add(name);
        }
        add(element);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (isHovered(mouseX, mouseY)) {
            File file = ScreenMain.instance.cursorObject.getAs(File.class);
            if (file != null) {
                if (resource.clazz == File.class) {
                    resource.value = new File(file.getAbsolutePath());
                    setDimensions(x, y, width, height);
                } else if (resource.clazz == SimpleParagraph.class) {
                    ((SimpleParagraph) resource.value).value = SaveUtil.loadAsString(file.getAbsolutePath());
                    setDimensions(x, y, width, height / 4);
                }
            }
        }
    }
}
