package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.ui.GUI.GUIAccept;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.UIArray;
import net.plixo.paper.client.ui.elements.UIButton;
import net.plixo.paper.client.manager.TheEditor;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.ui.elements.UILabel;
import net.plixo.paper.client.ui.other.OptionMenu;
import net.plixo.paper.client.manager.TheManager;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


public class TabExplorer extends UITab {


    public TabExplorer(int id) {
        super(id, "Explorer");
        TheEditor.explorer = this;
    }


    @Override
    public void init() {
        canvas = new UICanvas(0);
        canvas.setDimensions(0,0, parent.width, parent.height);
        canvas.setRoundness(0);

        reCalc();
        super.init();
    }

    void reCalc() {
        canvas.clear();
        UIArray array = new UIArray(0);
        array.setDimensions(0, 15, parent.width, parent.height-15);
        array.setRoundness(0);
        array.setColor(ColorLib.getBackground(0.2f));

        for (GameObject obj : TheManager.allEntities) {

            UICanvas main = new UICanvas(0);
            main.setDimensions(0,0, canvas.width-15, 20);


            UIButton button = new UIButton(0) {
                @Override
                public void drawStringCentered(float mouseX, float mouseY) {
                    if (displayName != null) {
                        Gui.drawString(displayName, x + 5, y + height / 2, textColor);
                    }
                }

                @Override
                public void actionPerformed() {
                    TheEditor.inspector.initInspector(obj);
                }

            };
            button.setDimensions(0,0, main.width-20, 20);
            button.setDisplayName(obj.name);
            button.setRoundness(3);
            button.setColor(0);

            UIButton label = new UIButton(0) {
                @Override
                public void actionPerformed() {
                    mc.displayGuiScreen(new GUIAccept(() -> {
                        TheManager.removeEntity(obj);
                        TheEditor.inspector.initInspector((GameObject) null);
                        reCalc();
                    }, () -> {
                    }, "Delete?"));
                }
            };
            label.setDimensions(main.width-20,0,20,20);
            label.setDisplayName("-");

            main.add(button);
            main.add(label);

            array.add(main);
        }

        UIButton addEntity = new UIButton(0) {
            @Override
            public void actionPerformed() {
                GameObject entity = new GameObject("NewEntity" + TheManager.allEntities.size());
                TheManager.addEntity(entity);
                reCalc();
            }
        };
        addEntity.setDisplayName("+");
        addEntity.setColor(ColorLib.blue());
        addEntity.setDimensions(parent.width-15, 0,15,15);
        canvas.add(addEntity);

        canvas.add(array);
    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        hideMenu();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}
