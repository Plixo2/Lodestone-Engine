package net.plixo.lodestone.client.ui.tabs;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.canvas.UIDraggable;
import net.plixo.lodestone.client.manager.RAssets;
import net.plixo.lodestone.client.manager.REditor;
import net.plixo.lodestone.client.manager.RFunctions;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.ui.visualscript.UIFunction;
import net.plixo.lodestone.client.ui.visualscript.UIGrid;
import net.plixo.lodestone.client.util.FunctionCursorHolder;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.visualscript.Function;
import net.plixo.lodestone.client.visualscript.functions.FMethod;
import net.plixo.lodestone.client.visualscript.functions.FSuperMethod;
import org.lwjgl.glfw.GLFW;

import java.io.File;

public class UIViewport extends UIGrid {


    public UIViewport() {
        setDisplayName("Viewport");
        REditor.viewport = this;
        setShouldDrawLines(true);
    }

    public void load(File file) {
        RAssets.setCurrentScript(null);
        RAssets.setCurrentScript(RFunctions.loadFromFile(file));
        init();
    }

    @Override
    public void init() {
        clear();

        if (RAssets.getLoadedScript() != null) {
            for (Function function : RAssets.getLoadedScript().getFunctions()) {
                add(function.getUI());
            }
        }
    }

    @Override
    public void drawScreenTranslated(float mouseX, float mouseY) {
        super.drawScreenTranslated(mouseX, mouseY);
        FunctionCursorHolder cursorObject = ScreenMain.instance.cursorObject.getAs(FunctionCursorHolder.class);
        if (cursorObject != null) {
            if (cursorObject.isLink()) {
                Function function = cursorObject.getLink();
                float xE = mouseX;
                float yE = mouseY;
                float xI = function.getUI().x + function.getUI().width - 6;
                float yI = function.getUI().y + 20 + (cursorObject.id * 12) + 6;

                float reach = 30;
                Vector2f start = new Vector2f(xI + 12, yI);
                Vector2f startR = new Vector2f(xI + reach, yI);
                Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                Vector2f endL = new Vector2f(xE - reach, yE);
                Vector2f end = new Vector2f(xE, yE);

                int darker = UColor.getDarker(UColor.getMainColor());
                UGui.Bezier(darker, darker, 3 * getZoom(), start, startR, mid, endL, end);
                UGui.drawCircle(start.x, start.y, 2, UColor.getMainColor());
                UGui.drawCircle(end.x, end.y, 2, UColor.getMainColor());
            } else if (cursorObject.isOutput()) {
                Function function = cursorObject.getLink();
                UIFunction UIFunction = function.getUI();
                UIElement element = UIFunction.outputList.get(function.getOutput()[cursorObject.id]);
                if (element == null) {
                    UMath.print("Something went wrong");
                    return;
                }
                float xE = element.x + element.width / 2;
                float yE = element.y + element.height / 2;
                xE += UIFunction.x;
                xE += UIFunction.width - 12;
                yE += UIFunction.y;
                yE += 20;

                float xI = mouseX;
                float yI = mouseY;

                float reach = 30;
                Vector2f start = new Vector2f(xI, yI);
                Vector2f startR = new Vector2f(xI - reach, yI);
                Vector2f mid = new Vector2f((xI + xE) / 2, (yI + yE) / 2);
                Vector2f endL = new Vector2f(xE + reach, yE);
                Vector2f end = new Vector2f(xE + 12, yE);


                UGui.Bezier(0x99CCDDEE, 0x99CCDDEE, 3 * getZoom(), start, startR, mid, endL, end);
                UGui.drawCircle(start.x, start.y, 2, 0xAACCDDEE);
                UGui.drawCircle(end.x, end.y, 2, 0xAACCDDEE);
            }
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        if (RAssets.getLoadedScript() == null) {
            return;
        }
        super.drawScreen(mouseX, mouseY);

        String name = RAssets.getLoadedScript().name;
        UGui.drawString(name, width - UGui.getStringWidth(name) - 9, height - 29, 0xFF444444);
        UGui.drawString(name, width - UGui.getStringWidth(name) - 10, height - 30, UColor.getTextColor());

        name = RAssets.getLoadedScript().location.getName();
        UGui.drawString(name, width - UGui.getStringWidth(name) - 10, height - 20, UColor.getTextColor());

        name = RAssets.getLoadedScript().getFunctions().size() + " Nodes";
        UGui.drawString(name, width - UGui.getStringWidth(name) - 10, height - 10, UColor.getTextColor());
    }



    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        if (RAssets.getLoadedScript() == null) {
            return;
        }

        super.mouseReleased(mouseX, mouseY, state);
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (!isHovered(mouseX, mouseY) || RAssets.getLoadedScript() == null) {
            return;
        }
        Vector2f mouseToWorld = screenToWorld(mouseX, mouseY);
        for (UIElement element : elements) {
            if (element instanceof UIDraggable)
                ((UIDraggable) element).beginSelect(mouseToWorld.x, mouseToWorld.y, mouseButton);
        }
        for (UIElement element : elements) {
            element.mouseClicked(mouseToWorld.x, mouseToWorld.y, mouseButton);
            if (element.isHovered(mouseToWorld.x, mouseToWorld.y)) {
                return;
            }
        }
        if (mouseButton == 1) {
            startDragging(mouseX, mouseY);
        }
        mouseClickedTranslated(mouseToWorld.x, mouseToWorld.y, mouseButton);
    }



    @Override
    public void mouseReleasedTranslated(float globalMouseX, float globalMouseY, float mouseX, float mouseY, int state) {
        if (state == 1) {
            boolean b = true;
            if (isHovered(globalMouseX,globalMouseY) && hasMoved(globalMouseX,globalMouseY)) {

                ScreenMain.instance.beginMenu();
                for (Function function : RFunctions.getStandard()) {
                    ScreenMain.instance.addMenuOption("Other", function.getDisplayName(), () -> {
                        if (RAssets.getLoadedScript() != null) {
                            RAssets.getLoadedScript().addFunction(function, mouseX, mouseY);
                        }
                    });
                }
                for (FSuperMethod function : RFunctions.getSuper(RAssets.getLoadedScript().getObject())) {
                    ScreenMain.instance.addMenuOption("Super", function.getDisplayName(), () -> {
                        if (RAssets.getLoadedScript() != null) {
                            RAssets.getLoadedScript().addFunction(function, mouseX, mouseY);
                        }
                    });
                }
                for (FMethod function : RFunctions.getEvent(RAssets.getLoadedScript().getObject())) {
                    ScreenMain.instance.addMenuOption("Event", function.getDisplayName(), () -> {
                        if (RAssets.getLoadedScript() != null) {
                            RAssets.getLoadedScript().addFunction(function, mouseX, mouseY);
                        }
                    });
                }
                for (Function function : RFunctions.getUtil()) {
                    ScreenMain.instance.addMenuOption("Util", function.getDisplayName(), () -> {
                        if (RAssets.getLoadedScript() != null) {
                            RAssets.getLoadedScript().addFunction(function,mouseX, mouseY);
                        }
                    });
                }
                for (Object object : RFunctions.queriedObjects) {
                    for (Function function : RFunctions.getSuper(object)) {
                        ScreenMain.instance.addMenuOption(object.getClass().getSimpleName(), function.getDisplayName(), () -> {
                            if (RAssets.getLoadedScript() != null) {
                                RAssets.getLoadedScript().addFunction(function, mouseX, mouseY);
                            }
                        });
                    }
                }


                ScreenMain.instance.showMenu();
            }
        }
        super.mouseReleasedTranslated(globalMouseX, globalMouseY, mouseX, mouseY, state);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        if (key == GLFW.GLFW_KEY_DELETE) {
            for (UIElement element : elements) {
                if (element instanceof UIFunction) {
                    UIFunction UIFunction = ((UIFunction) element);
                    if (UIFunction.isSelected) {
                        RAssets.getLoadedScript().removeFunction(UIFunction.function);
                    }
                }
            }
        }
        super.keyPressed(key, scanCode, action);
    }


}

