package net.plixo.paper.client.ui.other;

import net.minecraft.client.Minecraft;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.ui.elements.UIButton;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.tabs.TabConsole;
import net.plixo.paper.client.ui.GUI.GUIOption;
import net.plixo.paper.client.ui.elements.UILabel;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;

public class Toolbar extends UICanvas {

    public Toolbar(int id) {
        setRoundness(0);
        setColor(ColorLib.getBackground(-0.2f));
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);



        UIButton settingsButton = new UIButton() {
            @Override
            public void actionPerformed() {
                Util.print("Open Settings menu");
                Minecraft.getInstance().displayGuiScreen(new GUIOption());
            }
        };
        settingsButton.setDimensions(100, 0, 50, height);
        settingsButton.setDisplayName("Settings");
        settingsButton.setColor(0);
        settingsButton.setRoundness(0);


        UIButton uiToggleButton = new UIButton() {
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
        uiToggleButton.setDimensions(50, 0, 50, height);
        uiToggleButton.setDisplayName("Start");
        uiToggleButton.setRoundness(0);
        uiToggleButton.setColor(0);

        UIButton compile = new UIButton() {
            @Override
            public void actionPerformed() {
                AssetLoader.compile();
            }
        };
        compile.setDimensions(0,0,50,20);
        compile.setRoundness(0);
        compile.setColor(0);
        compile.setDisplayName("Compile");



        UILabel consoleOutput = new UILabel() {
            @Override
            public void drawDisplayString() {
                if (TabConsole.consoleLines.size() > 0) {
                    TabConsole.ConsoleLine line = TabConsole.consoleLines.get(TabConsole.consoleLines.size() - 1);
                    Gui.drawString(line.line,x+width- (Gui.getStringWidth(line.line)+5),y+height/2,-1);
                }
            }
        };
        consoleOutput.setColor(0);
        consoleOutput.setRoundness(0);
        consoleOutput.setDimensions(width - 300, 0, 300, 20);

        add(compile);
        add(consoleOutput);
        add(uiToggleButton);
      //  add(settingsButton);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        super.drawScreen(mouseX, mouseY);
        Gui.drawSideGradientRect(0,0,5,height , 0x80000000,0x00000000);
        Gui.drawSideGradientRect(width-5,0,width,height , 0,0x80000000);

    }


}
