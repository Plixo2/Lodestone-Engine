package net.plixo.lodestone.client.visualscript.functions.values;


import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.plixo.lodestone.client.manager.RScript;
import net.plixo.lodestone.client.ui.resource.util.SimpleParagraph;
import net.plixo.lodestone.client.visualscript.Function;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.util.UMath;

import javax.script.Invocable;
import javax.script.ScriptEngine;

public class FScript extends Function {

    Resource<SimpleParagraph> simpleParagraphResource;
    boolean firstTime = true;
    ScriptEngine engine;
    @Override
    public void calculate() {
        pullInputs();
            try {
                if(firstTime) {
                    String input = simpleParagraphResource.value.value;
                    engine = RScript.getNewEngine();
                    engine.eval(input);  engine = RScript.getNewEngine();
                    engine.eval(input);
                    firstTime = false;
                }
                Object[] objects = new java.lang.Object[getInput().length];
                for (int i = 0; i < getInput().length; i++) {
                    objects[i] = input(i);
                }
               Object obj = invokeFunction("eval",objects);
                if(obj instanceof ScriptObjectMirror) {
                    ScriptObjectMirror result = (ScriptObjectMirror)obj;
                    obj = result.to(Object[].class);
                }
                output(0,obj);
            } catch (Exception e) {
              UMath.print(e);
              e.printStackTrace();
            }
    }

    @Override
    public void run() {
        calculate();
        execute();
    }

    public java.lang.Object invokeFunction(String name, java.lang.Object... objs) {
        try {
            Invocable invocable = (Invocable) engine;
            return invocable.invokeFunction(name, objs);
        } catch (Exception e) {
            UMath.print(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set() {
        setInputs("a","b","c","d","e");
        setOutputs("Object");
        setLinks("");
        String res = System.getProperty("line.separator");
        simpleParagraphResource = new Resource<>("Source", new SimpleParagraph(
                "function eval(a,b,c,d,e) {"+res+
                      "return \"Hello World!\";" +res+
                     "}"
        ));
        setSettings(simpleParagraphResource);
    }
}
