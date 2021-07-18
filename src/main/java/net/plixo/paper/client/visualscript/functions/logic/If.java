package net.plixo.paper.client.visualscript.functions.logic;

import net.plixo.paper.client.visualscript.Function;

public class If extends Function {
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
