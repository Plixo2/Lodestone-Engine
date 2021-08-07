package net.plixo.lodestone.client.manager;


import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.tabs.*;

public class REditor {

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

    public static void initTab(UICanvas tab) {
        if (tab != null) {
            tab.init();
        }
    }

}
