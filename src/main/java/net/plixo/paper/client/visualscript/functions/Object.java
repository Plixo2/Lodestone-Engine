package net.plixo.paper.client.visualscript.functions;


import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.simple.SimpleParagraph;
import net.plixo.paper.client.visualscript.Function;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.manager.ScriptManager;
import net.plixo.paper.client.util.Util;

import javax.script.Invocable;
import javax.script.ScriptEngine;

public class Object extends Function {

    Resource<SaveUtil.FileFormat> enumResource;
    Resource<SimpleParagraph> simpleParagraphResource;
    boolean firstTime = true;
    ScriptEngine engine;
    @Override
    public void calculate() {
        pullInputs();
            try {
                if(firstTime) {
                    String input = simpleParagraphResource.value.value;
                    engine = ScriptManager.getNewEngine();
                    engine.eval(input);
                    firstTime = false;
                }
                java.lang.Object obj = invokeFunction("eval");
                output(0,obj);
            } catch (Exception e) {
              Util.print(e);
              e.printStackTrace();
            }
        execute();
    }
    public java.lang.Object invokeFunction(String name, java.lang.Object... objs) {
        try {
            Invocable invocable = (Invocable) engine;
            return invocable.invokeFunction(name, objs);
        } catch (Exception e) {
            Util.print(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set() {
        setInputs();
        setOutputs("Object");
        setLinks();
        String res = System.getProperty("line.separator");
        enumResource = new Resource<>("Type", SaveUtil.FileFormat.Code);
        simpleParagraphResource = new Resource<>("Source", new SimpleParagraph(
                "function eval() {"+res+
                      "return \"Hello World!\";" +res+
                     "}"
        ));
        custom(enumResource,simpleParagraphResource);
    }
}
