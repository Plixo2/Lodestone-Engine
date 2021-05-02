package net.plixo.paper.client.avs.newVersion.test;

import net.plixo.paper.client.avs.newVersion.nFunction;
import net.plixo.paper.client.ui.GUI.*;
import net.plixo.paper.client.ui.elements.*;
import net.plixo.paper.client.util.Util;

public class GUITest extends nFunction {

    @Override
    public void run() {
        pullInputs();
        GUICanvas canvas = new GUICanvas() {
            @Override
            protected void init() {
                super.init();
            }
        };
        UIButton button = new UIButton() {
            public void actionPerformed() {
                super.actionPerformed();
            }
        };
        UITextbox box = new UITextbox();
        box.getText();


        mc.displayGuiScreen(canvas);
        execute();
    }

    @Override
    public void set() {
        set(0,0,1);
    }
}
