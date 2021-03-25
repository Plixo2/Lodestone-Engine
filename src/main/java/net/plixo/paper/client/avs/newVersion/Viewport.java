package net.plixo.paper.client.avs.newVersion;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.paper.client.manager.TheEditor;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.MouseUtil;
import net.plixo.paper.client.util.Util;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Viewport extends UITab {

    ArrayList<nFunction> functions = new ArrayList<>();

    public float x = 0, y = 0;
    public float zoom = 1;
    boolean dragging = false;
    float dragX = 0, dragY = 0;

    public Viewport(int id) {
        super(id, "Viewport");
        TheEditor.viewport = this;
    }

    @Override
    public void init() {
        canvas = new UICanvas(0);
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(0);

        try {
            functions.add(new Jump());
            functions.add(new Event());
            functions.add(new If());
            functions.add(new getGround());

            for (nFunction function : functions) {
                function.set();
                nUIFunction f = new nUIFunction(function);
                f.setDimensions(0, 0, 120, 20);
                canvas.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTick() {
        try {
            for (nFunction function : functions) {
                if (function instanceof Event) {
                    function.run();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Util.print(e.getMessage());
        }

        super.onTick();
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
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
                nFunction function = draggedObj.getLink();
                float xE = mouseX;
                float yE = mouseY;
                float xI = function.ui.x + function.ui.width - 6;
                float yI = function.ui.y + 20 + (draggedObj.id * 12) + 6;

                float reach = 30;
                Vector2f start = new Vector2f(xI + 12, yI);
                Vector2f startR = new Vector2f(xI + reach, yI);
                Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                Vector2f endL = new Vector2f(xE - reach, yE);
                Vector2f end = new Vector2f(xE - 12, yE);
                Gui.drawCircle(start.x, start.y, 2, ColorLib.orange());
                Gui.drawCircle(end.x, end.y, 2, ColorLib.orange());
                Gui.Bezier(ColorLib.getDarker(ColorLib.orange()), 3, start, startR, mid, endL, end);
            }
            if (draggedObj.isOutput()) {
                nFunction function = draggedObj.getLink();
                nUIFunction nUIFunction = function.ui;
                UIElement element = nUIFunction.outputList.get(function.output[draggedObj.id]);
                if (element == null) {
                    Util.print("Something went wrong");
                    return;
                }
                float xE = element.x + element.width / 2;
                float yE = element.y + element.height / 2;
                xE += nUIFunction.x;
                xE += nUIFunction.width - 12;
                yE += nUIFunction.y;
                yE += 20;

                float xI = mouseX;
                float yI = mouseY;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        hideMenu();
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        for (UIElement element : canvas.elements) {
            element.mouseClicked(mouseToWorld.x - canvas.x, mouseToWorld.y - canvas.y, mouseButton);
            if (element.hovered(mouseToWorld.x - canvas.x, mouseToWorld.y - canvas.y)) {
                return;
            }
        }
        if (mouseButton == 1) {
            dragging = true;
            dragX = mouseX;
            dragY = mouseY;
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        if (state == 1) {
            dragging = false;
            dragX = 0;
            dragY = 0;
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        super.mouseReleased(mouseToWorld.x, mouseToWorld.y, state);
        setDraggedObj(null);
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

