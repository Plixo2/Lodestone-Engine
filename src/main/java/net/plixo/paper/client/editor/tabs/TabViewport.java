package net.plixo.paper.client.editor.tabs;


import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.Paper;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.UIButton;
import net.plixo.paper.client.UI.elements.UIToggleButton;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.blueprint.Canvas;
import net.plixo.paper.client.editor.blueprint.Rect;
import net.plixo.paper.client.engine.buildIn.blueprint.BlueprintManager;
import net.plixo.paper.client.engine.buildIn.blueprint.Module;
import net.plixo.paper.client.engine.buildIn.blueprint.function.Function;
import net.plixo.paper.client.util.*;
import org.lwjgl.opengl.GL11;

public class TabViewport extends UITab {

    public static float x = 0, y = 0;
    public static float zoom = 1;


    boolean dragedSlider = false;
    boolean dragging = false;
    float dragX = 0, dragY = 0;


    Rect resetRect;

    //   Rect startEngine;

    UIButton uiToggleButton;

    public float startX, startY;

    public TabViewport(int id) {
        super(id, "Viewport");
        TheEditor.viewport = this;
    }


    @Override
    public void init() {
        zoom = 1;
        x = parent.width / 2;
        y = parent.height / 2;
        resetRect = new Rect(0, 0, 30, 30, -1, -1);


        uiToggleButton = new UIButton(0) {
            @Override
            public void actionPerformed() {
                if (Paper.paperEngine.isRunning) {
                    setDisplayName("Start");
                    setColor(ColorLib.getDarker(ColorLib.getButtonColor()));
                    Paper.paperEngine.stopEngine();
                } else {
                    setDisplayName("Stop");
                    setColor(ColorLib.getOffButtonColor());
                    Paper.paperEngine.startEngine();
                }
                super.actionPerformed();
            }
        };
        uiToggleButton.setDimensions(parent.width / 2 - 25, 0, 50, 15);
        uiToggleButton.setDisplayName("Start");

        if (Paper.paperEngine.isRunning) {
            uiToggleButton.setColor(ColorLib.getOffButtonColor());
            uiToggleButton.setDisplayName("Stop");
        }

        //uiToggleButton.setState(true);


        super.init();
    }


    @Override
    public void drawScreen(float mouseX, float mouseY) {
        Gui.drawRect(0, 0, parent.width, parent.height, ColorLib.getBackground());

        if (dragging) {
            x += (mouseX - dragX);
            y += (mouseY - dragY);
            dragX = mouseX;
            dragY = mouseY;
        }

        Module mod = TheEditor.activeMod;
        if (mod != null) {
            Canvas tab = mod.getTab();
            if (tab != null) {


                GL11.glPushMatrix();
                GL11.glTranslated(x, y, 0);
                GL11.glScaled(zoom, zoom, 1);
                drawLines();

                if (parent.isMouseInside(mouseX, mouseY)) {
                    mouse(mouseX, mouseY);
                }

                Vector3d mouseToWorld = screenToWorld(mouseX, mouseY);

                tab.drawScreen((float) mouseToWorld.x, (float) mouseToWorld.y);

                GL11.glPopMatrix();

                int toH = Math.round((zoom) * 100.0f);
                Gui.drawStringwithShadow("Zoom: " + toH + "%", parent.width - 60, 10, -1);

                GL11.glPushMatrix();
                float nameX = parent.width - Gui.getStringWidth("§l" + mod.name + "." + SaveUtil.FileFormat.VisualScript.format) * 2 - 20;
                GL11.glTranslated(nameX, parent.height - 20, 0);
                GL11.glScaled(2, 2, 0);
                Gui.drawString("§l" + mod.name + "." + SaveUtil.FileFormat.VisualScript.format, 0, 0, 0xFF000000);
                Gui.drawString("§l" + mod.name + "." + SaveUtil.FileFormat.VisualScript.format, -1, -1, ColorLib.getTextColor());
                GL11.glPopMatrix();

                drawPoint();

                float z = (zoom - 0.2f) / (2 - 0.2f);
                float yS = 15 + (z * (parent.height - 30));
                Gui.drawRect(3, 1, 4, parent.height - 1, -1);
                Gui.drawRect(2, yS - 15, 5, yS + 15, ColorLib.getMainColor());

                if (dragedSlider) {
                    float px = parent.width / 2;
                    float py = parent.height / 2;
                    Vector3d pre = screenToWorld(px, py);
                    float p = (mouseY - 15) / (parent.height - 30);
                    zoom = 0.2f + (p * (2 - 0.2f));
                    zoom = Math.min(Math.max(zoom, 0.2f), 2);
                    Vector3d post = screenToWorld(px, py);
                    Vector3d diff = pre.subtract(post);
                    x -= diff.x * zoom;
                    y -= diff.y * zoom;
                }

            }
        }

        uiToggleButton.draw(mouseX, mouseY);
        drawOutline();
    }


    void drawLines() {

        int i = 50;

        int toI = ((int) (zoom * 50.0f));
        if (toI > 0) {
            float w = 10 / toI;
            if (w > 0)
                i *= w;
        }

        Vector3d top = screenToWorld(0, 0);
        Vector3d bottom = screenToWorld(parent.width, parent.height);

        float x = (float) top.x;
        float mX = x % i;

        float y = (float) top.y;
        float mY = y % i;

        int color = ColorLib.utilLines();

        for (double j = top.x; j <= bottom.x; j += i) {
            double off = j - mX;
            Gui.drawLine(off, top.y, off, bottom.y, color, 1);
        }
        for (double j = top.y; j <= bottom.y; j += i) {
            double off = j - mY;

            Gui.drawLine(top.x, off, bottom.x, off, color, 1);
        }

    }

    void drawPoint() {

        Module mod = TheEditor.activeMod;
        if (mod != null) {
            Canvas tab = mod.getTab();
            if (tab != null) {

                float pX = 0, pY = 0;
                Vector3d screen = worldToScreen(pX, pY);

                Vector3d minBounds = new Vector3d(7, 7, 1);
                Vector3d maxBounds = new Vector3d(parent.width - 7, parent.height - 7, 1);
                resetRect.id = -1;

                if (screen.x > maxBounds.x || screen.x < minBounds.x
                        || screen.y > maxBounds.y || screen.y < minBounds.y) {

                    Vector3d atBorder = new Vector3d(MathHelper.clamp(screen.x, minBounds.x, maxBounds.x),
                            MathHelper.clamp(screen.y, minBounds.y, maxBounds.y), 1);
                    double degToTarget = Math
                            .toDegrees(Math.atan2(screen.x - atBorder.x, screen.y - atBorder.y));

                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y, 0);

                    GL11.glTranslated(atBorder.x - (this.x), atBorder.y - (this.y), 0);

                    GL11.glRotated(-degToTarget, 0, 0, 1);

                    int size = 6;
                    Gui.drawLine(0, 0, -size, -size, -1, 3);
                    Gui.drawLine(0, 0, size, -size, -1, 3);
                    Gui.drawLine(0, 0.5f, 0, -size * 2.5f, -1, 3);
                    Gui.drawLine(0, 0.7f, 0, -size * 2.5f, -1, 2);

                    resetRect.x = (float) (atBorder.x - 15);
                    resetRect.y = (float) (atBorder.y - 15);
                    resetRect.id = 1;

                    GL11.glPopMatrix();

                }

                // Gui.drawCircle(screen.xCoord, screen.yCoord, 3, ColorUtil.getMainColor());
            }
        }

        Vector3d nullPoint = worldToScreen(0, 0);
        float Nx = (float) nullPoint.x;
        float Ny = (float) nullPoint.y;
        float w = 25 * zoom;
        Gui.drawLine(Nx, Ny, Nx + w, Ny, ColorLib.getMainColor(), 3 * zoom);
        Gui.drawLine(Nx, Ny, Nx, Ny + w, ColorLib.getMainColor(), 3 * zoom);

    }


    void mouse(float mouseX, float mouseY) {

        float dir = Math.signum(MouseUtil.getDWheel());

        if (dir != 0) {
            Vector3d pre = screenToWorld(mouseX, mouseY);
            float pZoom = zoom;
            zoom *= 1 + (0.1f * dir);
            zoom = Math.min(Math.max(zoom, 0.2f), 2);
            Vector3d post = screenToWorld(mouseX, mouseY);
            Vector3d diff = pre.subtract(post);
            x -= diff.x * zoom;
            y -= diff.y * zoom;
        }

    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        hideMenu();

        uiToggleButton.mouseClicked(mouseX, mouseY, mouseButton);
        if (!parent.isMouseInside(mouseX, mouseY)) {
            return;
        }

        if (mouseButton == 1) {
            startX = mouseX;
            startY = mouseY;
        }

        if (resetRect.id == 1 && resetRect.mouseInside(mouseX, mouseY, -1)) {

            Module mod = TheEditor.activeMod;
            if (mod != null) {
                Canvas tab =
                        mod.getTab();
                if (tab != null) {
                    zoom = 1;
                    x = parent.width / 2;
                    y =
                            parent.height / 2;
                }
            }

            return;
        }


        if (mouseButton == 0 && mouseX < 7) {
            System.out.println("sliderDrag");
            dragedSlider = true;
            return;
        }

        if (mouseButton == 1) {
            dragging = true;
            dragX = mouseX;
            dragY = mouseY;
        }

        Module mod = TheEditor.activeMod;
        if (mod != null) {
            Canvas tab = mod.getTab();
            if (tab != null) {
                Vector3d mouseToWorld = screenToWorld(mouseX, mouseY);
                tab.mouseClicked((float) mouseToWorld.x, (float) mouseToWorld.y, mouseButton);
            }
        }


    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {


        if (state == 0) {
            dragedSlider = false;
        }
        if (state == 1) {
            dragging = false;
            dragX = 0;
            dragY = 0;
        }

        Module mod = TheEditor.activeMod;
        if (mod != null) {
            Canvas tab = mod.getTab();
            if (tab != null) {


                if (state == 1 && parent.isMouseInside(mouseX, mouseY)) {
                    float dx = startX - mouseX;
                    float dy = startY - mouseY;
                    if (dx * dx + dy * dy < 2) {
                        String[] nameList = new String[BlueprintManager.allFunctions.size()];
                        for (int i = 0; i < nameList.length; i++) {
                            nameList[i] = BlueprintManager.allFunctions.get(i).name;
                        }
                        showMenu(0, mouseX, mouseY, nameList);
                    }
                }

                Vector3d mouseToWorld = screenToWorld(mouseX, mouseY);
                tab.mouseReleased((float) mouseToWorld.x, (float) mouseToWorld.y, state);
            }
        }


    }

    @Override
    public void optionsSelected(int id, int option) {

        Module mod = TheEditor.activeMod;
        if (mod != null) {
            Canvas tab = mod.getTab();
            if (tab != null) {
                Function func = BlueprintManager.getFromList(BlueprintManager.allFunctions.get(option).name, "");
                if (func != null) {
                    Vector3d toWorld = TheEditor.viewport.screenToWorld(TheEditor.viewport.parent.width / 2,
                            TheEditor.viewport.parent.height / 2);

                    tab.addFunction(func, (float) toWorld.x, (float) toWorld.y);
                } else {
                    Util.print("Error at loading a new Function");
                }
            }
        }

        super.optionsSelected(id, option);
    }

    Vector3d screenToWorld(float x, float y) {

        x -= this.x;
        y -= this.y;

        x /= zoom;
        y /= zoom;

        return new Vector3d(x, y, 1);
    }


    Vector3d worldToScreen(float x, float y) {

        x *= zoom;
        y *= zoom;

        x += this.x;
        y += this.y;

        return new Vector3d(x, y, 1);
    }
}
