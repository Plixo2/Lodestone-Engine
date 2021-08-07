package net.plixo.lodestone.client.engine.behaviors;

import net.plixo.lodestone.client.engine.core.Behavior;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.engine.events.Event;
import net.plixo.lodestone.client.engine.events.EInit;
import net.plixo.lodestone.client.engine.events.EStop;
import net.plixo.lodestone.client.engine.events.ETick;
import net.plixo.lodestone.client.manager.RScript;
import net.plixo.lodestone.client.util.UMath;

import java.io.File;
import java.util.Objects;


public class BJava extends Behavior {

    transient Runnable runnable;

    Resource<File> fileResource = new Resource<>("File",new File(""));
    public BJava() {
        super("Java Addon");
        setSerializableResources(fileResource);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EStop) {
            this.runnable = null;
        }
        if (event instanceof EInit) {
            File file = fileResource.value;
            Objects.requireNonNull(file);
            if (file.exists()) {
                try {
                    Object obj = RScript.loadClassFromFile(file);
                    Objects.requireNonNull(obj);
                    this.runnable = (Runnable) obj;
                } catch (Exception e) {
                    UMath.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        if (event instanceof ETick) {
            if (this.runnable != null)
                this.runnable.run();
        }
    }
}
