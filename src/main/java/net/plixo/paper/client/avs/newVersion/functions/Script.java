package net.plixo.paper.client.avs.newVersion.functions;


import net.plixo.paper.client.avs.newVersion.nFunction;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.manager.ScriptManager;
import net.plixo.paper.client.util.Util;

import javax.script.ScriptEngine;

public class Script extends nFunction {

    boolean firstTime = true;
    ScriptEngine engine;
    @Override
    public void run() {
        pullInputs();

            try {
                if(firstTime) {
                    String input = setting(0).getAsString();
                    engine = ScriptManager.getNewEngine();
                    engine.eval(input);
                    firstTime = false;
                }
                Object obj = ScriptManager.invokeFunction("eval",engine,input(0),input(1),input(2),input(3),input(4),input(5));
                output(0,obj);
            } catch (Exception e) {
                e.printStackTrace();
              Util.print(e.getMessage());
              e.printStackTrace();
            }
        execute();
    }

    @Override
    public void set() {
        set(6,1,1);
        Resource resource = new Resource("Script",String.class,"function eval(a,b,c,d,e,f) { return !a; }");
        custom(resource);
    }
}
