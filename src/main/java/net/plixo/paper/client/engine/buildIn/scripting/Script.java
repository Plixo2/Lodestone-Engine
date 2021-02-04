package net.plixo.paper.client.engine.buildIn.scripting;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;
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
        execute(name, var);
        super.onEvent(name, var);
    }

    @Override
    public void start() {

        Resource res = getResource(0);
        engine = Lodestone.paperEngine.scriptEngineManager.getEngineByName("nashorn");

        if (res.hasValue()) {
            try {
                File file = res.getAsFile();
                String extension = FilenameUtils.getExtension(file.getName());
                if (!extension.equalsIgnoreCase(SaveUtil.FileFormat.Code.format)) {
                    Util.print("Wrong Format");
                    Lodestone.paperEngine.stopEngine();
                    return;
                }
                engine.eval(new FileReader(file));

                //Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);

                execute("onStart", null);

            } catch (Exception e) {
                Util.print(e.getMessage());
                Lodestone.paperEngine.stopEngine();
                e.printStackTrace();
            }
        }

        super.start();
    }


    @Override
    public void stop() {
        execute("onStop", null);
        super.stop();
    }

    @SuppressWarnings("unused")
	public void execute(String name, Variable var) {
        try {
            Invocable invocable = (Invocable) engine;
            Object obj = engine.get(name);
            if (obj != null) invocable.invokeFunction(name, mc);
        } catch (Exception e) {
            Util.print(e.getMessage());
            Lodestone.paperEngine.stopEngine();
            e.printStackTrace();
        }
    }
    //TODO redo variable system to javascript
}
