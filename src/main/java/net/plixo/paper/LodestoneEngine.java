package net.plixo.paper;

import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.manager.ClientManager;
import net.plixo.paper.client.util.Util;

public class LodestoneEngine {

    public boolean isRunning = false;

    public void onEvent(ClientEvent event) {
        if (isRunning) {
            for (GameObject e : ClientManager.allEntities) {
                e.onEvent(event);
            }
        }
    }
    public void startEngine() {
        if (!isRunning) {
            isRunning = true;
            System.out.println("Starting Engine");
            Lodestone.save();
            for (GameObject e : ClientManager.allEntities) {
                e.onEvent(ClientEvent.InitEvent.event);
            }
        }
    }

    public void stopEngine() {
        if (isRunning) {
            isRunning = false;
            Util.print("Engine stopped");
            for (GameObject e : ClientManager.allEntities) {
                e.onEvent(new ClientEvent.StopEvent());
            }
            //Reload entities
            AssetLoader.load();
        }
    }

}
