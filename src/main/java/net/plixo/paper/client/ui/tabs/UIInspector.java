package net.plixo.paper.client.ui.tabs;


import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.manager.ClientManager;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.manager.MetaManager;
import net.plixo.paper.client.ui.GUI.GUIAccept;
import net.plixo.paper.client.ui.GUI.GUIMain;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UIArray;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.ui.elements.values.UIResource;
import net.plixo.paper.client.ui.elements.values.UITextbox;
import net.plixo.paper.client.ui.elements.values.UIVector;
import net.plixo.paper.client.ui.elements.visual.UILabel;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.Options;
import net.plixo.paper.client.util.Util;

import java.io.File;

public class UIInspector extends UIArray {


    public UIInspector() {
        this.displayName = "Inspector";
        EditorManager.inspector = this;
    }


    @Override
    public void init() {
        this.color = ColorLib.getBackground(0.2f);
        super.init();
        if (AssetLoader.getLoadedEntity() != null) {
            initInspector(AssetLoader.getLoadedEntity());
        }
        this.space = 5;
    }

    public void initInspector(File origin) {
        clear();
        AssetLoader.setCurrentEntity(null);
        AssetLoader.setCurrentMeta(null);
        if (origin == null) {
            return;
        }
        try {
            AssetLoader.setCurrentMeta(MetaManager.getMetaByFile(origin));
            if (AssetLoader.getLoadedMeta() != null)
                for (Resource<?> res : AssetLoader.getLoadedMeta().list) {
                    UIResource resource = new UIResource();
                    resource.setResource(res);
                    resource.setDimensions(2, 0, width - 4, 20);
                    add(resource);
                }
        } catch (Exception e) {
            e.printStackTrace();
            Util.print(e.getMessage());
        }
    }
    public UICanvas addVector(String name, Vector3d vector3d,Util.IObject<Vector3d> runnable) {
        float xStart  = width * 0.25f;
        UICanvas canvas = new UICanvas();
        canvas.setDimensions(0,0,width,20);
        canvas.setColor(0);
        UIVector uiVector = new UIVector();
        uiVector.setTickAction(() -> runnable.run(uiVector.getAsVector()));
        uiVector.setDimensions(xStart, 0, width - xStart, 20);
        uiVector.setVector(vector3d);
        uiVector.setDisplayName(name);

        UIButton uiLabel = new UIButton();
        uiLabel.setDimensions(0, uiVector.y, xStart, 20);
        uiLabel.setDisplayName(name);
        uiLabel.setRoundness(0);
        uiLabel.setColor(ColorLib.getBackground(0.1f));

        canvas.add(uiVector);
        canvas.add(uiLabel);

        return canvas;
    }


    public void initInspector(GameObject entity) {
        clear();
        AssetLoader.setCurrentEntity(entity);
        AssetLoader.setCurrentMeta(null);
        if (entity == null) {
            return;
        }
        try {
            UITextbox nameField = new UITextbox() {
                @Override
                public void textFieldChanged() {
                    entity.name = getText();
                    EditorManager.initTab(EditorManager.explorer);
                    super.textFieldChanged();
                }

                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    super.drawScreen(mouseX, mouseY);

                    if (!field.isFocused() && Options.options.useUnicode.value) {
                        Gui.drawString(" \u270E", x + width - 20, y + height / 2, textColor);
                    }
                }
            };
            nameField.setDimensions(0, 0, width, 20);
            nameField.setRoundness(0);
            nameField.setText(entity.name);
            add(nameField);


            add(addVector("Position",entity.position, (vec) -> entity.position = vec));
            add(addVector("Rotation",entity.rotation, (vec) -> entity.rotation = vec));
            add(addVector("Scale",entity.scale, (vec) -> entity.scale = vec));


            for (Behavior b : entity.components) {
                UIArray behaviorCanvas = new UIArray();

                behaviorCanvas.setRoundness(1);
                behaviorCanvas.space = 3;
                behaviorCanvas.setColor(ColorLib.getBackground(0f));

                UICanvas canvas = new UICanvas();
                canvas.setDimensions(0,0,width,20);

                UIButton head = new UIButton() {
                    @Override
                    public void drawStringCentered(float mouseX, float mouseY) {
                        if (displayName != null) {
                            Gui.drawStringWithShadow(displayName, x + 5, y + height / 2, textColor);
                        }
                    }
                };
                head.setDimensions(0, 0, width, 20);
                head.setDisplayName(b.name);
                head.setColor(0);
                canvas.add(head);

                UIButton remove = new UIButton();
                remove.setDisplayName("-");
                remove.setDimensions(width - 20, 0, 20, 20);
                remove.setColor(0);
                remove.setAction(() -> {
                    mc.displayGuiScreen(new GUIAccept(() -> {
                        entity.components.remove(b);
                        init();
                        initInspector(entity);
                    }, () -> {
                    }, "Delete?"));
                });

                canvas.add(remove);
                behaviorCanvas.add(canvas);

                for (Resource<?> res : b.serializable) {
                    UIResource resource = new UIResource();
                    resource.setResource(res);
                    resource.setDimensions(2, 0, width - 4, 20);
                    behaviorCanvas.add(resource);
                }

                behaviorCanvas.setDimensions(0, 0, width, behaviorCanvas.getMax()+2);
                add(behaviorCanvas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if (mouseButton == 1 && hovered(mouseX, mouseY) && AssetLoader.getLoadedEntity() != null) {
            GUIMain.instance.beginMenu();
            for (Behavior b : ClientManager.standardBehavior) {
                GUIMain.instance.addMenuOption(b.name, () -> {
                    Behavior instance = ClientManager.newInstanceByName(b.name);
                    if (instance != null && AssetLoader.getLoadedEntity() != null) {
                        AssetLoader.getLoadedEntity().addBehavior(instance);
                        initInspector(AssetLoader.getLoadedEntity());
                    }
                });
            }
            GUIMain.instance.showMenu();
        } else {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

}
