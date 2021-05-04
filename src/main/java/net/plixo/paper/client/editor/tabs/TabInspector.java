package net.plixo.paper.client.editor.tabs;


import net.plixo.paper.Lodestone;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.manager.ClientManager;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.manager.MetaManager;
import net.plixo.paper.client.ui.GUI.GUIEditor;
import net.plixo.paper.client.util.Options;
import net.plixo.paper.client.engine.ecs.*;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.*;
import net.plixo.paper.client.util.*;
import org.lwjgl.glfw.GLFW;

import java.io.File;

public class TabInspector extends UITab {


    public TabInspector(int id) {
        super(id, "Inspector");
        EditorManager.inspector = this;
    }


    @Override
    public void init() {
        canvas = new UICanvas();
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.05f));

        super.init();
    }

    public void initInspector(File origin) {
        canvas.clear();
        AssetLoader.setCurrentEntity(null);
        AssetLoader.setCurrentMeta(null);
        if (origin == null) {
            return;
        }
        try {
            int yRes = 5;
            AssetLoader.setCurrentMeta(MetaManager.getMetaByFile(origin));
            if(AssetLoader.getLoadedMeta() != null)
                for (Resource res : AssetLoader.getLoadedMeta().serialized) {
                UIElement element = Resource.getUIElement(res, 50, yRes, parent.width - 54, 20);
                UILabel label = new UILabel() {
                    @Override
                    public void drawDisplayString() {
                            Gui.drawString(displayName, x, y + height / 2, textColor);
                    }
                };
                label.setDimensions(3,yRes,50,20);
                label.setDisplayName(res.name);
                canvas.add(label);
                element.setDisplayName(res.name);
                canvas.add(element);
                yRes += 22;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Util.print(e.getMessage());
        }
    }

    public void initInspector(GameObject entity) {
        canvas.clear();
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

                    if (!field.isFocused() && Options.useUnicode) {
                        Gui.drawString(" \u270E", x + width - 20, y + height / 2, textColor);
                    }
                }
            };
            canvas.add(nameField);

            nameField.setDimensions(0, 0, parent.width, 20);
            nameField.setRoundness(0);

            nameField.setText(entity.name);


            int yBe = 20;

            float xStart = parent.width * 0.25f;


            UIVector pos = new UIVector() {
                @Override
                public void onTick() {
                    entity.position = getAsVector();
                    super.onTick();
                }
            };
            pos.setDimensions(xStart, yBe, parent.width - xStart, 20);
            pos.setVector(entity.position);
            pos.setDisplayName("Position");


            yBe += 20;

            UIVector scale = new UIVector() {
                @Override
                public void onTick() {
                    entity.scale = getAsVector();
                    super.onTick();
                }
            };
            scale.setDimensions(xStart, yBe, parent.width - xStart, 20);
            scale.setVector(entity.scale);
            scale.setDisplayName("Scale");

            yBe += 20;

            UIVector rot = new UIVector() {
                @Override
                public void onTick() {
                    entity.rotation = getAsVector();
                    super.onTick();
                }
            };
            rot.setDimensions(xStart, yBe, parent.width - xStart, 20);
            rot.setVector(entity.rotation);
            rot.setDisplayName("Rotation");
            rot.setRoundness(0);
            yBe += 20;

            UILabel positionLabel = new UILabel();
            positionLabel.setDimensions(0, pos.y, xStart, 20);
            positionLabel.setDisplayName("Position");

            UILabel scaleLabel = new UILabel();
            scaleLabel.setDimensions(0, scale.y, xStart, 20);
            scaleLabel.setDisplayName("Scale");

            UILabel rotationLabel = new UILabel();
            rotationLabel.setDimensions(0, rot.y, xStart, 20);
            rotationLabel.setDisplayName("Rotation");

            canvas.add(pos);
            canvas.add(scale);
            canvas.add(rot);

            canvas.add(positionLabel);
            canvas.add(scaleLabel);
            canvas.add(rotationLabel);


            for (Behavior b : entity.components) {
                UICanvas behaviorCanvas = new UICanvas() {
                    @Override
                    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                        if (hovered(mouseX, mouseY) && !Lodestone.lodestoneEngine.isRunning && KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                            entity.components.remove(b);
                            init();
                            initInspector(entity);
                        } else {
                            super.mouseClicked(mouseX, mouseY, mouseButton);
                        }
                    }
                };
                behaviorCanvas.setRoundness(0);
                behaviorCanvas.setColor(0);


                UILabel head = new UILabel() {
                    @Override
                    public void drawDisplayString() {
                        if (displayName != null) {
                            Gui.drawStringWithShadow("-" + displayName, x + 5, y + height / 2, textColor);
                        }
                    }
                };
                head.setDimensions(0, 0, parent.width - 10, 20);
                head.setDisplayName(b.name);
                behaviorCanvas.add(head);


                int yRes = 20;
                for (Resource res : b.serializable) {
                    UIElement element = Resource.getUIElement(res, 2, yRes, parent.width - 4, 20);
                    element.setDisplayName(res.name);
                    behaviorCanvas.add(element);
                    yRes += 22;
                }

                behaviorCanvas.setDimensions(0, yBe, parent.width, yRes);
                canvas.add(behaviorCanvas);
                yBe += yRes + 7;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if (mouseButton == 1 && parent.isMouseInside(mouseX,mouseY) && AssetLoader.getLoadedEntity() != null) {
            GUIEditor.instance.beginMenu();
            for (Behavior b : ClientManager.standardBehavior) {
                GUIEditor.instance.addMenuOption(b.name , () -> {
                    Behavior instance = ClientManager.newInstanceByName(b.name);
                    if (instance != null && AssetLoader.getLoadedEntity() != null) {
                        AssetLoader.getLoadedEntity().addBehavior(instance);
                        initInspector(AssetLoader.getLoadedEntity());
                    }
                });
            }
            GUIEditor.instance.showMenu();
        } else {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

}
