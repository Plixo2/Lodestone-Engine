package net.plixo.paper.client.manager;


import net.plixo.paper.client.avs.newVersion.Viewport;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.editor.tabs.*;

public class EditorManager {

   // public static Module activeMod = null;
    public static TabConsole console;
    public static TabExplorer explorer;
    public static TabFiles files;
    public static TabInspector inspector;
    public static Viewport viewport;

    public static void init() {
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

}
