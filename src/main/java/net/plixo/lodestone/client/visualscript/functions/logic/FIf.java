package net.plixo.lodestone.client.visualscript.functions.logic;

import net.plixo.lodestone.client.visualscript.Function;

public class FIf extends Function {
    @Override
    public void run() {
        pullInputs();
        if(hasInput(0)) {
            boolean state = input(0, false);
            execute(state ? 0 : 1);
        }
    }

    @Override
    public void set() {
        setInputs("Condition");
        setOutputs();
        setLinks("True","False");
    }
}
