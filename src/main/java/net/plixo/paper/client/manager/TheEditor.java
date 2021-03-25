package net.plixo.paper.client.manager;


import net.plixo.paper.client.avs.TabViewport;
import net.plixo.paper.client.avs.newVersion.Viewport;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.editor.tabs.*;
import net.plixo.paper.client.editor.tabs.TabModelViewer;
import net.plixo.paper.client.avs.components.Module;

public class TheEditor {

   // public static Module activeMod = null;
    public static TabConsole console;
    public static TabExplorer explorer;
    public static TabFiles files;
    public static TabInspector inspector;
    public static Viewport viewport;
    public static TabModelViewer modelViewer;

    public static void init() {
        initTab(console);
        initTab(files);
        initTab(explorer);
        initTab(viewport);
        initTab(inspector);
        initTab(modelViewer);
    }

    public static void initTab(UITab tab) {
        if (tab != null) {
            tab.init();
        }
    }

}
