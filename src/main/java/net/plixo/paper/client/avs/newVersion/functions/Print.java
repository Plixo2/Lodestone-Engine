package net.plixo.paper.client.avs.newVersion.functions;


import net.plixo.paper.client.avs.newVersion.nFunction;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.Util;

public class Print extends nFunction {


    @Override
    public void run() {
        pullInputs();
        Util.print(input(0));
        execute();
    }

    @Override
    public void set() {
        set(1,0,1);
    }
}
