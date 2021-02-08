package net.plixo.paper.client.engine.components.scripting;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.util.Util;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import java.io.File;
import java.io.FileReader;

public class ScriptManager {

    public static ScriptEngine getNewEngine() {
        return Lodestone.paperEngine.scriptEngineManager.getEngineByName("nashorn");
    }

    public static boolean setup(File file , ScriptEngine engine) {
        try {
            engine.eval("var util = Java.type(\"net.plixo.paper.client.util.Util\");");
            engine.eval("var mc = util.mc;");
            engine.eval(new FileReader(file));
            return true;
        } catch (Exception e) {
            Util.print(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static Object findIgnoreCase(String name, ScriptEngine engine) {
        Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        for (String str : b.keySet()) {
            if (str.equalsIgnoreCase(name)) {
                return b.get(str);
            }
        }
        return null;
    }

    public static Object find(String name, ScriptEngine engine) {
        try {
            return engine.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeFunction(String name, ScriptEngine engine , Object... objs) {
        try {
            Invocable invocable = (Invocable) engine;
            Object obj = find(name,engine);
            if (obj == null) {
                return null;
            }
            return  invocable.invokeFunction(name,objs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
