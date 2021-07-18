package net.plixo.paper.client.visualscript.functions.logic;

import net.plixo.paper.client.visualscript.Function;

public class Not extends Function {

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
        setInputs("State");
        setOutputs("Opposite");
        setLinks();
    }
}
