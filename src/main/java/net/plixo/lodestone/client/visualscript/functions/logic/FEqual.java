package net.plixo.lodestone.client.visualscript.functions.logic;

import net.plixo.lodestone.client.visualscript.Function;

public class FEqual extends Function {

    @Override
    public void calculate() {
        pullInputs();
        if(hasInput()) {
            Object obj1 = input(0);
            Object obj2 = input(1);
            output(0,obj1 == obj2);
        }

    }

    @Override
    public void set() {
        setInputs("Object 1","Object 2");
        setOutputs("Result");
        setLinks();
    }
}
