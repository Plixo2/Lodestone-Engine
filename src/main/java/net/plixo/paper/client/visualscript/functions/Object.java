package net.plixo.paper.client.visualscript.functions;


import net.plixo.paper.client.visualscript.Function;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.manager.ScriptManager;
import net.plixo.paper.client.util.Util;

import javax.script.ScriptEngine;

public class Object extends Function {

    boolean firstTime = true;
    ScriptEngine engine;
    @Override
    public void calculate() {
        pullInputs();
            try {
                if(firstTime) {
                    String input = "function eval() { return " + setting(0).getAsString() + "; } ";
                    engine = ScriptManager.getNewEngine();
                    engine.eval(input);
                    firstTime = false;
                }
                java.lang.Object obj = ScriptManager.invokeFunction("eval",engine);
                output(0,obj);
            } catch (Exception e) {
              Util.print(e);
              e.printStackTrace();
            }
        execute();
    }

    @Override
    public void set() {
        set(0,1,0);
        Resource resource = new Resource("Script",String.class,"Math.random()");
        custom(resource);
    }
}
