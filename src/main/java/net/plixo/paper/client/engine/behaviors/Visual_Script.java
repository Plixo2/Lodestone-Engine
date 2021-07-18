package net.plixo.paper.client.engine.behaviors;

import net.plixo.paper.client.manager.FunctionManager;
import net.plixo.paper.client.visualscript.VisualScript;
import net.plixo.paper.client.visualscript.functions.events.*;
import net.plixo.paper.client.visualscript.Function;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.events.ClientEvent;

import java.io.File;
import java.util.Objects;


public class Visual_Script extends Behavior {

    VisualScript script;
    TickEvent tickEvent;
    KeyEvent keyEvent;
    StopEvent stopEvent;

    Resource<File> fileResource = new Resource<>("File",new File(""));
    public Visual_Script() {
        super("Visual Script");
        setSerializableResources(fileResource);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if(event instanceof ClientEvent.StopEvent) {
            if (stopEvent != null) {
                stopEvent.run();
            }
            this.tickEvent = null;
            this.keyEvent = null;
            this.script = null;
        }

        if(event instanceof ClientEvent.InitEvent) {
            File file =  fileResource.value;
            Objects.requireNonNull(file);
            if(file.exists()) {
                script = FunctionManager.loadFromFile(file);

                for (Function function : script.functions) {
                    if (function instanceof TickEvent) {
                        tickEvent = ((TickEvent) function);
                    }
                    if (function instanceof KeyEvent) {
                        keyEvent = ((KeyEvent) function);
                    }
                    if(function instanceof StopEvent) {
                        stopEvent = ((StopEvent) function);
                    }
                    if (function instanceof StartEvent) {
                        function.run();
                        System.out.println("Start event");
                    }
                }
            }
        }
        if(event instanceof ClientEvent.TickEvent) {
            if (this.tickEvent != null) {
                this.tickEvent.run();
            }
        }
        if(event instanceof ClientEvent.KeyEvent)  {
            if (keyEvent != null) {
                ClientEvent.KeyEvent keyEvent = ((ClientEvent.KeyEvent) event);
                this.keyEvent.runWith(keyEvent.key,keyEvent.state,keyEvent.name);
            }
        }
        super.onEvent(event);
    }
}
