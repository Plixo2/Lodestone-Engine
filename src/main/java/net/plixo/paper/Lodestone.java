package net.plixo.paper;

import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.engine.PaperEngine;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.buildIn.visualscript.VisualScriptManager;
import net.plixo.paper.client.engine.buildIn.visualscript.Module;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;

public class Lodestone {

    static long lastMS = 0;

    public static PaperEngine paperEngine;

    public static void load() {
        TheManager.load();
        TheEditor.init();
    }

    public static void save() {
        Module modToSave = TheEditor.activeMod;
        if (modToSave != null) {
            modToSave.canvas.saveToFile();
        }
        TheManager.save();
    }

    public static void startClient() {
        System.setProperty("java.awt.headless", "false");
        paperEngine = new PaperEngine();
        VisualScriptManager.register();
        TheManager.register();
        load();
    }

    public static void update(String name, Variable var) {

        Lodestone.paperEngine.onEvent(name, var);
        if (System.currentTimeMillis() - lastMS > 60000) {
            if (!paperEngine.isRunning) {
                Lodestone.save();
            }
            lastMS = System.currentTimeMillis();
        }
    }

}
