package net.plixo.paper;

import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.engine.PaperEngine;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.components.visualscript.Module;
import net.plixo.paper.client.engine.components.visualscript.VisualScriptManager;
import net.plixo.paper.client.engine.components.visualscript.variable.Variable;

/**
 * Second main class.
 * Used for functions independent from forge.
 */
public class Lodestone {
    static long lastMS = 0;
    public static PaperEngine paperEngine;


    /**
     * First function called.
     * Initialises {@link PaperEngine} parameter.
     * Calls load function.
     */
    public static void startClient() {
        System.setProperty("java.awt.headless", "false");
        paperEngine = new PaperEngine();
        load();
    }

    /**
     * Calls different initialises and the load function of {@link TheManager}.
     */
    public static void load() {
        VisualScriptManager.register();
        TheManager.register();
        TheManager.load();
        TheEditor.init();
    }

    /**
     * Setups the save function.
     * Calls the save function in {@link TheManager}.
     */
    public static void save() {
        Module modToSave = TheEditor.activeMod;
        if (modToSave != null) {
            modToSave.canvas.saveToFile();
        }
        if(TheEditor.timeline != null && TheEditor.timeline.currentTimeline != null) {
            TheEditor.timeline.currentTimeline.saveToFile();
        }
        TheManager.save();
    }

    /**
     * Event handling.
     *
     * @param name Event name.
     * @param var Event data.
     */
    public static void update(String name, Variable var) {

        Lodestone.paperEngine.onEvent(name, var);
        if (System.currentTimeMillis() - lastMS > 60000) {
            if (!paperEngine.isRunning) {
                Lodestone.save();
            }
            lastMS = System.currentTimeMillis();
        }
    }

    /**
     * Render handling.
     */
    public static void render() {
        Lodestone.paperEngine.render();
    }


}
