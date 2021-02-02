package net.plixo.paper.client.editor.blueprint;

import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.tabs.TabViewport;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.buildIn.blueprint.BlueprintManager;
import net.plixo.paper.client.engine.buildIn.blueprint.Module;
import net.plixo.paper.client.engine.buildIn.blueprint.event.Event;
import net.plixo.paper.client.engine.buildIn.blueprint.function.Function;
import net.plixo.paper.client.engine.buildIn.blueprint.function.other.Connection;
import net.plixo.paper.client.engine.buildIn.blueprint.function.other.Execute;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.GetGlobalVariable;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import java.util.ArrayList;

public class DrawFunction {

    static float connectionButtonWidth = 10f;
    static float headerHeight = 20;
    static float IOHeight = 15f;

    ArrayList<Rect> bodies = new ArrayList<Rect>();

    float calcWidth = 0;
    boolean dragging = false;

    float dragX = 0, dragY = 0;

    public Function function;

    Rect head;
    Rect leftConnection;

    ArrayList<Rect> rights = new ArrayList<Rect>();
    public float x = 0, y = 0;

    public DrawFunction(Function function) {
        this.function = function;
        function.setDrawFunction(this);
    }

    void calcWidth() {
        float max = Gui.getStringWidth(function.name) + 15;
        for (int i = 0; i < function.inputTypes.length; i++) {

            String str;
            if (function.names != null) {
                str = function.names[i];
            } else {
                str = function.inputTypes[i].name();
            }

            float width = Gui.getStringWidth(str);

            if (function.outputs.length > 0 && i < function.outputs.length) {
                float w = Gui.getStringWidth(function.outputs[i].name);
                float maxw = Math.max(w, width);
                width = maxw * 2;
            }

            if (width > max) {
                max = width;
            }
        }

        for (int i = 0; i < function.outputs.length; i++) {

            float width = Gui.getStringWidth(function.outputs[i].name);

            if (width > max) {
                max = width;
            }
        }
        calcWidth = max + 15;
    }

    void drawOnScreen(float mouseX, float mouseY) {

        if (dragging) {
            x += (mouseX - dragX);
            y += (mouseY - dragY);
            dragX = mouseX;
            dragY = mouseY;

        }

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);


        int leftOffset = 5;
        if (function instanceof Execute) {
            int colorL = 0xFF384247;
            int colorR = 0xFF384247;

            Execute execute = (Execute) function;
            if (execute.nextConnection != null)
                if(!TheManager.functions.contains(function)){

                }
                for (int i = 0; i < execute.size; i++) {
                    Execute next = execute.nextConnection[i];
                    if (next == null) {
                        continue;
                    }

                    float height = (headerHeight / execute.size);
                    float h = i * height;

                    float startY = y + h + height / 2;
                    float startX = x + getWidth();

                    float endY = next.drawFunction.y + headerHeight / 2;
                    float endX = next.drawFunction.x;

                    float dX = startX - endX;
                    float dY = startY - endY;
                    double distance = Math.sqrt(dX * dX + dY * dY);

                    GL11.glTranslated(-x, -y, 0);

                    Gui.besier(startX, startY, endX, endY, 0xFF505050, 0, GL11.GL_LINE_STRIP, true,
                            6f * TabViewport.zoom, 0xFF505050, 1, 4, 1);
                    Gui.besier(startX, startY, endX, endY, 0xFF8341FF, 0, GL11.GL_LINE_STRIP, true,
                            3f * TabViewport.zoom, 0xFF8341FF, 1, 4, 1);


                    GL11.glTranslated(x, y, 0);
                }
        }
        try {
            for (int i = 0; i < function.inputTypes.length; i++) {
                if (function.inputs[i] != null) {

                    Rect l = bodies.get(function.outputs.length + i);
                    Vector2f start = l.getTotalPosition();
                    start = new Vector2f(start.x, start.y + l.height / 2);


                    DrawFunction draw = function.inputs[i].function.drawFunction;
                    int index = function.inputs[i].connectionIndex;
                    // Vector2f end = new Vector2f(draw.x + getWidth() / 2,draw.y + headerHeight +
                    // function.inputs[i].connectionIndex * IOHeight);

                    // end.x += -x + getWidth() / 2;
                    // end.y += -y + IOHeight / 2;

                    // System.out.println(draw. .size());

                    Rect r = draw.bodies.get(index);
                    Vector3f f = new Vector3f(r.x, r.y, 0);

                    f.add(draw.x, 0, 0);
                    f.add(r.width, 0, 0);
                    f.add(0, draw.y, 0);
                    f.add(0, headerHeight, 0);
                    f.add(0, r.height / 2, 0);
                    f.add(-x, 0, 0);
                    f.add(0, -y, 0);

					/*
					f.x += draw.x;
					f.x += r.width;
					f.y += draw.y;
					f.y += headerHeight;
					f.y += r.height / 2;
					f.x -= x;
					f.y -= y;
					*/

                    float dX = start.x - f.getX();
                    float dY = start.y - f.getY();
                    double distance = Math.sqrt(dX * dX + dY * dY);

                    if (distance > 25) {
                        Gui.besier(start.x, start.y, f.getX(), f.getY(), 0xFF505050, 1, GL11.GL_LINE_STRIP, true,
                                3f * TabViewport.zoom, function.inputTypes[i].getColor(),
                                (int) (10 - (System.currentTimeMillis() / 50) % 10), 10, 0);

                    } else {
                        Gui.drawLine(start.x, start.y, f.getX(), f.getY(), 0xFF505050, 3f * TabViewport.zoom);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        head.draw(mouseX - x, mouseY - y);

        rights.forEach(rect -> rect.draw(mouseX - x, mouseY - y));

        if (leftConnection != null) {
            leftConnection.draw(mouseX - x, mouseY - y);
        }

        bodies.forEach(rect -> rect.draw(mouseX - x, mouseY - y));

        if (function.hasCalculated) {
            Gui.drawRect(10, 0, this.getWidth() - 10, 2, -1);
        }

        GL11.glPopMatrix();
    }

    float getWidth() {

        return calcWidth;
    }

    public void init() {

        calcWidth();
        int mainColor = ColorLib.getVisualScriptMainColor();
        head = new Rect(0, 0, getWidth(), headerHeight, mainColor, ColorLib.getBrighter(mainColor));

        boolean hasOuts = function.outputs.length > 0;
        boolean hasIns = function.inputTypes.length > 0;

        boolean full = hasOuts && hasIns;

        float otherWidth = getWidth();

        float xStart = 0;

        if (full) {
            xStart = (otherWidth / 2);
            otherWidth /= 2;
        }

        if (function.customData != null) {
            head.setTxt(function.customData.toString(), null);
            head.setHoverTxt(function.name, -1);
            if (function instanceof GetGlobalVariable) {
                head.setCustomVector(new Vector3i(1, 0, 0));
            }

        } else {
            head.setTxt(function.name, null);
        }

        for (int i = 0; i < function.outputs.length; i++) {

            Rect rect;
            bodies.add(rect = new Rect(xStart - 1, i * (IOHeight - 1) - 1, otherWidth - 1, IOHeight, ColorLib.getDarker(mainColor),
                    mainColor, head, Rect.Alignment.DOWN));
            rect.setCustomVector(new Vector3i(1, i, 0));

            rect.setHoverTxt(function.outputs[i].type.name(), function.outputs[i].type.getColor());
            rect.setTxt(function.outputs[i].name, Rect.Alignment.RIGHT);

        }

        for (int i = 0; i < function.inputTypes.length; i++) {

            Rect rect;
            bodies.add(rect = new Rect(0, i * (IOHeight - 1) - 1, otherWidth - 1, IOHeight, ColorLib.getDarker(mainColor), mainColor,
                    head, Rect.Alignment.DOWN));
            rect.setCustomVector(new Vector3i(-1, i, 0));

            if (function.names != null) {
                rect.setHoverTxt(function.inputTypes[i].name(), function.inputTypes[i].getColor());
                rect.setTxt(function.names[i], Rect.Alignment.LEFT);
            } else {
                rect.setTxt(function.inputTypes[i].name(), Rect.Alignment.LEFT);
                rect.hoverTxtColor = function.inputTypes[i].getColor();
            }

        }

        if (function instanceof Execute) {
            Execute execute = (Execute) function;
            float height = headerHeight / execute.size;

            for (int i = 0; i < execute.size; i++) {

                float h = i * height;

                Rect rectRight;
                rights.add(rectRight = new Rect(-connectionButtonWidth, h, connectionButtonWidth, height, ColorLib.cyan(),
                        ColorLib.getDarker(ColorLib.cyan()), head, Rect.Alignment.RIGHT));
                rectRight.setCustomVector(new Vector3i(i, 0, 0));
                if (!(function instanceof Event)) {
                    leftConnection = new Rect(connectionButtonWidth, 0, connectionButtonWidth, headerHeight, ColorLib.cyan(),
                            ColorLib.getDarker(ColorLib.cyan()), head, Rect.Alignment.LEFT);
                }
            }
        }

    }

    boolean mouseClicked(Canvas tab, int i, float mouseX, float mouseY, int mouseButton) {

        if (mouseButton != 0) {
            BlueprintManager.draggedType = 0;
            return false;
        }

        try {
            if (function instanceof Execute) {
                Execute execute = (Execute) function;
                for (Rect rect : rights) {
                    if (rect.mouseInside(mouseX - x, mouseY - y, mouseButton)) {
                        if (!KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                            if (BlueprintManager.draggedType == 0) {
                                BlueprintManager.dragTab = i;
                                BlueprintManager.dragIndex = rect.custom.getX();
                                BlueprintManager.draggedType = -1;
                            }
                        } else {
                            Execute cast = (Execute) function;
                            execute.nextConnection[rect.custom.getX()] = null;
                        }
                        return true;
                    }
                }

                if (leftConnection != null && BlueprintManager.draggedType == -1) {
                    if (leftConnection.mouseInside(mouseX - x, mouseY - y, mouseButton)) {

                        Module mod = TheEditor.activeMod;
                        if (mod != null) {
                            Canvas tab1 = mod.getTab();
                            if (tab1 != null) {
                                Function nextFunction = tab1.getDragFunction();
                                int i2 = BlueprintManager.dragIndex;

                                Execute cast = (Execute) nextFunction;

                                cast.nextConnection[i2] = execute;
                                BlueprintManager.draggedType = 0;
                            }
                        }
                    }
                }
            }

            if (head.mouseInside(mouseX - x, mouseY - y, mouseButton)) {
                if (KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE) && tab != null) {
                    tab.removeFunction(this);//buggy
                } else {
                    dragging = true;
                    dragX = mouseX;
                    dragY = mouseY;
                }
                return true;
            }

            for (Rect rect : bodies) {
                if (rect.mouseInside(mouseX - x, mouseY - y, mouseButton)) {
                    int side = rect.custom.getX();
                    int index = rect.custom.getY();

                    if (KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                        function.inputs[index] = null;
                        break;
                    }

                    if (BlueprintManager.draggedType == 1 && side == -1) {
                        Module mod = TheEditor.activeMod;
                        if (mod != null) {
                            Canvas tab1 = mod.getTab();
                            if (tab1 != null) {
                                Function nextFunction = tab1.getDragFunction();
                                int i2 = BlueprintManager.dragIndex;
                                if (function.inputTypes[index].canConnect(nextFunction.outputs[i2].type)) {
                                    function.inputs[index] = new Connection(nextFunction, i2, false);
                                    BlueprintManager.draggedType = 0;
                                }
                            }
                        }
                    } else if (BlueprintManager.draggedType == 0 && side == 1) {
                        BlueprintManager.dragTab = i;
                        BlueprintManager.dragIndex = index;
                        BlueprintManager.draggedType = 1;
                        return true;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // BlueprintManager.draggedType = 0;
        return false;
    }

    void mouseReleased(float mouseX, float mouseY, int state) {
        if (state != 0)
            return;
        dragging = false;
        dragX = 0;
        dragY = 0;
    }

}
