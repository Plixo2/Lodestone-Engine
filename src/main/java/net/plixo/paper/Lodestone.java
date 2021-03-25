package net.plixo.paper;

import net.plixo.paper.client.manager.TheEditor;
import net.plixo.paper.client.engine.LodestoneEngine;
import net.plixo.paper.client.manager.TheManager;
import net.plixo.paper.client.avs.components.Module;
import net.plixo.paper.client.manager.VisualScriptManager;
import net.plixo.paper.client.avs.components.variable.Variable;

/**
 * Second main class.
 * Used for functions independent from forge.
 */
public class Lodestone {
    static long lastMS = 0;
    public static LodestoneEngine lodestoneEngine;


    /**
     * First function called.
     * Initialises {@link LodestoneEngine} parameter.
     * Calls load function.
     */
    public static void startClient() {
        System.setProperty("java.awt.headless", "false");
        lodestoneEngine = new LodestoneEngine();
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
        /*
        Module modToSave = TheEditor.activeMod;
        if (modToSave != null) {
            modToSave.canvas.saveToFile();
        }
         */
        /*
        if(TheEditor.timeline != null && TheEditor.timeline.currentTimeline != null) {
            TheEditor.timeline.currentTimeline.saveToFile();
        }

         */
        TheManager.save();
    }

    /**
     * Event handling.
     *
     * @param name Event name.
     * @param var  Event data.
     */
    public static void update(String name, Variable var) {

        Lodestone.lodestoneEngine.onEvent(name, var);
        if (System.currentTimeMillis() - lastMS > 60000) {
            if (!lodestoneEngine.isRunning) {
                Lodestone.save();
            }
            lastMS = System.currentTimeMillis();
        }
    }

    /**
     * Render handling.
     */
    public static void render() {
        Lodestone.lodestoneEngine.render();
    }


}
