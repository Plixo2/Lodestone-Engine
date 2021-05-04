package net.plixo.paper.client.avs.newVersion.functions;

import net.plixo.paper.client.avs.newVersion.nFunction;

public class Equal extends nFunction {

    @Override
    public void calculate() {
        pullInputs();
        if(hasInput(0)) {
            Object obj1 = input(0);
            Object obj2 = input(1);
            output(0,obj1 == obj2);
        }

    }

    @Override
    public void set() {
        set(2,1,0);
    }
}
