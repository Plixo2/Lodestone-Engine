package net.plixo.lodestone.client.engine.core;

import net.minecraft.client.Minecraft;
import net.plixo.lodestone.client.engine.events.Event;

public abstract class Behavior {

    public static Minecraft mc = Minecraft.getInstance();
    public transient GameObject entity;

    public String name;
    public Resource<?>[] serializable = new Resource[0];
    public void setSerializableResources(Resource<?>... res) {
        serializable = res;
    }
    public Behavior(String name) {
        this.name = name;
    }
    
    public abstract void onEvent(Event event);
}
