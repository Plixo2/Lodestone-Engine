package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
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
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRect(0, 0, parent.width, parent.height, ColorLib.getBackground(obj == null ? 0 : 0.5f));


        if (obj != null) {
            drawName();
        }

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
