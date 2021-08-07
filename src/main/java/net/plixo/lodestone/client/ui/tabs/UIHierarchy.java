package net.plixo.lodestone.client.ui.tabs;

import net.plixo.lodestone.client.engine.core.GameObject;
import net.plixo.lodestone.client.manager.RClient;
import net.plixo.lodestone.client.manager.REditor;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.ui.screens.ScreenAccept;
import net.plixo.lodestone.client.ui.screens.ScreenTextInput;
import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.util.UColor;


public class UIHierarchy extends UICanvas {


    public UIHierarchy() {
      setDisplayName("Hierarchy");
        REditor.explorer = this;
    }


    @Override
    public void init() {
       setColor(UColor.getBackground(0.2f));
        reCalc();
    }

    void reCalc() {
        clear();
        UIArray array = new UIArray();
        array.setDimensions(0, 0, width, height - 15);
        array.setRoundness(0);
        array.setColor(UColor.getBackground(0.2f));
        array.space = 2;
        array.add(new UIElement() {});

        for (GameObject obj : RClient.allEntities) {
            if(obj == null) {
                System.out.println("null?");
                RClient.allEntities.remove(null);
                continue;
            }
            UICanvas main = new UICanvas();
            main.setDimensions(2, 0, width-4, 20);
            main.setColor(UColor.getBackground(-0.2f));

            UIButton button = new UIButton();
            button.alignLeft();
            button.setAction(() ->  REditor.inspector.initInspector(obj));
            button.setDimensions(0, 0, main.width - 20, 20);
            button.setDisplayName(obj.name);
            button.setRoundness(3);
            button.setColor(0);

            UIButton label = new UIButton();
            label.setAction(() -> {
                mc.displayGuiScreen(new ScreenAccept(() -> {
                    RClient.removeEntity(obj);
                    REditor.inspector.initInspector((GameObject) null);
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

        if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
            ScreenMain.instance.beginMenu();
            ScreenMain.instance.addMenuOption("New", () -> {
                ScreenTextInput input = new ScreenTextInput((a) -> {
                    GameObject entity = new GameObject(a);
                    RClient.addEntity(entity);
                    reCalc();
                }, (b) -> {
                }, "Add Entity");
                input.Syes = "Create";
                mc.displayGuiScreen(input);
            });
            ScreenMain.instance.showMenu();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


}
