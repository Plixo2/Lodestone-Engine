package net.plixo.lodestone.client.ui.resource.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.gui.screen.Screen;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.screens.ScreenCanvasUI;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.elements.other.UILabel;
import net.plixo.lodestone.client.util.UColor;
import org.plixo.jrcos.Serializer;

import java.util.Map;

/**
 * for displaying and choosing a file with {@code JFileChooser}
 **/
public class UIObject extends UICanvas {

    Object object;

    public UIObject() {
        setColor(0);
    }


    @Override
    public void drawScreen(float mouseX, float mouseY) {

       drawDefault( UColor.getBackground(0.3f));
        drawHoverEffect();


        if (object != null) {
            UGui.drawString(object.getClass().getSimpleName()+".class",x+5,y+height/2,UColor.getTextColor());
        }

        super.drawScreen(mouseX, mouseY);
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        UIButton button = new UIButton();
        try {
            JsonElement element = Serializer.getJson(object);
            button.setAction(() -> {
                JsonGui jsonGui = new JsonGui(object.getClass().getName(), element);
                mc.displayGuiScreen(jsonGui);
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        button.setDisplayName("View");
        button.setRoundness(2);
        button.setDimensions(width - 40, 0, 40, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }

    //set current file
    public void setObject(Object object) {
        this.object = object;
    }

    public static class JsonGui extends ScreenCanvasUI {

        Screen lastScreen;
        String sName;
        JsonElement sElement;

        public JsonGui(String name, JsonElement element) {
            super();
            sName = name;
            sElement = element;
            lastScreen = mc.currentScreen;
        }

        @Override
        protected void init() {
            super.init();
            add(getUIElementFromJson(sName, sElement));
        }

        public UIElement getUIElementFromJson(String name, JsonElement element) {

            UIArray array = new UIArray();
            array.setDimensions(0, 0, 300, 1000);
            array.space = 4;

            UIButton button = new UIButton();
            button.setDimensions(0, 0, 20 + UGui.getStringWidth(name), 20);
            button.setDisplayName("< " + name);
            button.setAction(() -> mc.displayGuiScreen(lastScreen));

            array.add(button);

            if (element.isJsonPrimitive()) {
                UIElement uiElement = null;
                JsonPrimitive primitive = element.getAsJsonPrimitive();

                if (primitive.isNumber()) {
                    Number n = primitive.getAsNumber();
                    if (n instanceof Integer) {
                        UISpinner number = new UISpinner();
                        number.setDimensions(0, 0, array.width, 20);
                        number.setNumber(n.intValue());
                        uiElement = number;
                    } else {
                        UIPointNumber number = new UIPointNumber();
                        number.setDimensions(0, 0, array.width, 20);
                        number.setNumber(n.floatValue());
                        uiElement = number;
                    }
                } else if (primitive.isBoolean()) {
                    UIToggleButton uiToggleButton = new UIToggleButton();
                    uiToggleButton.setDimensions(0, 0, array.width, 20);
                    uiToggleButton.setState(primitive.getAsBoolean());
                    uiElement = uiToggleButton;
                } else if (primitive.isString()) {
                    UITextBox uiTextbox = new UITextBox();
                    uiTextbox.setDimensions(0, 0, array.width, 20);
                    uiTextbox.setText(primitive.getAsString());
                    uiElement = uiTextbox;
                }
                if (uiElement != null) {
                    array.add(uiElement);
                }
            } else if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                for (Map.Entry<String, JsonElement> sJEntry : jsonObject.entrySet()) {

                    UICanvas canvas = new UICanvas();
                    canvas.setDimensions(0, 0, array.width, 20);
                    canvas.setColor(0);

                    UILabel uiLabel = new UILabel();
                    uiLabel.setDimensions(0, 0, 20, 20);
                    uiLabel.setDisplayName("> ");

                    canvas.add(uiLabel);

                    UIButton open = new UIButton();
                    open.setDimensions(30, 0, 10 + UGui.getStringWidth(sJEntry.getKey()), 20);
                    open.setDisplayName(sJEntry.getKey());
                    open.setAction(() -> mc.displayGuiScreen(new JsonGui(sJEntry.getKey(), sJEntry.getValue())));

                    canvas.add(open);


                    array.add(canvas);
                }
            } else if (element.isJsonArray()) {
                JsonArray jsonArray = element.getAsJsonArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    UIButton uiButton = new UIButton();
                    uiButton.setDimensions(0, 0, 30, 20);
                    uiButton.setDisplayName(String.valueOf(i));
                    uiButton.setColor(UColor.blue());
                    int finalI = i;
                    uiButton.setAction(() -> mc.displayGuiScreen(new JsonGui(name + " Array (Element " + finalI + ")", jsonArray.get(finalI))));
                    array.add(uiButton);
                }
                if(jsonArray.size() == 0) {
                    UIButton uiButton = new UIButton();
                    uiButton.setDimensions(0, 0, 30, 20);
                    uiButton.setDisplayName("Empty");
                    uiButton.setColor(UColor.red());
                    array.add(uiButton);
                }
            }

            return array;
        }

    }
}
