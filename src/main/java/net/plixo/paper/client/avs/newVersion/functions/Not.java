package net.plixo.paper.client.avs.newVersion.functions;

import net.plixo.paper.client.avs.newVersion.nFunction;

public class Not extends nFunction {

    @Override
    public void calculate() {
        pullInputs();
        if(hasInput(0)) {
            boolean obj1 = input(0,true);
            output(0, !obj1);
        }

    }

    @Override
    public void set() {
        set(1,1,0);
    }
}
