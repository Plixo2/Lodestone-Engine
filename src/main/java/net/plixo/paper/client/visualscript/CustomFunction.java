package net.plixo.paper.client.visualscript;

import net.plixo.paper.client.engine.ecs.Meta;
import net.plixo.paper.client.manager.FunctionManager;
import net.plixo.paper.client.util.Util;

import java.util.Arrays;

public abstract class CustomFunction extends Function {

    int metaInputs = 0;
    int metaOutputs = 0;
    int metaLinks = 0;

    public CustomFunction() {
        Meta meta = FunctionManager.MetaNameMap.get(getName());
        if (meta == null) {
            Util.print("Error at loading function");
        } else {
           // metaInputs = Util.clamp(meta.get(0).getAsInteger(), 9, 0);
          //  metaOutputs = Util.clamp(meta.get(1).getAsInteger(), 9, 0);
           // metaLinks = Util.clamp(meta.get(2).getAsInteger(), 9, 0);
        }
    }

    @Override
    public void set() {
        setInputs(getEmpty(metaInputs,"Input"));
        setOutputs(getEmpty(metaOutputs,"Output"));
        setLinks(getEmpty(metaLinks,"Link"));
    }

    public String[] getEmpty(int length,String name) {
        String[] inputs = new String[length];
        Arrays.fill(inputs, name);
        return inputs;
    }

    @Override
    public void run() {
        pullInputs();
        link();
        execute();
    }

    @Override
    public void calculate() {
        pullInputs();
        out();
    }

    public void out() {

    }

    public void link() {

    }

    public void print(Object obj) {
        Util.print(obj);
    }
}
