package net.plixo.paper.client.manager;


import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.tabs.*;
import net.plixo.paper.client.ui.tabs.viewport.UIViewport;

public class EditorManager {

    public static UIConsole console;
    public static UIHierarchy explorer;
    public static UIFiles files;
    public static UIInspector inspector;
    public static UIViewport viewport;

    public static void register() {
        initTab(console);
        initTab(files);
        initTab(explorer);
        initTab(viewport);
        initTab(inspector);
    }

    public static void initTab(UITab tab) {
        if (tab != null) {
            tab.init();
        }
    }
    public static void initTab(UICanvas tab) {
        if (tab != null) {
            tab.init();
        }
    }

}
