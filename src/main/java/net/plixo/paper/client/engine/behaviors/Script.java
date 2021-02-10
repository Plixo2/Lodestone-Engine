package net.plixo.paper.client.engine.behaviors;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.engine.components.scripting.ScriptManager;
import net.plixo.paper.client.engine.components.visualscript.variable.Variable;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FilenameUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.File;
import java.io.FileReader;

public class Script extends Behavior {

    ScriptEngine engine;

    public Script() {
        super("Script");
        setSerializableResources(new Resource("Javascript", File.class, null));
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void onEvent(String name, Variable var) {
        ScriptManager.invokeFunction(name , engine);
        super.onEvent(name, var);
    }

    @Override
    public void start() {
        Resource res = getResource(0);
        engine = ScriptManager.getNewEngine();
        if (res.hasValue()) {
            File file = res.getAsFile();
            String extension = FilenameUtils.getExtension(file.getName());
            if (!extension.equalsIgnoreCase(SaveUtil.FileFormat.Code.format)) {
                Util.print("Wrong Format");
                Lodestone.paperEngine.stopEngine();
                return;
            }
            ScriptManager.setup(file, engine);
            ScriptManager.invokeFunction("onStart" , engine);
        }

        super.start();
    }


    @Override
    public void stop() {
        ScriptManager.invokeFunction("onStop" , engine);
        super.stop();
    }

    //TODO redo variable system to javascript
}
