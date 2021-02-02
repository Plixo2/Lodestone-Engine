package net.plixo.paper.client.UI.other;

import net.plixo.paper.Paper;
import net.plixo.paper.client.UI.elements.UIButton;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Util;

public class Toolbar extends UICanvas {
    public Toolbar(int id) {
        super(id);
        setRoundness(0);
        setColor(0);
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
        fileButton.setDimensions(0,0,50,height);
        fileButton.setDisplayName("File");
        fileButton.setColor(0);
        fileButton.setRoundness(0);

        UIButton settingsButton = new UIButton(0) {
            @Override
            public void actionPerformed() {
                Util.print("Open Settings menu");
            }
        };
        settingsButton.setDimensions(50,0,50,height);
        settingsButton.setDisplayName("Settings");
        settingsButton.setColor(0);
        settingsButton.setRoundness(0);


        UIButton uiToggleButton = new UIButton(0) {
            @Override
            public void actionPerformed() {
                if (Paper.paperEngine.isRunning) {
                    setDisplayName("Start");
                    setColor(0);
                    Paper.paperEngine.stopEngine();
                } else {
                    setDisplayName("Stop");
                    setColor(ColorLib.cyan());
                    Paper.paperEngine.startEngine();
                }
                super.actionPerformed();
            }
        };
        uiToggleButton.setDimensions(100, 0, 50, height);
        uiToggleButton.setDisplayName("Start");
        uiToggleButton.setRoundness(0);
        uiToggleButton.setColor(0);

        if (Paper.paperEngine.isRunning) {
            uiToggleButton.setColor(ColorLib.cyan());
            uiToggleButton.setDisplayName("Stop");
        }

        add(uiToggleButton);
        add(fileButton);
        add(settingsButton);
    }
}
