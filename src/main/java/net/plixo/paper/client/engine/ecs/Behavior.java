package net.plixo.paper.client.engine.ecs;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.events.ClientEvent;

public abstract class Behavior {

    public static Minecraft mc = Minecraft.getInstance();
    public transient GameObject entity;

    public String name;
    public Resource[] serializable = new Resource[0];

    public Behavior(String name) {
        this.name = name;
    }
    
    public void onEvent(ClientEvent event) {

    }

    public void setSerializableResources(Resource... res) {
        serializable = res;
    }

}
