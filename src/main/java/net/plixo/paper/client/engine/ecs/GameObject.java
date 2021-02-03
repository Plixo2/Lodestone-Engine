package net.plixo.paper.client.engine.ecs;

import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject {


    public CopyOnWriteArrayList<Behavior> components = new CopyOnWriteArrayList<>();

    public String name;


    public GameObject(String name) {
        this.name = name;
    }


    public void addBehavior(Behavior behavior) {
        this.components.add(behavior);
        behavior.entity = this;
        behavior.init();
    }

    public void init() {
        for (Behavior b : components) {
            b.init();
        }
    }

    public void onEvent(String name, Variable var) {
        for (Behavior b : components) {
            b.onEvent(name, var);
        }
    }

    public void start() {
        for (Behavior b : components) {
            b.start();
        }
    }

    public void stop() {
        for (Behavior b : components) {
            b.stop();
        }
    }

}
