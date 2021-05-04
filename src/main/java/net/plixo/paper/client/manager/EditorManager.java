package net.plixo.paper.client.manager;


import net.plixo.paper.client.editor.tabs.TabViewport;
import net.plixo.paper.client.ui.UITab;
import net.plixo.paper.client.editor.tabs.*;

public class EditorManager {

    public static TabConsole console;
    public static TabExplorer explorer;
    public static TabFiles files;
    public static TabInspector inspector;
    public static TabViewport viewport;
    public static TabEditor editor;

    public static void register() {
        initTab(console);
        initTab(files);
        initTab(explorer);
        initTab(viewport);
        initTab(inspector);
        initTab(editor);
    }

    public static void initTab(UITab tab) {
        if (tab != null) {
            tab.init();
        }
    }

}
