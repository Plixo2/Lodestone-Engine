package net.plixo.paper.client.tabs;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.paper.client.ui.elements.*;
import net.plixo.paper.client.util.*;
import net.plixo.paper.client.visualscript.CursorObject;
import net.plixo.paper.client.visualscript.UIFunction;
import net.plixo.paper.client.visualscript.Function;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.manager.FunctionManager;
import net.plixo.paper.client.ui.GUI.GUIEditor;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.UITab;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.io.File;

public class TabViewport extends UITab {


    public float x = 0, y = 0;
    public float zoom = 1;
    boolean dragging = false;
    float dragX = 0, dragY = 0;
    float startMouseX = 0,startMouseY = 0;

    public TabViewport(int id) {
        super(id, "Viewport");
        EditorManager.viewport = this;
    }

    public void load(File file) {
        AssetLoader.setCurrentScript(null);
        AssetLoader.setCurrentScript( FunctionManager.loadFromFile(file));
        init();
    }



    @Override
    public void init() {
        canvas = new UICanvas();
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(0);


        if(AssetLoader.getLoadedScript() != null) {
            for (Function function : AssetLoader.getLoadedScript().getFunctions()) {
                canvas.add(function.ui);
            }
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        if(AssetLoader.getLoadedScript() == null) {
            return;
        }
        try {
            if (dragging) {
                x += (mouseX - dragX);
                y += (mouseY - dragY);
                dragX = mouseX;
                dragY = mouseY;
            }

            if (parent.isMouseInside(mouseX, mouseY)) {
                mouse(mouseX, mouseY);
            }

            GL11.glPushMatrix();
            GL11.glTranslated(x, y, 0);
            GL11.glScaled(zoom, zoom, 1);
            drawLines();
            Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
            super.drawScreen(mouseToWorld.x, mouseToWorld.y);
            CursorObject draggedObj = getDraggedObj();
            if (draggedObj.isLink()) {
                Function function = draggedObj.getLink();
                float xE = mouseToWorld.x;
                float yE = mouseToWorld.y;
                float xI = function.ui.x + function.ui.width - 6;
                float yI = function.ui.y + 20 + (draggedObj.id * 12) + 6;

                float reach = 30;
                Vector2f start = new Vector2f(xI + 12, yI);
                Vector2f startR = new Vector2f(xI + reach, yI);
                Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                Vector2f endL = new Vector2f(xE - reach, yE);
                Vector2f end = new Vector2f(xE - 12, yE);
                Gui.drawCircle(start.x, start.y, 2, ColorLib.getMainColor());
                Gui.drawCircle(end.x, end.y, 2, ColorLib.getMainColor());
                Gui.Bezier(ColorLib.getDarker(ColorLib.getMainColor()), 3, start, startR, mid, endL, end);
            }
            if (draggedObj.isOutput()) {
                Function function = draggedObj.getLink();
                UIFunction UIFunction = function.ui;
                UIElement element = UIFunction.outputList.get(function.output[draggedObj.id]);
                if (element == null) {
                    Util.print("Something went wrong");
                    return;
                }
                float xE = element.x + element.width / 2;
                float yE = element.y + element.height / 2;
                xE += UIFunction.x;
                xE += UIFunction.width - 12;
                yE += UIFunction.y;
                yE += 20;

                float xI = mouseToWorld.x;
                float yI = mouseToWorld.y;

                float reach = 30;
                Vector2f start = new Vector2f(xI - 12, yI);
                Vector2f startR = new Vector2f(xI - reach, yI);
                Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                Vector2f endL = new Vector2f(xE + reach, yE);
                Vector2f end = new Vector2f(xE + 12, yE);

                Gui.drawCircle(start.x, start.y, 2, 0xAACCDDEE);
                Gui.drawCircle(end.x, end.y, 2, 0xAACCDDEE);
                Gui.Bezier(0x99CCDDEE, 3, start, startR, mid, endL, end);
            }
            GL11.glPopMatrix();

            String name = AssetLoader.getLoadedScript().name;
            Gui.drawString(name,parent.width-Gui.getStringWidth(name)-9,parent.height-29,0xFF444444);
            Gui.drawString(name,parent.width-Gui.getStringWidth(name)-10,parent.height-30,-1);

            name = AssetLoader.getLoadedScript().location.getName();
            Gui.drawString(name,parent.width-Gui.getStringWidth(name)-10,parent.height-20,-1);

            name = AssetLoader.getLoadedScript().getFunctions().size() + " Nodes";
            Gui.drawString(name,parent.width-Gui.getStringWidth(name)-10,parent.height-10,-1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if(!parent.isMouseInside(mouseX,mouseY)) {
            return;
        }
        if(AssetLoader.getLoadedScript() == null) {
            return;
        }

        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        for (UIElement element : canvas.elements) {
            if(element instanceof UIDraggable)
            ((UIDraggable) element).beginSelect(mouseToWorld.x - canvas.x, mouseToWorld.y - canvas.y, mouseButton);
        }
        for (UIElement element : canvas.elements) {
            element.mouseClicked(mouseToWorld.x - canvas.x, mouseToWorld.y - canvas.y, mouseButton);
            if (element.hovered(mouseToWorld.x - canvas.x, mouseToWorld.y - canvas.y)) {
                return;
            }
        }

        if (mouseButton == 1) {
            startMouseX = mouseX;
            startMouseY = mouseY;
            dragging = true;
            dragX = mouseX;
            dragY = mouseY;
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        if(AssetLoader.getLoadedScript() == null) {
            return;
        }
        if (state == 1) {
            if(parent.isMouseInside(mouseX,mouseY) && Math.abs(mouseX-startMouseX) < 5 && Math.abs(mouseY-startMouseY) < 5) {
                Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
                GUIEditor.instance.beginMenu();
                for (int i = 0; i < FunctionManager.functions.size(); i++) {
                    Function function = FunctionManager.functions.get(i);
                    GUIEditor.instance.addMenuOption(function.getName() , () -> {
                        if(AssetLoader.getLoadedScript() != null) {
                            Function Function = FunctionManager.getInstanceByName(function.getName());
                            if(Function != null) {
                                AssetLoader.getLoadedScript().addFunction(Function,mouseToWorld.x,mouseToWorld.y);
                                canvas.add(Function.ui);
                            }
                        }
                    });
                }
                GUIEditor.instance.showMenu();
            }

            dragging = false;
            dragX = 0;
            dragY = 0;
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        super.mouseReleased(mouseToWorld.x, mouseToWorld.y, state);
        setDraggedObj(null);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        if(key == GLFW.GLFW_KEY_DELETE) {
            for (UIElement element : canvas.elements) {
                if(element instanceof UIFunction) {
                    UIFunction UIFunction = ((UIFunction) element);
                    if (UIFunction.isSelected) {
                        canvas.elements.remove(element);
                        AssetLoader.getLoadedScript().removeFunction(UIFunction.function);
                    }
                }
            }
        }
        super.keyPressed(key, scanCode, action);
    }

    void mouse(float mouseX, float mouseY) {
        float dir = Math.signum(MouseUtil.getDWheel());
        if (dir != 0) {
            Vector2f pre = screenToWorld(mouseX, mouseY);
            zoom *= 1 + (0.1f * dir);
            zoom = Math.min(Math.max(zoom, 0.1f), 3);
            Vector2f post = screenToWorld(mouseX, mouseY);
            Vector2f diff = new Vector2f(pre.x - post.x, pre.y - post.y);
            x -= diff.x * zoom;
            y -= diff.y * zoom;
        }
    }

    void drawLines() {
        int i = 50;
        Vector2f top = screenToWorld(0, 0);
        Vector2f bottom = screenToWorld(parent.width, parent.height);
        float x = top.x;
        float mX = x % i;
        float y = top.y;
        float mY = y % i;
        int color = 0x6F000000;
        for (double j = top.x; j <= bottom.x; j += i) {
            double off = j - mX;
            Gui.drawLine(off, top.y, off, bottom.y, color, 1);
        }
        for (double j = top.y; j <= bottom.y; j += i) {
            double off = j - mY;
            Gui.drawLine(top.x, off, bottom.x, off, color, 1);
        }
    }

    Vector2f screenToWorld(float x, float y) {

        x -= this.x;
        y -= this.y;

        x /= zoom;
        y /= zoom;

        return new Vector2f(x, y);
    }

    Vector2f worldToScreen(float x, float y) {

        x *= zoom;
        y *= zoom;

        x += this.x;
        y += this.y;

        return new Vector2f(x, y);
    }

    private CursorObject draggedObj;

    public CursorObject getDraggedObj() {
        return draggedObj == null ? CursorObject.none : draggedObj;
    }

    public void setDraggedObj(CursorObject object) {
        this.draggedObj = object;
    }

    public void setDraggedObj(int id, CursorObject.DraggedType type, Object object) {
        this.draggedObj = new CursorObject(id, type, object);
    }

}
