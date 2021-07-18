package net.plixo.paper.client.ui.tabs.viewport;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.manager.FunctionManager;
import net.plixo.paper.client.ui.GUI.GUIMain;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.canvas.UIDraggable;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import net.plixo.paper.client.util.Util;
import net.plixo.paper.client.visualscript.Function;
import net.plixo.paper.client.visualscript.FunctionCursorHolder;
import net.plixo.paper.client.visualscript.UIFunction;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.io.File;

public class UIViewport extends UICanvas {

    public static float planeX = 0, planeY = 0;
    public static float zoom = 1;
    boolean dragging = false;
    float dragX = 0, dragY = 0;
    float startMouseX = 0, startMouseY = 0;

    public UIViewport() {
        this.displayName = "Viewport";
        EditorManager.viewport = this;
    }

    public void load(File file) {
        AssetLoader.setCurrentScript(null);
        AssetLoader.setCurrentScript(FunctionManager.loadFromFile(file));
        init();
    }


    @Override
    public void init() {
        clear();

        if (AssetLoader.getLoadedScript() != null) {
            for (Function function : AssetLoader.getLoadedScript().getFunctions()) {
                add(function.ui);
            }
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        this.color = 0;
        if (AssetLoader.getLoadedScript() == null) {
            return;
        }
        try {
            if (dragging) {
                planeX += (mouseX - dragX);
                planeY += (mouseY - dragY);
                dragX = mouseX;
                dragY = mouseY;
            }

            if (hovered(mouseX, mouseY)) {
                mouse(mouseX, mouseY);
            }

            GL11.glPushMatrix();
            GL11.glTranslated(planeX, planeY, 0);
            GL11.glScaled(zoom, zoom, 1);
            drawLines();
            Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
            super.drawScreen(mouseToWorld.x, mouseToWorld.y);
            FunctionCursorHolder cursorObject = GUIMain.instance.cursorObject.getAs(FunctionCursorHolder.class);
            if (cursorObject != null) {
                if (cursorObject.isLink()) {
                    Function function = cursorObject.getLink();
                    float xE = mouseToWorld.x;
                    float yE = mouseToWorld.y;
                    float xI = function.ui.x + function.ui.width - 6;
                    float yI = function.ui.y + 20 + (cursorObject.id * 12) + 6;

                    float reach = 30;
                    Vector2f start = new Vector2f(xI + 12, yI);
                    Vector2f startR = new Vector2f(xI + reach, yI);
                    Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                    Vector2f endL = new Vector2f(xE - reach, yE);
                    Vector2f end = new Vector2f(xE, yE);
                    Gui.drawCircle(start.x, start.y, 2, ColorLib.getMainColor());
                    Gui.drawCircle(end.x, end.y, 2, ColorLib.getMainColor());
                    Gui.Bezier(ColorLib.getDarker(ColorLib.getMainColor()), 3, start, startR, mid, endL, end);
                } else if (cursorObject.isOutput()) {
                    Function function = cursorObject.getLink();
                    UIFunction UIFunction = function.ui;
                    UIElement element = UIFunction.outputList.get(function.output[cursorObject.id]);
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
                    Vector2f start = new Vector2f(xI, yI);
                    Vector2f startR = new Vector2f(xI - reach, yI);
                    Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                    Vector2f endL = new Vector2f(xE + reach, yE);
                    Vector2f end = new Vector2f(xE + 12, yE);

                    Gui.drawCircle(start.x, start.y, 2, 0xAACCDDEE);
                    Gui.drawCircle(end.x, end.y, 2, 0xAACCDDEE);
                    Gui.Bezier(0x99CCDDEE, 3, start, startR, mid, endL, end);
                }
            }
            GL11.glPopMatrix();

            String name = AssetLoader.getLoadedScript().name;
            Gui.drawString(name, width - Gui.getStringWidth(name) - 9, height - 29, 0xFF444444);
            Gui.drawString(name, width - Gui.getStringWidth(name) - 10, height - 30, -1);

            name = AssetLoader.getLoadedScript().location.getName();
            Gui.drawString(name, width - Gui.getStringWidth(name) - 10, height - 20, -1);

            name = AssetLoader.getLoadedScript().getFunctions().size() + " Nodes";
            Gui.drawString(name, width - Gui.getStringWidth(name) - 10, height - 10, -1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (!hovered(mouseX, mouseY)) {
            return;
        }
        if (AssetLoader.getLoadedScript() == null) {
            return;
        }

        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        for (UIElement element : elements) {
            if (element instanceof UIDraggable)
                ((UIDraggable) element).beginSelect(mouseToWorld.x, mouseToWorld.y, mouseButton);
        }
        for (UIElement element : elements) {
            element.mouseClicked(mouseToWorld.x, mouseToWorld.y, mouseButton);

            if (element.hovered(mouseToWorld.x, mouseToWorld.y)) {
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
        if (AssetLoader.getLoadedScript() == null) {
            return;
        }
        if (state == 1) {
            if (hovered(mouseX, mouseY) && Math.abs(mouseX - startMouseX) < 5 && Math.abs(mouseY - startMouseY) < 5) {
                Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
                GUIMain.instance.beginMenu();
                for (Function function : FunctionManager.functions) {
                    GUIMain.instance.addMenuOption(function.getName(), () -> {
                        if (AssetLoader.getLoadedScript() != null) {
                            Function addFunction = FunctionManager.getInstanceByName(function.getName());
                            if (addFunction != null) {
                                AssetLoader.getLoadedScript().addFunction(addFunction, mouseToWorld.x, mouseToWorld.y);
                                add(addFunction.ui);
                            }
                        }
                    });
                }
                GUIMain.instance.showMenu();
            }
            dragging = false;
            dragX = 0;
            dragY = 0;
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        super.mouseReleased(mouseToWorld.x, mouseToWorld.y, state);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        if (key == GLFW.GLFW_KEY_DELETE) {
            for (UIElement element : elements) {
                if (element instanceof UIFunction) {
                    UIFunction UIFunction = ((UIFunction) element);
                    if (UIFunction.isSelected) {
                        elements.remove(element);
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
            planeX -= diff.x * zoom;
            planeY -= diff.y * zoom;
        }
    }

    void drawLines() {
        int i = 50;
        Vector2f top = screenToWorld(-i, -i);
        Vector2f bottom = screenToWorld(width + i, height + i);
        float x = top.x;
        float mX = x % i;
        float y = top.y;
        float mY = y % i;
        int color = 0x6F000000;
        // color = 0x6F000000;
        for (float j = top.x; j <= bottom.x; j += i) {
            float off = j - mX;
            Gui.drawLine(off, top.y, off, bottom.y, color, 1);
        }
        for (float j = top.y; j <= bottom.y; j += i) {
            float off = j - mY;
            Gui.drawLine(top.x, off, bottom.x, off, color, 1);
        }
    }

    Vector2f screenToWorld(float x, float y) {

        x -= this.planeX;
        y -= this.planeY;

        x /= zoom;
        y /= zoom;

        return new Vector2f(x, y);
    }

    Vector2f worldToScreen(float x, float y) {

        x *= zoom;
        y *= zoom;

        x += this.planeX;
        y += this.planeY;

        return new Vector2f(x, y);
    }


}

