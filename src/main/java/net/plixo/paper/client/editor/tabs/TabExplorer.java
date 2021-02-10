package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.UIArray;
import net.plixo.paper.client.UI.elements.UIButton;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.engine.TheManager;
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
        canvas = new UIArray(0) {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

                for (UIElement element : elements) {
                    if(element.hovered(mouseX-x,mouseY-y)) {
                        super.mouseClicked(mouseX, mouseY, mouseButton);
                        return;
                    }
                }

                if(mouseButton == 1 && hovered(mouseX,mouseY)) {
                    OptionMenu.TxtRun run = new OptionMenu.TxtRun("New Entity") {
                        @Override
                        protected void run() {
                            GameObject entity = new GameObject("NewEntity" + TheManager.allEntities.size());
                            TheManager.addEntity(entity);
                            reCalc();
                        }
                    };
                    showMenu(0, mouseX, mouseY, run);
                }
            }
        };
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.2f));
        reCalc();
        super.init();
    }

    void reCalc() {
        canvas.clear();
        for (GameObject obj : TheManager.allEntities) {
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

                @Override
                public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                    if(mouseButton == 1 && hovered(mouseX,mouseY)) {
                        UIButton b = this;
                        OptionMenu.TxtRun run = new OptionMenu.TxtRun("Delete") {
                            @Override
                            protected void run() {
                                TheManager.allEntities.remove(obj);
                                canvas.remove(b);
                                TheEditor.inspector.initInspector(null);
                            }
                        };
                        showMenu(0, mouseX, mouseY, run);
                        return;
                    }
                    super.mouseClicked(mouseX, mouseY, mouseButton);
                }
            };
            button.setDisplayName(obj.name);
            button.setRoundness(3);
            button.setColor(0);
            canvas.add(button);
        }

    }


    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        hideMenu();
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}
