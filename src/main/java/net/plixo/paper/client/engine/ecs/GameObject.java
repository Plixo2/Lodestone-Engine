package net.plixo.paper.client.engine.ecs;

import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.avs.old.components.variable.Variable;
import net.plixo.paper.client.events.ClientEvent;

import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject {


    public CopyOnWriteArrayList<Behavior> components = new CopyOnWriteArrayList<>();

    public String name;
    public Vector3d position = new Vector3d(0,0,0);
    public Vector3d rotation= new Vector3d(0,0,0);
    public Vector3d scale= new Vector3d(1,1,1);

    public GameObject(String name) {
        this.name = name;
    }


    public void addBehavior(Behavior behavior) {
        this.components.add(behavior);
        behavior.entity = this;
        behavior.onEvent(ClientEvent.InitEvent.event);
    }

    public void onEvent(ClientEvent event) {
        for (Behavior b : components) {
            b.onEvent(event);
        }
    }


}
