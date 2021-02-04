package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.ui.other.OptionMenu;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class TabModelViewer extends UITab {
    public TabModelViewer(int id) {
        super(id, "3D-View");
        TheEditor.modelViewer = this;
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        Gui.drawRect(0,0, parent.width, parent.height,  ColorLib.getBackground(0));
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        hideMenu();
        if(mouseButton == 1){
            OptionMenu.TxtRun file = new OptionMenu.TxtRun("Open File") {
                @Override
                protected void run() {
                    
                }
            };
            showMenu(0, mouseX, mouseY, file);
        }
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {

    }

}
