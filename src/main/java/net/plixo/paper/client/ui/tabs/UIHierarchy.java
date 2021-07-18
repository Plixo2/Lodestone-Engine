package net.plixo.paper.client.ui.tabs;

import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.manager.ClientManager;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.ui.GUI.GUIAccept;
import net.plixo.paper.client.ui.GUI.GUIEditor;
import net.plixo.paper.client.ui.GUI.GUIMain;
import net.plixo.paper.client.ui.GUI.GUITextInput;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.canvas.UIArray;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;


public class UIHierarchy extends UICanvas {


    public UIHierarchy() {
        this.displayName = "Hierarchy";
        EditorManager.explorer = this;
    }


    @Override
    public void init() {
        this.color = ColorLib.getBackground(0.2f);
        reCalc();
    }

    void reCalc() {
        clear();
        UIArray array = new UIArray();
        array.setDimensions(0, 0, width, height - 15);
        array.setRoundness(0);
        array.setColor(ColorLib.getBackground(0.2f));
        array.space = 2;

        for (GameObject obj : ClientManager.allEntities) {
            if(obj == null) {
                System.out.println("null?");
                ClientManager.allEntities.remove(null);
                continue;
            }
            UICanvas main = new UICanvas();
            main.setDimensions(0, 0, width, 20);

            UIButton button = new UIButton() {
                @Override
                public void drawStringCentered(float mouseX, float mouseY) {
                    if (displayName != null) {
                        Gui.drawString(displayName, x + 5, y + height / 2, textColor);
                    }
                }
            };
            button.setAction(() ->  EditorManager.inspector.initInspector(obj));
            button.setDimensions(0, 0, main.width - 20, 20);
            button.setDisplayName(obj.name);
            button.setRoundness(3);
            button.setColor(0);

            UIButton label = new UIButton();
            label.setAction(() -> {
                mc.displayGuiScreen(new GUIAccept(() -> {
                    ClientManager.removeEntity(obj);
                    EditorManager.inspector.initInspector((GameObject) null);
                    reCalc();
                }, () -> {
                }, "Delete?"));
            });
            label.setColor(0);
            label.setDimensions(main.width - 20, 0, 20, 20);
            label.setDisplayName("-");

            main.add(button);
            main.add(label);

            array.add(main);
        }
        add(array);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if (mouseButton == 1 && hovered(mouseX, mouseY)) {
            GUIMain.instance.beginMenu();
            GUIMain.instance.addMenuOption("Add Entity", () -> {
                GUITextInput input = new GUITextInput((a) -> {
                    GameObject entity = new GameObject(a);
                    ClientManager.addEntity(entity);
                    reCalc();
                }, (b) -> {
                }, "Add Entity");
                input.Syes = "Create";
                mc.displayGuiScreen(input);
            });
            GUIMain.instance.showMenu();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}
