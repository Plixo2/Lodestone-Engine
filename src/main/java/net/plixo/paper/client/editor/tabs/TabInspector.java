package net.plixo.paper.client.editor.tabs;


import net.plixo.paper.Lodestone;
import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.*;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.KeyboardUtil;
import net.plixo.paper.client.util.Util;
import org.lwjgl.glfw.GLFW;

public class TabInspector extends UITab {

    GameObject lastEntity;
    UICanvas UI;

    public TabInspector(int id) {
        super(id, "Inspector");
        TheEditor.inspector = this;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        UI.draw(mouseX, mouseY);
    }

    @Override
    public void init() {
        UI = new UICanvas(0);
        UI.setDimensions(0, 0, parent.width, parent.height);
        UI.setRoundness(0);
        UI.setColor(ColorLib.getBackground(0.05f));

        super.init();
    }

    public void initInspector(GameObject entity) {
        UI.clear();
        if(entity == null) {
            return;
        }

        try {
            UITextbox nameField = new UITextbox(-1) {
                @Override
                public void textFieldChanged() {
                    entity.name = getText();
                    TheEditor.initTab(TheEditor.explorer);
                    super.textFieldChanged();
                }
            };
            UI.add(nameField);

            nameField.setDimensions(0, 0, parent.width, 20);
            nameField.setRoundness(0);
            nameField.setText(entity.name);


            int yBe = 20;


            UIVector pos = new UIVector(0) {
                @Override
                public void update() {
                    entity.position = getAsVector();
                    super.update();
                }
            };
            pos.setDimensions(0, yBe, parent.width, 20);
            pos.setVector(entity.position);
            pos.setDisplayName("Position");


            yBe += 20;

            UIVector scale = new UIVector(0) {
                @Override
                public void update() {
                    entity.scale = getAsVector();
                    super.update();
                }
            };
            scale.setDimensions(0, yBe, parent.width, 20);
            scale.setVector(entity.scale);
            scale.setDisplayName("Scale");
            yBe += 20;

            UIVector rot = new UIVector(0) {
                @Override
                public void update() {
                    entity.rotation = getAsVector();
                    super.update();
                }
            };
            rot.setDimensions(0, yBe, parent.width, 20);
            rot.setVector(entity.rotation);
            rot.setDisplayName("Rotation");
            rot.setRoundness(0);
            yBe += 20;

            UI.add(pos);
            UI.add(scale);
            UI.add(rot);


            for (Behavior b : entity.components) {

                UICanvas behaviorCanvas = new UICanvas(0) {
                    @Override
                    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                        if (hovered(mouseX, mouseY) && !Lodestone.paperEngine.isRunning && KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                            entity.components.remove(b);
                            init();
                            initInspector(lastEntity);
                        } else {
                            super.mouseClicked(mouseX,mouseY,mouseButton);
                        }
                    }
                };
                behaviorCanvas.setRoundness(0);
                behaviorCanvas.setColor(0);


                UIHead head = new UIHead(0) {
                    @Override
                    public void drawStringCentered() {
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
                    UIElement element = null;
                    if (res.isFile()) {
                        UIFileChooser chooser = new UIFileChooser(0) {
                            @Override
                            public void update() {
                                res.setValue(getFile());
                                super.update();
                            }
                        };
                        chooser.setFile(res.getAsFile());
                        element = chooser;
                    } else if (res.isInteger()) {
                        UISpinner spinner = new UISpinner(0) {
                            @Override
                            public void update() {
                                res.setValue(getNumber());
                                super.update();
                            }
                        };
                        element = spinner;
                        spinner.setNumber(res.getAsInteger());
                    } else if (res.isBoolean()) {
                        UIToggleButton toggleButton = new UIToggleButton(0) {
                            @Override
                            public void update() {
                                res.setValue(getState());
                                super.update();
                            }
                        };
                        element = toggleButton;
                        toggleButton.setYesNo("True", "False");
                        toggleButton.setState(res.getAsBoolean());
                    } else if (res.isString()) {
                        UITextbox txt = new UITextbox(0) {
                            @Override
                            public void update() {
                                res.setValue(getText());
                                super.update();
                            }
                        };
                        element = txt;
                        txt.setText(res.getAsString());
                    } else if (res.isFloat()) {
                        UIPointNumber number = new UIPointNumber(0) {
                            @Override
                            public void update() {
                                res.setValue(getAsDouble());
                                super.update();
                            }
                        };
                        element = number;
                        number.setValue(res.getAsFloat());
                    } else if (res.isVector()) {
                        UIVector vec = new UIVector(0) {
                            @Override
                            public void update() {
                                res.setValue(getAsVector());
                                super.update();
                            }
                        };
                        element = vec;
                        vec.setVector(res.getAsVector());
                    }

                    element.setDimensions(2, yRes, parent.width - 4, 20);
                    element.setDisplayName(res.name);
                    behaviorCanvas.add(element);
                    yRes += 22;
                }

                behaviorCanvas.setDimensions(0, yBe, parent.width, yRes);
                UI.add(behaviorCanvas);
                yBe += yRes + 7;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        lastEntity = entity;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        UI.keyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        UI.keyPressed(key, scanCode, action);
        super.keyPressed(key, scanCode, action);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {

        hideMenu();
        if (!parent.isMouseInside(mouseX, mouseY) || lastEntity == null) {
            return;
        }
        if (mouseButton == 1) {
            OptionMenu.TxtRun[] array = new OptionMenu.TxtRun[TheManager.standardBehavior.size()];
            int index = 0;
            for (Behavior b : TheManager.standardBehavior) {
                OptionMenu.TxtRun run = new OptionMenu.TxtRun(b.name) {
                    @Override
                    protected void run() {
                        Behavior instance = TheManager.newInstanceByName(b.name);
                        if (instance != null && lastEntity != null) {
                            lastEntity.addBehavior(instance);
                            initInspector(lastEntity);
                        }

                    }
                };
                array[index] = run;
                index += 1;
            }

            showMenu(0, mouseX, mouseY, array);
        } else {
            UI.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public void onTick() {
        UI.update();
    }
}
