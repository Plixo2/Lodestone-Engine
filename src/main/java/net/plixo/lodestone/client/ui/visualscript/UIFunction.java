package net.plixo.lodestone.client.ui.visualscript;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.elements.canvas.UIDraggable;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.elements.other.UICircle;
import net.plixo.lodestone.client.ui.elements.other.UILabel;
import net.plixo.lodestone.client.ui.resource.UIResource;
import net.plixo.lodestone.client.ui.resource.resource.UITextBox;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.util.FunctionCursorHolder;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.KeyboardUtil;
import net.plixo.lodestone.client.ui.resource.util.SimpleParagraph;
import net.plixo.lodestone.client.visualscript.Function;
import net.plixo.lodestone.client.visualscript.functions.events.FEvent;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class UIFunction extends UIDraggable {

    public Function function;

    public UIFunction(Function function) {
        super();
        this.function = function;
    }

    public HashMap<Function.Output, UIElement> outputList = new HashMap<>();
    public UIElement[] inputList = new UIElement[0];

    public boolean isSmall = false;

    @Override
    public void setDimensions(float x, float y, float width, float height) {

        if(isSmall || function.getSettings().length == 0) {
            width = Math.max(60, UGui.getStringWidth(function.getCustomDisplayString())+20);
        }

        super.setDimensions(x, y, width, height);


        clear();
        String str = function.getDisplayName();
        String nStr = UMath.displayTrim(str,width-13);
        if(!str.equals(nStr)) {
            nStr += "...";
        }
        UILabel name = new UILabel();
        name.setDimensions(0, 0, width, 20);
        name.setDisplayName(nStr);

        UITextBox textbox = new UITextBox();
        textbox.setTextChanged(() -> {
            function.setCustomDisplayString(textbox.getText());
        });
        textbox.setDimensions(0, 0, width, 20);
        textbox.setText(function.getCustomDisplayString());
        if(textbox.getText().isEmpty()) {
            textbox.setText(function.getDisplayName());
        }
        function.setCustomDisplayString(textbox.getText());
        textbox.setColor(0);
        add(textbox);

        UIArray inputs = new UIArray();
        inputs.setDimensions(0, 20, 12, 100);


        UIArray outputs = new UIArray();
        outputs.setDimensions(width - 12, 20, 12, 100);


        outputList.clear();
        boolean hasExecutionInput = function.getLinks().length > 0 && !(function instanceof FEvent);
        if (hasExecutionInput) {
            UICircle element = new UICircle() {
                @Override
                public void mouseReleased(float mouseX, float mouseY, int state) {
                    if (isHovered(mouseX, mouseY) && state == 1) {
                        FunctionCursorHolder cursorObject = ScreenMain.instance.cursorObject.getAs(FunctionCursorHolder.class);
                        if (cursorObject != null && cursorObject.isLink()) {
                            Function nfunction = cursorObject.getLink();
                            nfunction.getLinks()[cursorObject.id] = function;
                        }
                    }
                    super.mouseReleased(mouseX, mouseY, state);
                }
            };

            element.setDimensions(0, 0, 12, 12);
            element.setColor(UColor.cyan());
            element.setRadius(2);
            element.alignRight();
            inputs.add(element);
        }

        for (int i = 0; i < function.getLinks().length; i++) {
            int finalI = i;
            UICircle element = new UICircle();
            element.setCursorObject(() -> new FunctionCursorHolder(finalI, FunctionCursorHolder.DraggedType.LINK, function));
            element.setDimensions(0, 0, 12, 12);
            element.setRadius(2);
            element.alignRight();
            element.setColor(UColor.getMainColor());
            element.setHoverName(function.getLinkNames()[i]);
            outputs.add(element);
        }

        inputList = new UIElement[function.getInput().length];
        for (int i = 0; i < function.getInput().length; i++) {
            int finalI = i;
            UICircle element = new UICircle() {
                @Override
                public void mouseReleased(float mouseX, float mouseY, int state) {
                    if (isHovered(mouseX, mouseY) && state == 1) {
                        FunctionCursorHolder cursorObject = ScreenMain.instance.cursorObject.getAs(FunctionCursorHolder.class);
                        if (cursorObject != null && cursorObject.isOutput()) {
                            Function nfunction = cursorObject.getLink();
                            function.getInput()[finalI] = nfunction.getOutput()[cursorObject.id];
                        }
                    }
                    super.mouseReleased(mouseX, mouseY, state);
                }
            };
            inputList[i] = element;
            element.setDimensions(0, 0, 12, 12);
            element.setRadius(2);
            element.setColor(-1);
            element.alignRight();
            element.setHoverName(function.getInputNames()[i]);
            inputs.add(element);
        }

        for (int i = 0; i < function.getOutput().length; i++) {
            int finalI = i;
            UICircle element = new UICircle();
            element.setCursorObject(() -> new FunctionCursorHolder(finalI, FunctionCursorHolder.DraggedType.OUTPUT, function));
            outputList.put(function.getOutput()[i], element);
            element.setDimensions(0, 0, 12, 12);
            element.setRadius(2);
            element.setColor(0xFFCCDDEE);
            element.alignRight();
            element.setHoverName(function.getOutputNames()[i]);
            outputs.add(element);
        }

        float IOHeight = 23 + Math.max(outputs.getMax(), inputs.getMax());
        UIArray resources = new UIArray();
        resources.setDimensions(0, IOHeight, width, 200);
        resources.space = 3;
        if (!isSmall) {
            for (Resource<?> setting : function.getSettings()) {
                UIResource resource = new UIResource();
                resource.setResource(setting);
                if(function.getSettings().length == 1) {
                    resource.hideName();
                }
                resource.setDimensions(5, 0, width - 10, 20);
                resource.setColor(UColor.getBackground(-0.1f));
                resources.add(resource);
            }
        }

      //  add(name);
        add(inputs);
        add(outputs);
        add(resources);




        float end = IOHeight + resources.getMax();

        UIButton visualCollapse = new UIButton();
        visualCollapse.setDimensions(width / 2 - 8, end + 2,  16, 1);
        visualCollapse.setColor(-1);
        add(visualCollapse);

        UIButton collapse = new UIButton();
        collapse.setDimensions(0, end, width, 5);
        collapse.setColor(0);


        collapse.setAction(() -> {
            isSmall = !isSmall;
            if (isSmall) {
                remove(resources);
            } else {
                if (!elements.contains(resources)) {
                    add(resources);
                }
            }
            setDimensions(this.x,this.y, getWidth(),height);
        });
        add(collapse);

        setRoundness(4);
        setColor(0);
        this.height = end + 5;

    }

    public float getWidth() {
        if (function.getSettings() != null)
            for (Resource<?> setting : function.getSettings()) {
                if (setting.clazz == SimpleParagraph.class) {
                    return 220;
                }
            }
        return 120;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {


        for (int i = 0; i < function.getInput().length; i++) {
            Function.Output out = function.getInput()[i];
            if (out != null) {

                Function connectedFunction = out.function;
                UIFunction UIFunction = connectedFunction.getUI();
                UIElement element = UIFunction.outputList.get(out);
                if (element == null) {
                    UMath.print("Something went wrong");
                    continue;
                }
                float xE = element.x + element.width / 2;
                float yE = element.y + element.height / 2;
                xE += UIFunction.x;
                xE += UIFunction.width - 12;

                yE += UIFunction.y;
                yE += 20;

                UIElement thisElement = inputList[i];
                float xI = this.x + thisElement.x + thisElement.width / 2;
                float yI = 20 + this.y + thisElement.y + thisElement.height / 2;

                float reach = 30;
                Vector2f start = new Vector2f(xI - 12, yI);
                Vector2f startR = new Vector2f(xI - reach, yI);
                Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                Vector2f endL = new Vector2f(xE + reach, yE);
                Vector2f end = new Vector2f(xE + 12, yE);


                UGui.Bezier(0xFFCCDDEE, 0xFFCCDDEE, 3 * UGui.getScale(), start, startR, mid, endL, end);
                UGui.drawCircle(start.x, start.y, 2, 0xFFCCDDEE);
                UGui.drawCircle(end.x, end.y, 2, 0xFFCCDDEE);
                if (UGui.wasHovered) {
                    UGui.Bezier(0xFFF23D4C, 0xFFF23D4C, 3.1f * UGui.getScale(), start, startR, mid, endL, end);
                    UGui.drawCircle(start.x, start.y, 2, 0xFFF23D4C);
                    UGui.drawCircle(end.x, end.y, 2, 0xFFF23D4C);
                    if (KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                        function.getInput()[i] = null;
                    }
                }

            }
        }

        for (int i = 0; i < function.getLinks().length; i++) {
            Function nextFunction = function.getLinks()[i];
            if (nextFunction != null) {
                UIFunction UIFunction = nextFunction.getUI();
                float xE = UIFunction.x + 6;
                float yE = 20 + UIFunction.y + 6;

                float xI = this.x + this.width - 6;
                float yI = this.y + 20 + (i * 12) + 6;

                float reach = 30;
                Vector2f start = new Vector2f(xI + 12, yI);
                Vector2f startR = new Vector2f(xI + reach, yI);
                Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                Vector2f endL = new Vector2f(xE - reach, yE);
                Vector2f end = new Vector2f(xE - 12, yE);
                UGui.Bezier(UColor.getDarker(UColor.getMainColor()), UColor.getDarker(UColor.getMainColor()), 3 * UGui.getScale(), start, startR, mid, endL, end);
                UGui.drawCircle(start.x, start.y, 2, UColor.getMainColor());
                UGui.drawCircle(end.x, end.y, 2, UColor.cyan());
                if (UGui.wasHovered) {
                    UGui.Bezier(0xFFF23D4C, 0xFFF23D4C, 3.1f * UGui.getScale(), start, startR, mid, endL, end);
                    UGui.drawCircle(start.x, start.y, 2,0xFFF23D4C);
                    UGui.drawCircle(end.x, end.y, 2,0xFFF23D4C);
                    if (KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                        function.getLinks()[i] = null;
                    }
                }


            }
        }

        int color = UColor.getBackground(0.3f);

        int headColor = color;
        if (function instanceof FEvent) {
            headColor = UColor.getDarker(UColor.red());
        }

        if (isSelected) {
            int lColor = -1;
            float lineWidth = 0.5f;
            float line = 3;
           // UGui.drawLinedRect(x-lineWidth, y + 10-lineWidth, x + width+lineWidth, y + height - 3+lineWidth, lColor,line);
          //  UGui.drawLinedRoundedRect(x-lineWidth, y-lineWidth, x + width+lineWidth, y + 20, 4+lineWidth, lColor,line);
          //  UGui.drawLinedRoundedRect(x-lineWidth, y + height - 5-lineWidth, x + width+lineWidth, y + height+lineWidth, 4, lColor,line);
            UGui.drawLinedRoundedRect(x-lineWidth,y-lineWidth,x+width+lineWidth,y+height+lineWidth,4,lColor,line);
        }

        UGui.drawRect(x, y + 10, x + width, y + height - 3, UColor.getBackground(0.7f));
        UGui.drawRoundedRect(x, y, x + width, y + 20, 4, headColor);
        UGui.drawRoundedRect(x, y + height - 5, x + width, y + height, 4, color);

        //  UGui.drawRoundedRect(x, y, x + width, y + 20, 4, color);
        //  UGui.drawRect(x, y + 10, x + width, y + height - 10, color);
        //   UGui.drawRoundedRect(x, y + height - 20, x + width, y + height, 4, color);

        super.drawScreen(mouseX, mouseY);
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (UIElement element : elements) {
            if (element.isHovered(mouseX - x, mouseY - y) && !(element instanceof UITextBox)) {
                dragging = false;
            }
        }
    }

}
