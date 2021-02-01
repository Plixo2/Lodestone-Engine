package net.plixo.paper.client.editor;


import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.tabs.*;
import net.plixo.paper.client.engine.buildIn.blueprint.Module;

public class TheEditor {

	public static Module activeMod = null;
	public static TabConsole console;
	public static TabExplorer explorer;
	public static TabFiles files;
	public static TabHud hud;
	public static TabInspector inspector;

	public static TabViewport viewport;

	public static void init() {
		initTab(console);
		initTab(files);
		initTab(hud);
		initTab(explorer);
		initTab(viewport);
		initTab(inspector);
	}

	public static void initTab(UITab tab) {
		if (tab != null) {
			tab.init();
		}
	}

	public static void printError(Object obj) {
		printLn("�4: " + obj);
	}

	public static void printLn(Object obj) {
		console.consoleLines.add(new TabConsole.ConsoleLine(obj + ""));
	}
}
