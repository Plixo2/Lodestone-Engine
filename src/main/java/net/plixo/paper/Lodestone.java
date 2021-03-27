package net.plixo.paper;

import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.manager.ClientManager;

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
     * Calls different initialises and the load function of {@link ClientManager}.
     */
    public static void load() {
        ClientManager.register();
        ClientManager.load();
        EditorManager.init();
    }

    /**
     * Setups the save function.
     * Calls the save function in {@link ClientManager}.
     */
    public static void save() {
        ClientManager.save();
    }

    public static void update(ClientEvent event) {

        Lodestone.lodestoneEngine.onEvent(event);
    }

}
