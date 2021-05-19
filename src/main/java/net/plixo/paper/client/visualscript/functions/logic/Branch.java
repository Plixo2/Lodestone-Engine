package net.plixo.paper.client.visualscript.functions.logic;

import net.plixo.paper.client.visualscript.Function;

public class Branch extends Function {

    @Override
    public void run() {
        pullInputs();
        execute();
    }

    @Override
    public void set() {
        set(0, 0, 2);
    }
}
