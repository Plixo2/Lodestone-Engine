package net.plixo.lodestone;

import net.plixo.lodestone.client.engine.core.GameObject;
import net.plixo.lodestone.client.engine.events.Event;
import net.plixo.lodestone.client.engine.events.EInit;
import net.plixo.lodestone.client.engine.events.EStop;
import net.plixo.lodestone.client.manager.RAssets;
import net.plixo.lodestone.client.manager.RClient;

public class LodestoneEngine {

    public boolean isRunning = false;

    public void onEvent(Event event) {
        if (isRunning) {
            for (GameObject e : RClient.allEntities) {
                e.onEvent(event);
            }
        }
    }
    public void startEngine() {
        if (!isRunning) {
            isRunning = true;
            Lodestone.save();
            for (GameObject e : RClient.allEntities) {
                e.onEvent(new EInit());
            }
        }
    }

    public void stopEngine() {
        if (isRunning) {
            isRunning = false;
            for (GameObject e : RClient.allEntities) {
                e.onEvent(new EStop());
            }
            //Reload entities
            RAssets.load();
        }
    }
}
