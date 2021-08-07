package net.plixo.lodestone.client.visualscript.functions;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.plixo.lodestone.client.manager.RScript;
import net.plixo.lodestone.client.visualscript.Function;

import javax.script.ScriptEngine;

public class FEvaluate extends Function {

    ScriptEngine engine = RScript.getNewEngine();

    @Override
    public void calculate() {
        pullInputs();
        if (hasInput(0)) {
            String input = ""+input(0);
            try  {

                Object eval = engine.eval(input);
                if(eval instanceof ScriptObjectMirror) {
                    ScriptObjectMirror result = (ScriptObjectMirror)eval;
                    Object[] objects = result.to(Object[].class);
                    eval = objects;
                }
                output(0, eval);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void set() {
        setInputs("Expression");
        setOutputs("value");
        setLinks();
    }
}
