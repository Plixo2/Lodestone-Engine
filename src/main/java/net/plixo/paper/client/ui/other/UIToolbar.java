package net.plixo.paper.client.ui.other;

import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.ui.GUI.GUICanvas;
import net.plixo.paper.client.ui.GUI.GUIMain;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.canvas.UIContext;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.ui.tabs.UIConsole;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Options;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public class UIToolbar extends UICanvas {


    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);


        UIButton compile = new UIButton();
        compile.setAction(AssetLoader::compile);
        compile.setDimensions(0, 0, 50, height);
        compile.setRoundness(0);
        compile.setColor(0);
        compile.setDisplayName("Compile");


        UIButton settings = new UIButton();
        settings.setAction(() -> {
            GUICanvas optionsCanvas = new GUICanvas() {
                @Override
                public boolean keyPressed(int key, int scanCode, int action) {
                    if (key == 0) {
                        mc.displayGuiScreen(new GUIMain());
                    }
                    return super.keyPressed(key, scanCode, action);
                }
            };
            mc.displayGuiScreen(optionsCanvas);
            UICanvas canvas = Options.options.getOptionsCanvas(400, 25);
            canvas.setDimensions(0, 0, optionsCanvas.canvas.width, optionsCanvas.canvas.height);
            optionsCanvas.add(canvas);

        });
        settings.setDimensions(50, 0, 50, height);
        settings.setDisplayName("Settings");
        settings.setColor(0);
        settings.setRoundness(0);

        UIButton start = new UIButton() {
            @Override
            public void actionPerformed() {
                if (Lodestone.lodestoneEngine.isRunning) {
                    Lodestone.lodestoneEngine.stopEngine();
                } else {
                    Lodestone.lodestoneEngine.startEngine();
                }
                super.actionPerformed();
            }

            @Override
            public void drawScreen(float mouseX, float mouseY) {
                if (Lodestone.lodestoneEngine.isRunning) {
                    setColor(ColorLib.cyan());
                    setDisplayName("Stop");
                } else {
                    setDisplayName("Start");
                    setColor(0);
                }
                super.drawScreen(mouseX, mouseY);
            }
        };
        start.setDimensions(100, 0, 50, height);
        start.setDisplayName("Start");
        start.setRoundness(0);
        start.setColor(0);


        UIButton consoleOutput = new UIButton() {
            @Override
            public void drawStringCentered(float mouseX, float mouseY) {
                int size = UIConsole.consoleLines.size();
                if (size > 0) {
                    try {
                        UIConsole.ConsoleLine line = UIConsole.consoleLines.get(size - 1);
                        Gui.drawString(line.line, x + width - (Gui.getStringWidth(line.line) + 5), y + height / 2, -1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        consoleOutput.setColor(0);
        consoleOutput.setRoundness(0);
        consoleOutput.setDimensions(width - 300, 0, 300, height);
        consoleOutput.setAction(() -> {
            GUICanvas guiCanvas = new GUICanvas();
            mc.displayGuiScreen(guiCanvas);

            guiCanvas.canvas.setDimensions(20,10,guiCanvas.width,guiCanvas.height);
            UIContext context = new UIContext();
            context.setDrawContext((mouseX,mouseY) -> {
            });
            context.setDimensions(20,30,100,100);
            guiCanvas.canvas.add(context);
        });

        add(compile);
        add(consoleOutput);
        add(start);
        add(settings);

        setRoundness(0);
        setColor(ColorLib.getBackground(-0.2f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        super.drawScreen(mouseX, mouseY);
    }


}
