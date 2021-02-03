package net.plixo.paper.client.editor;


import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.tabs.*;
import net.plixo.paper.client.engine.buildIn.visualscript.Module;

public class TheEditor {

    public static Module activeMod = null;
    public static TabConsole console;
    public static TabExplorer explorer;
    public static TabFiles files;
    public static TabInspector inspector;
    public static TabViewport viewport;

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
        //TODO remove this lul
    }

}
