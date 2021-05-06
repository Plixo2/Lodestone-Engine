package net.plixo.paper.client.avs.newVersion;

import net.plixo.paper.client.engine.meta.Meta;
import net.plixo.paper.client.manager.FunctionManager;
import net.plixo.paper.client.util.Util;

public class CustomFunction extends nFunction {

    int metaInputs = 0;
    int metaOutputs = 0;
    int metaLinks = 0;
    public CustomFunction() {
       Meta meta =  FunctionManager.MetaNameMap.get(getName());
       if(meta == null)
       {
           Util.print("Error at loading function");
       } else {
           metaInputs = Util.clamp(meta.get(0).getAsInteger(),99999,0);
           metaOutputs =Util.clamp(meta.get(1).getAsInteger(),99999,0);
           metaLinks = Util.clamp(meta.get(2).getAsInteger(),99999,0);
       }

    }
    @Override
    public void set() {
        set(metaInputs, metaOutputs, metaLinks);
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
