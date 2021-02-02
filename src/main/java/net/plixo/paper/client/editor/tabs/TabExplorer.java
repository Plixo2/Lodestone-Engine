package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.Paper;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.blueprint.Rect;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import net.plixo.paper.client.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;


public class TabExplorer extends UITab {

    class EntityBox extends Rect {

        GameObject entity;
        public boolean state;

        public EntityBox(GameObject entity, boolean state, float x, float y, float width, float height) {
            super(x, y, width, height, 0xFF32a852, 0xFF328ca8);
            this.entity = entity;
            this.state = state;
            this.setTxt(" -" + entity.name, Alignment.LEFT);
            this.roundness = 0;
        }

    }

    ArrayList<EntityBox> BoxList = new ArrayList<EntityBox>();

    GameObject selectedEntity;

    public TabExplorer(int id) {
        super(id, "Explorer");
        TheEditor.explorer = this;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

    Gui.drawRect(0, 0, parent.width, parent.height, ColorLib.getBackground(0.2f));

        for (EntityBox box : BoxList) {
            box.draw(mouseX, mouseY);
        }

     //  drawOutline();
    }

    @Override
    public void init() {

        BoxList.clear();
        float y = 0;
        for (GameObject e : TheManager.allEntitys) {
            EntityBox box = new EntityBox(e, false, 0, y, parent.width, 20);
            BoxList.add(box);
            y += 20;
        }

        super.init();
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        hideMenu();
        if (!parent.isMouseInside(mouseX, mouseY)) {
            return;
        }
        for (EntityBox box : BoxList) {
            if (box.mouseInside(mouseX, mouseY, mouseButton)) {
                if (mouseButton == 0) {

                    if (KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                        GameObject e = box.entity;
                        TheManager.removeEntity(e);
                        init();
                        break;
                    } else {
                        TheEditor.inspector.initInspector(box.entity);
                    }
                }
                return;
            }
        }
        if (mouseButton == 1) {
            showMenu(0, mouseX, mouseY, "New Entity");
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void optionsSelected(int id, int option) {
        if (id == 0 && option == 0 && !Paper.paperEngine.isRunning) {
            GameObject entity = new GameObject("NewEntity" + TheManager.allEntitys.size());
            TheManager.addEntity(entity);
            init();
        }
        super.optionsSelected(id, option);
    }
}
