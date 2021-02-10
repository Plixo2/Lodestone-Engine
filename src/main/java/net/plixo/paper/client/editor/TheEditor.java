package net.plixo.paper.client.editor;


import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.tabs.*;
import net.plixo.paper.client.editor.tabs.TabModelViewer;
import net.plixo.paper.client.engine.components.visualscript.Module;

public class TheEditor {

    public static Module activeMod = null;
    public static TabConsole console;
    public static TabExplorer explorer;
    public static TabFiles files;
    public static TabInspector inspector;
    public static TabViewport viewport;
    public static TabModelViewer modelViewer;
    public static TabTimeline timeline;

    public static void init() {
        initTab(console);
        initTab(files);
        initTab(explorer);
        initTab(viewport);
        initTab(inspector);
        initTab(modelViewer);
        initTab(timeline);
    }

    public static void initTab(UITab tab) {
        if (tab != null) {
            tab.init();
        }
    }

}
