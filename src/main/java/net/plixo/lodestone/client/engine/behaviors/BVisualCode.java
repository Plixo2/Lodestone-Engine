package net.plixo.lodestone.client.engine.behaviors;

import net.plixo.lodestone.client.engine.core.Behavior;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.engine.events.*;
import net.plixo.lodestone.client.manager.RFunctions;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.visualscript.VisualCode;
import net.plixo.lodestone.client.visualscript.injection.classes.CModule;

import java.io.File;


public class BVisualCode extends Behavior {

    CModule eventScript;

    Resource<File> fileResource = new Resource<>("File", new File(""));

    public BVisualCode() {
        super("Visual Code");
        setSerializableResources(fileResource);
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof EInit) {
            File file = fileResource.value;
            VisualCode visualCode = RFunctions.loadFromFile(file);

            if(visualCode.getObject() instanceof CModule) {
                eventScript = (CModule) visualCode.getObject();
            } else {
                UMath.print("this is not from CModule.class");
                return;
            }
            eventScript.startEvent();


        } else if (event instanceof EStop) {
            eventScript.stopEvent();
        } else if (event instanceof ETick) {
            if(eventScript.isEnabled())
            eventScript.tickEvent();
        } else if (event instanceof EKey) {
            EKey keyEvent = ((EKey) event);
           eventScript.keyEvent(keyEvent.key, keyEvent.state, keyEvent.name);
        }
    }
}
