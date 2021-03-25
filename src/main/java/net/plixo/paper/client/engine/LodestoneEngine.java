package net.plixo.paper.client.engine;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.avs.components.variable.Variable;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.manager.TheManager;

import javax.script.ScriptEngineManager;

public class LodestoneEngine {


    public boolean isRunning = false;

    public ScriptEngineManager scriptEngineManager = new ScriptEngineManager();


    public void onEvent(String name, Variable var) {
        if (isRunning) {
            for (GameObject e : TheManager.allEntities) {
                e.onEvent(name, var);
            }
        }
    }

    public void render() {
        if (isRunning) {
            for (GameObject e : TheManager.allEntities) {
                e.render();
            }
        }
    }

    public void startEngine() {
        if (!isRunning) {
            isRunning = true;
            System.out.println("Starting Engine");
            Lodestone.save();

            for (GameObject e : TheManager.allEntities) {
                e.start();
            }

        }
    }

    public void stopEngine() {
        if (isRunning) {
            isRunning = false;
            System.out.println("Stoping Engine");
            for (GameObject e : TheManager.allEntities) {
                e.stop();
            }

            Lodestone.load();
        }
    }

}
