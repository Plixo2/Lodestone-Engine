package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.UICanvas;
import net.plixo.paper.client.manager.TheEditor;
import net.plixo.paper.client.util.*;
import org.lwjgl.opengl.GL11;

import java.io.File;


public class TabModelViewer extends UITab {

    File obj;

    public TabModelViewer(int id) {
        super(id, "3D-View");
        TheEditor.modelViewer = this;
    }

    @Override
    public void init() {
        canvas = new UICanvas(0);
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.1f));
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        if (obj != null) {
            drawName();
        }
        super.drawScreen(mouseX,mouseY);
    }

    private void drawName() {
        String name = obj.getName();
        String fullname = "\u00A7l" + name;
        float nameX = parent.width - Gui.getStringWidth(fullname) * 2 - 20;
        GL11.glPushMatrix();
        GL11.glTranslated(nameX, parent.height - 20, 0);
        GL11.glScaled(2, 2, 0);
        Gui.drawString(fullname, 0, 0, 0xFF000000);
        Gui.drawString(fullname, -1, -1, -1);
        GL11.glPopMatrix();
    }


    public void initViewer(File file) {
        obj = file;
    }

}
