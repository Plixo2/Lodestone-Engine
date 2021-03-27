package net.plixo.paper.client.engine.behaviors;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.avs.old.components.Module;
import net.plixo.paper.client.avs.old.components.variable.Variable;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FilenameUtils;

import java.io.File;


public class VisualScript extends Behavior {

    Module mod;

    public VisualScript() {
        super("Visual Script");
        setSerializableResources(new Resource("File", File.class, null));
    }

}
