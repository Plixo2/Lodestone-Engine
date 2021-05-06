package net.plixo.paper.client.engine.behaviors;

import net.minecraft.util.ResourceLocation;
import net.plixo.paper.client.manager.FunctionManager;
import net.plixo.paper.client.avs.newVersion.VisualScript;
import net.plixo.paper.client.avs.newVersion.nFunction;
import net.plixo.paper.client.avs.old.components.Module;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.events.ClientEvent;

import java.io.File;
import java.util.Objects;


public class Visual_Script extends Behavior {

    Module mod;
    VisualScript script;
    nFunction event;
    public Visual_Script() {
        super("Visual Script");
        setSerializableResources(new Resource("File", File.class, null));
    }

    @Override
    public void onEvent(ClientEvent event) {
        if(event instanceof ClientEvent.StopEvent) {
            this.event = null;
            this.script = null;
        }
        if(event instanceof ClientEvent.InitEvent) {

            Resource resource = getResource(0);
            File file = resource.getAsFile();
            Objects.requireNonNull(file);
            if(file.exists()) {
                script = FunctionManager.loadFromFile(file);
                this.event = script.getEvent();
            }
        }
        if(event instanceof ClientEvent.TickEvent) {
            if (this.event != null) {
                this.event.run();
            }
        }

        super.onEvent(event);
    }
}
