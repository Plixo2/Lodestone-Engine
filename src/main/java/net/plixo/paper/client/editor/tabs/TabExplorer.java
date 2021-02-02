package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.Paper;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.UI.elements.UIButton;
import net.plixo.paper.client.UI.elements.UICanvas;
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



    UICanvas canvas;

    GameObject selectedEntity;

    public TabExplorer(int id) {
        super(id, "Explorer");
        TheEditor.explorer = this;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRect(0, 0, parent.width, parent.height, ColorLib.getBackground(0.2f));

        canvas.draw(mouseX,mouseY);

     //  drawOutline();
    }

    @Override
    public void init() {

        canvas = new UICanvas(0);

        int y = 0;
        for(GameObject obj : TheManager.allEntitys) {
            UIButton button = new UIButton(0) {
                @Override
                public void actionPerformed() {
                    TheEditor.inspector.initInspector(obj);
                    if(KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                        TheManager.allEntitys.remove(obj);
                        init();
                    }
                }
            };
            button.setDimensions(0,y, parent.width,20);
            button.setDisplayName(obj.name);
            button.setRoundness(0);
            button.setColor(y % 40 == 0 ? ColorLib.blue() : ColorLib.getDarker(ColorLib.blue()));

            canvas.add(button);
             y+= 20;
        }


            super.init();
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        hideMenu();
        canvas.mouseClicked(mouseX,mouseY,mouseButton);

        if (mouseButton == 1 && parent.isMouseInside(mouseX,mouseY)) {
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
