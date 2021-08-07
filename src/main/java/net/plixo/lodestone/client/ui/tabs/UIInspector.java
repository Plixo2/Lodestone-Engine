package net.plixo.lodestone.client.ui.tabs;


import net.minecraft.util.math.vector.Vector3d;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.engine.core.Behavior;
import net.plixo.lodestone.client.engine.core.GameObject;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.manager.RAssets;
import net.plixo.lodestone.client.manager.RClient;
import net.plixo.lodestone.client.manager.REditor;
import net.plixo.lodestone.client.manager.RMeta;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.ScreenMain;
import net.plixo.lodestone.client.ui.screens.ScreenAccept;
import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.ui.resource.UIResource;
import net.plixo.lodestone.client.ui.resource.resource.UITextBox;
import net.plixo.lodestone.client.ui.resource.resource.UIVector;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.serialiable.Options;

import java.io.File;
import java.util.function.Consumer;

public class UIInspector extends UIArray {

    public UIInspector() {
       setDisplayName("Inspector");
        REditor.inspector = this;
    }


    @Override
    public void init() {
        setColor(UColor.getBackground(0.2f));
        super.init();
        if (RAssets.getLoadedEntity() != null) {
            initInspector(RAssets.getLoadedEntity());
        }
        this.space = 5;
    }

    public void initInspector(File origin) {
        clear();
        RAssets.setCurrentEntity(null);
        RAssets.setCurrentMeta(null);
        if (origin == null) {
            return;
        }
        try {
            RAssets.setCurrentMeta(RMeta.getMetaByFile(origin));
            if (RAssets.getLoadedMeta() != null) {
                add(new UIElement()  {});
                for (Resource<?> res : RAssets.getLoadedMeta().list) {
                    UIResource resource = new UIResource();
                    resource.setResource(res);
                    resource.setDimensions(2, 0, width - 4, 20);
                    resource.setColor(UColor.getBackground(0.4f));
                    add(resource);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            UMath.print(e.getMessage());
        }
    }
    public UICanvas addVector(String name, Vector3d vector3d, Consumer<Vector3d> runnable) {
        float xStart  = width * 0.25f;
        UICanvas canvas = new UICanvas();
        canvas.setDimensions(0,0,width,20);
        canvas.setColor(0);
        UIVector uiVector = new UIVector();
        uiVector.setTickAction(() -> runnable.accept(uiVector.getAsVector()));
        uiVector.setDimensions(xStart, 0, width - xStart, 20);
        uiVector.setVector(vector3d);
        uiVector.setDisplayName(name);

        UIButton uiLabel = new UIButton();
        uiLabel.setDimensions(0, uiVector.y, xStart, 20);
        uiLabel.setDisplayName(name);
        uiLabel.setRoundness(0);
        uiLabel.setColor(UColor.getBackground(0.1f));

        canvas.add(uiVector);
        canvas.add(uiLabel);

        return canvas;
    }


    public void initInspector(GameObject entity) {
        clear();
        RAssets.setCurrentEntity(entity);
        RAssets.setCurrentMeta(null);
        if (entity == null) {
            return;
        }
        try {
            add(new UIElement() {});
            UITextBox nameField = new UITextBox() {

                @Override
                public void drawScreen(float mouseX, float mouseY) {
                    super.drawScreen(mouseX, mouseY);

                    if (!field.isFocused() && Options.options.useUnicode.value) {
                        UGui.drawString(" \u270E", x + width - 20, y + height / 2, UColor.getTextColor());
                    }
                }
            };
            nameField.setTextChanged(() -> {
                entity.name = nameField.getText();
                REditor.initTab(REditor.explorer);
            });
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
                behaviorCanvas.setColor(UColor.getBackground(0f));

                UICanvas canvas = new UICanvas();
                canvas.setColor(UColor.getBackground(0.5f));
                canvas.setDimensions(0,0,width,20);

                UIButton head = new UIButton();
                head.alignLeft();
                head.setDimensions(0, 0, width, 20);
                head.setDisplayName(b.name);
                head.setColor(0);
                canvas.add(head);

                UIButton remove = new UIButton();
                remove.setDisplayName("-");
                remove.setDimensions(width - 20, 0, 20, 20);
                remove.setColor(0);
                remove.setAction(() -> {
                    mc.displayGuiScreen(new ScreenAccept(() -> {
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

        if (mouseButton == 1 && isHovered(mouseX, mouseY) && RAssets.getLoadedEntity() != null) {
            ScreenMain.instance.beginMenu();
            for (Behavior b : RClient.standardBehavior) {
                ScreenMain.instance.addMenuOption("Add",b.name, () -> {
                    Behavior instance = RClient.newInstanceByName(b.name);
                    if (instance != null && RAssets.getLoadedEntity() != null) {
                        RAssets.getLoadedEntity().addBehavior(instance);
                        initInspector(RAssets.getLoadedEntity());
                    }
                });
            }
            ScreenMain.instance.showMenu();
        } else {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

}
