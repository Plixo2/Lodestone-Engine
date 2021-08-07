package net.plixo.lodestone.client.ui.other;

import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.screens.ScreenCanvasUI;
import net.plixo.lodestone.Lodestone;
import net.plixo.lodestone.client.manager.RAssets;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.ui.tabs.UIConsole;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.serialiable.Options;

import javax.swing.*;

public class UIToolbar extends UICanvas {


    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);


        UIButton compile = new UIButton();
        compile.setAction(RAssets::compile);
        compile.setDimensions(0, 0, 50, height);
        compile.setRoundness(0);
        compile.setColor(0);
        compile.setDisplayName("Compile");


        UIButton settings = new UIButton();
        settings.setAction(() -> {
            ScreenCanvasUI optionsCanvas = new ScreenCanvasUI() {
                @Override
                public void onClose() {
                    SwingUtilities.invokeLater(() -> { mc.displayGuiScreen(new ScreenMain());
                    });
                    super.onClose();
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

        UIButton start = new UIButton();
        start.setTickAction(() -> {
            if (Lodestone.lodestoneEngine.isRunning) {
                start.setColor(UColor.getMainColor());
                start.setDisplayName("Stop");
            } else {
                start.setDisplayName("Start");
                start.setColor(0);
            }
        });
        start.setAction(() -> {
            if (Lodestone.lodestoneEngine.isRunning) {
                Lodestone.lodestoneEngine.stopEngine();
            } else {
                Lodestone.lodestoneEngine.startEngine();
            }
        });
        start.setDimensions(100, 0, 50, height);
        start.setDisplayName("Start");
        start.setRoundness(0);
        start.setColor(0);


        UIButton consoleOutput = new UIButton() {
            @Override
            public void drawName(float mouseX, float mouseY) {
                int size = UIConsole.consoleLines.size();
                if (size > 0) {
                    try {
                        String line = UIConsole.consoleLines.get(size - 1);
                        UGui.drawString(line, x + width - (UGui.getStringWidth(line) + 5), y + height / 2, UColor.getTextColor());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        consoleOutput.setColor(0);
        consoleOutput.setRoundness(0);
        consoleOutput.setDimensions(width - 300, 0, 300, height);

        add(compile);
        add(consoleOutput);
        add(start);
        add(settings);

        setRoundness(0);
        setColor(UColor.getBackground(-0.2f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        super.drawScreen(mouseX, mouseY);
    }


}
