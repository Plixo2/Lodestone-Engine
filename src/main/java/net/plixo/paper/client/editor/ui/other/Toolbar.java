package net.plixo.paper.client.editor.ui.other;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.UI.elements.UIButton;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.editor.tabs.TabConsole;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Util;

public class Toolbar extends UICanvas {
    public Toolbar(int id) {
        super(id);
        setRoundness(0);
        setColor(ColorLib.getBackground(-0.2f));
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);

        UIButton fileButton = new UIButton(0) {
            @Override
            public void actionPerformed() {
                Util.print("Open File menu");
            }
        };
        fileButton.setDimensions(0, 0, 50, height);
        fileButton.setDisplayName("File");
        fileButton.setColor(0);
        fileButton.setRoundness(0);

        UIButton settingsButton = new UIButton(0) {
            @Override
            public void actionPerformed() {
                Util.print("Open Settings menu");
            }
        };
        settingsButton.setDimensions(50, 0, 50, height);
        settingsButton.setDisplayName("Settings");
        settingsButton.setColor(0);
        settingsButton.setRoundness(0);


        UIButton uiToggleButton = new UIButton(0) {
            @Override
            public void actionPerformed() {
                if (Lodestone.paperEngine.isRunning) {
                    Lodestone.paperEngine.stopEngine();
                } else {
                    Lodestone.paperEngine.startEngine();
                }
                super.actionPerformed();
            }

            @Override
            public void draw(float mouseX, float mouseY) {
                if (Lodestone.paperEngine.isRunning) {
                    setColor(ColorLib.cyan());
                    setDisplayName("Stop");
                } else {
                    setDisplayName("Start");
                    setColor(0);
                }
                super.draw(mouseX, mouseY);
            }
        };
        uiToggleButton.setDimensions(100, 0, 50, height);
        uiToggleButton.setDisplayName("Start");
        uiToggleButton.setRoundness(0);
        uiToggleButton.setColor(0);


        UIButton consoleOutput = new UIButton(0) {
            @Override
            public void draw(float mouseX, float mouseY) {
                if (TabConsole.consoleLines.size() > 0) {
                    TabConsole.ConsoleLine line = TabConsole.consoleLines.get(TabConsole.consoleLines.size() - 1);
                    displayName = line.line;
                }

                super.draw(mouseX, mouseY);
            }
        };
        consoleOutput.setColor(0);
        consoleOutput.setRoundness(0);
        consoleOutput.setDimensions(width - 300, 0, 300, 20);

        add(consoleOutput);
        add(uiToggleButton);
        add(fileButton);
        add(settingsButton);
    }

    @Override
    public void draw(float mouseX, float mouseY) {

        super.draw(mouseX, mouseY);
        Gui.drawSideGradientRect(0,0,5,height , 0x80000000,0x00000000);
        Gui.drawSideGradientRect(width-5,0,width,height , 0,0x80000000);

    }


}
