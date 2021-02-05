package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.engine.buildIn.mesh.Mesh;
import net.plixo.paper.client.engine.buildIn.mesh.MeshBehavior;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.util.*;
import org.lwjgl.opengl.GL11;

import java.io.File;


public class TabModelViewer extends UITab {

    Mesh mesh;
    File obj;
    public TabModelViewer(int id) {
        super(id, "3D-View");
        TheEditor.modelViewer = this;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        Gui.drawRect(0, 0, parent.width, parent.height, ColorLib.getBackground(mesh == null ? 0 : 0.5f));


        if (mesh != null) {
            //TODO render Mesh...
            mesh.render();
        }
        if(obj != null) {

         drawName();
        }

    }

    private void drawName() {
        String name = obj.getName();
        String fullname = "\u00A7l" + name;
        float nameX = parent.width - Gui.getStringWidth( fullname) * 2 - 20;
        GL11.glPushMatrix();
        GL11.glTranslated(nameX, parent.height - 20, 0);
        GL11.glScaled(2, 2, 0);
        Gui.drawString(fullname, 0, 0, 0xFF000000);
        Gui.drawString(fullname, -1, -1, -1);
        GL11.glPopMatrix();
    }


    public void initViewer(File file) {
        mesh = MeshUtil.loadFromFile(file);
        obj = file;
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {

    }

}
