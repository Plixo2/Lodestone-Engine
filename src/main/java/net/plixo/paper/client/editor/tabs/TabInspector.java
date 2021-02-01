package net.plixo.paper.client.editor.tabs;


import net.plixo.paper.Paper;
import net.plixo.paper.client.UI.AbstractAction;
import net.plixo.paper.client.UI.Canvas;
import net.plixo.paper.client.UI.UIElement;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.*;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Entity;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;

public class TabInspector extends UITab {

    Entity lastEntity;
    Canvas UI;
    float yStart = 0;

    public TabInspector(int id) {
        super(id, "Inspector");
        TheEditor.inspector = this;
    }

    @Override
    public void draw(float mouseX, float mouseY) {

        // Gui.drawRect(0, 0, parent.width, parent.height, 0xFF1B1F26);

        UI.draw(mouseX, mouseY);

        int lineColor = ColorLib.getMainColor();
        int width = 2;
        Gui.drawLine(0, 0, parent.width, 0, lineColor, width);
        Gui.drawLine(0, 0, 0, parent.height, lineColor, width);
        Gui.drawLine(0, parent.height, parent.width, parent.height, lineColor, width);
        Gui.drawLine(parent.width, 0, parent.width, parent.height, lineColor, width);
    }

    @Override
    public void init() {
        UI = new Canvas(0);
        UI.setDimensions(0, 0, parent.width, parent.height);
        UI.setRoundness(0);
        UI.setColor(ColorLib.getBackground());
        super.init();
    }

    public void initInspector(Entity entity) {

        try {
            UI.clear();

            UI.add(new UITextbox(-1) {
                @Override
                public void textFieldChanged() {
                    entity.name = getText();
                    TheEditor.initTab(TheEditor.explorer);
                    super.textFieldChanged();
                }
            });

            UI.getLast().setDimensions(0, 0, parent.width, 20);
            ((UITextbox) UI.getLast()).setText(entity.name);

            float y = 20;
            int behaviorIndex = 0;
            for (Behavior b : entity.components) {

                // BehaviorCanvas
                Canvas EntityCanvas = new Canvas(behaviorIndex);
                EntityCanvas.setRoundness(5);
                EntityCanvas.setColor(ColorLib.getBrighter(ColorLib.getBackground()));

                // Head
                EntityCanvas.add(new UIHead(-(1 + behaviorIndex)) {
                    @Override
                    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                        super.mouseClicked(mouseX, mouseY, mouseButton);

                        if (!Paper.paperEngine.isRunning && KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                            int nId = -id - 1;
                            if (entity.components.size() > 0 && nId >= 0 && nId < entity.components.size()) {
                                entity.components.remove(nId);
                                init();
                                initInspector(lastEntity);
                            }
                            //	entity.components.get(index);
                        }
                        //showMenu(id, EntityCanvas.getX()+mouseX, EntityCanvas.getY()+mouseY, "Remove");
                    }
                });
                EntityCanvas.getLast().setDimensions(0, 0, parent.width - 10, 20);
                EntityCanvas.getLast().setDisplayName(b.name);

                float cY = 20;
                int Resindex = 0;
                for (Resource res : b.serializable) {
                    if (res.isFile()) {
                        EntityCanvas.add(new UIFileChooser(Resindex));
                        EntityCanvas.getLast().setDimensions(2, cY, parent.width - 14, 20);
                        ((UIFileChooser) EntityCanvas.getLast()).setFile(res.getAsFile());
                    } else if (res.isInteger()) {
                        EntityCanvas.add(new UISpinner(Resindex));
                        EntityCanvas.getLast().setDimensions(2, cY, parent.width - 14, 20);
                        ((UISpinner) EntityCanvas.getLast()).setNumber(res.getAsInteger());
                    } else if (res.isBoolean()) {
                        EntityCanvas.add(new UIToggleButton(Resindex));
                        EntityCanvas.getLast().setDimensions(2, cY, parent.width - 14, 20);
                        ((UIToggleButton) EntityCanvas.getLast()).setState(res.getAsBoolean());
                        ((UIToggleButton) EntityCanvas.getLast()).setYesNo("True", "False");
                    } else if (res.isString()) {
                        EntityCanvas.add(new UITextbox(Resindex));
                        EntityCanvas.getLast().setDimensions(2, cY, parent.width - 14, 20);
                        ((UITextbox) EntityCanvas.getLast()).setText(res.getAsString());
                    } else if (res.isFloat()) {
                        EntityCanvas.add(new UIPointNumber(Resindex));
                        EntityCanvas.getLast().setDimensions(2, cY, parent.width - 14, 20);
                        ((UIPointNumber) EntityCanvas.getLast()).setValue(res.getAsFloat());
                    } else {
                        EntityCanvas.add(new UIHead(Resindex));
                        EntityCanvas.getLast().setDimensions(2, cY, parent.width - 14, 20);
                    }
                    EntityCanvas.getLast().setDisplayName(res.name);

                    cY += 22;
                    Resindex += 1;
                }

                AbstractAction execution = (int parent, int id, Object element) -> {
                    if (parent >= 0 && parent < entity.components.size()) {
                        if (id >= 0) {
                            Behavior behavior = entity.components.get(parent);
                            if (behavior != null && id < behavior.serializable.length) {
                                Resource res = behavior.serializable[id];
                                if (res.isFile()) {
                                    UIFileChooser el = (UIFileChooser) element;
                                    res.setValue(el.getFile());
                                } else if (res.isInteger()) {
                                    UISpinner el = (UISpinner) element;
                                    res.setValue(el.getNumber());
                                } else if (res.isBoolean()) {
                                    UIToggleButton el = (UIToggleButton) element;
                                    res.setValue(el.getState());
                                } else if (res.isString()) {
                                    UITextbox el = (UITextbox) element;
                                    res.setValue(el.getText());
                                } else if (res.isFloat()) {
                                    UIPointNumber el = (UIPointNumber) element;
                                    res.setValue((float) el.getAsDouble());
                                } else {
                                    System.out.println("Wrong Type");
                                }
                            }
                        }
                    }
                };

                // for height
                EntityCanvas.setDimensions(5, y, parent.width - 10, cY + 5);

                EntityCanvas.setButtonAction(execution);

                UI.add(EntityCanvas);
                y += cY + 7;
                behaviorIndex += 1;
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

        if (!parent.isMouseInside(mouseX, mouseY)) {
            hideMenu();
            return;
        }
        hideMenu();
        if (mouseButton == 0) {
            UI.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (mouseButton == 1) {
            String[] array = new String[TheManager.standartBehavior.size()];

            int index = 0;
            for (Behavior b : TheManager.standartBehavior) {
                array[index] = b.name;
                index += 1;
            }
            showMenu(0, mouseX, mouseY, array);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void optionsSelected(int id, int option) {
        if (lastEntity != null && !Paper.paperEngine.isRunning) {
            if (id == 0) {
                Behavior instance = TheManager.newInstanceByName(parent.menu.options[option]);
                if (instance == null) {
                    System.out.println("Error!!!");
                    return;
                }
                lastEntity.addBehavior(instance);
                initInspector(lastEntity);
            }
        }
        hideMenu();
        super.optionsSelected(id, option);
    }

    @Override
    public void updateScreen() {
        UI.update();
        super.updateScreen();
    }
}