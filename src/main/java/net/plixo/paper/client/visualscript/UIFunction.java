package net.plixo.paper.client.visualscript;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.paper.client.visualscript.functions.events.Event;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.UIArray;
import net.plixo.paper.client.ui.elements.UICircle;
import net.plixo.paper.client.ui.elements.UIDraggable;
import net.plixo.paper.client.ui.elements.UILabel;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;

import java.util.HashMap;

public class UIFunction extends UIDraggable {

    public Function function;

    public UIFunction(Function function) {
        super();
        this.function = function;
    }

    public HashMap<Function.Output, UIElement> outputList = new HashMap<>();
    public UIElement[] inputList = new UIElement[0];

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);

        UILabel name = new UILabel();
        name.setDimensions(0, 0, width, 20);
        name.setDisplayName(function.getName());

        UIArray inputs = new UIArray();
        inputs.setDimensions(0, 20, 12, 100);

        UIArray outputs = new UIArray();
        outputs.setDimensions(width - 12, 20, 12, 100);

        boolean hasExecutionInput = function.links.length > 0 && !(function instanceof Event);
        outputList.clear();
        if (hasExecutionInput) {
            UICircle element = new UICircle() {
                @Override
                public void mouseReleased(float mouseX, float mouseY, int state) {
                    if (hovered(mouseX, mouseY) && state == 0) {
                        CursorObject o = EditorManager.viewport.getDraggedObj();
                        if (o.isLink()) {
                            Function nfunction = o.getLink();
                            nfunction.links[o.id] = function;
                            EditorManager.viewport.setDraggedObj(null);
                        }
                    }
                    super.mouseReleased(mouseX, mouseY, state);
                }
            };

            element.setDimensions(0, 0, 12, 12);
            element.setColor(ColorLib.cyan());
            element.radius = 4;
            inputs.add(element);
        }

        for (int i = 0; i < function.links.length; i++) {
            int finalI = i;
            UICircle element = new UICircle() {
                @Override
                public void actionPerformed() {
                    EditorManager.viewport.setDraggedObj(finalI, CursorObject.DraggedType.LINK, function);
                }
            };
            element.setDimensions(0, 0, 12, 12);
            element.radius = 4;
            element.setColor(ColorLib.getMainColor());
            outputs.add(element);
        }

        inputList = new UIElement[function.input.length];
        for (int i = 0; i < function.input.length; i++) {
            int finalI = i;
            UICircle element = new UICircle() {
                @Override
                public void mouseReleased(float mouseX, float mouseY, int state) {
                    if (hovered(mouseX, mouseY) && state == 0) {
                        CursorObject o = EditorManager.viewport.getDraggedObj();
                        if (o.isOutput()) {
                            Function nfunction = o.getLink();
                            function.input[finalI] = nfunction.output[o.id];
                            EditorManager.viewport.setDraggedObj(null);
                        }
                    }
                    super.mouseReleased(mouseX, mouseY, state);
                }
            };
            inputList[i] = element;
            element.setDimensions(0, 0, 12, 12);
            element.radius = 3;
            element.setColor(-1);
            inputs.add(element);
        }

        for (int i = 0; i < function.output.length; i++) {
            int finalI = i;
            UICircle element = new UICircle() {
                @Override
                public void actionPerformed() {
                    EditorManager.viewport.setDraggedObj(finalI, CursorObject.DraggedType.OUTPUT, function);
                }
            };
            outputList.put(function.output[i], element);
            element.setDimensions(0, 0, 12, 12);
            element.radius = 3;
            element.setColor(ColorLib.interpolateColor(0x99CCDDEE, 0xFF000000, 0.4f));
            outputs.add(element);
        }

        float IOHeight = 23 + Math.max(outputs.getMax(), inputs.getMax());

        UIArray resources = new UIArray();
        resources.setDimensions(0, IOHeight, width, 100);

        if (function.settings != null)
            for (Resource setting : function.settings) {
                UIElement element = Resource.getUIElement(setting, 5, 0, width - 10, 20);
                resources.add(element);
            }

        add(name);
        add(inputs);
        add(outputs);
        add(resources);

        this.color = 0;

        this.height = IOHeight + 5 + resources.getMax();
        this.roundness = 4;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        for (int i = 0; i < function.input.length; i++) {
            Function.Output out = function.input[i];
            if (out != null) {

                Function conntectedFunction = out.function;
                UIFunction UIFunction = conntectedFunction.ui;
                UIElement element = UIFunction.outputList.get(out);
                if (element == null) {
                    Util.print("Something went wrong");
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

                /*
                if( out.value != null) {
                    int code = out.value.hashCode();
                    code = code % 25;
                    float percent = code/25f;
                    color = Color.HSBtoRGB(percent,1,1);
                }
                */
                int color =  ColorLib.interpolateColor(0xFFCCDDEE, 0xFF000000, 0.6f);
                Gui.Bezier(0xFFCCDDEE,color, 3*EditorManager.viewport.zoom, start, startR, mid, endL, end);
                Gui.drawCircle(start.x, start.y, 2, 0xFFCCDDEE);
                Gui.drawCircle(end.x, end.y, 2,color);
            }
        }

        for (int i = 0; i < function.links.length; i++) {
            Function nextFunction = function.links[i];
            if (nextFunction != null) {
                UIFunction UIFunction = nextFunction.ui;
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
                Gui.Bezier(ColorLib.getDarker(ColorLib.getMainColor()), ColorLib.cyan(), 3*EditorManager.viewport.zoom, start, startR, mid, endL, end);
                Gui.drawCircle(start.x, start.y, 2, ColorLib.getMainColor());
                Gui.drawCircle(end.x, end.y, 2, ColorLib.cyan());
            }
        }

        int color = ColorLib.getBackground(0.5f);

        if(isSelected) {
            Gui.drawRoundedRect(x-1,y-1,x+width+1,y+20+1,4,-1);
            Gui.drawRect(x-1,y+10-1,x+width+1,y+height-10+1,-1);
            Gui.drawRoundedRect(x-1,y+height-20-1,x+width+1,y+height+1,4,-1);
        }

        Gui.drawRoundedRect(x,y,x+width,y+20,4,color);
        Gui.drawRect(x,y+10,x+width,y+height-10,color);
        Gui.drawRoundedRect(x,y+height-20,x+width,y+height,4,color);


     //   Gui.drawGradientRect(x, y + 20, x + width, y + 23, 0x60000000, 0);


        super.drawScreen(mouseX, mouseY);
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        for (UIElement element : elements) {
            if(element.hovered(mouseX-x,mouseY-y) && !(element instanceof UILabel)) {
                element.mouseClicked(mouseX-x,mouseY-y,mouseButton);
                return;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
