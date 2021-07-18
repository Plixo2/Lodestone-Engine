package net.plixo.paper.client.engine.behaviors;

import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.manager.ScriptManager;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.util.Objects;


public class Java_Addon extends Behavior {

    public transient Runnable runnable;

    Resource<File> fileResource = new Resource<>("File",new File(""));
    public Java_Addon() {
        super("Java Addon");
        setSerializableResources(fileResource);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event instanceof ClientEvent.StopEvent) {
            this.runnable = null;
        }
        if (event instanceof ClientEvent.InitEvent) {
            File file = fileResource.value;
            Objects.requireNonNull(file);
            if (file.exists()) {
                try {
                    Object obj = ScriptManager.loadClassFromFile(file);
                    Objects.requireNonNull(obj);
                    this.runnable = (Runnable) obj;
                } catch (Exception e) {
                    Util.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        if (event instanceof ClientEvent.TickEvent) {
            if (this.runnable != null)
                this.runnable.run();
        }
        super.onEvent(event);
    }
}
