package net.plixo.lodestone.client.visualscript.functions.logic;

import net.plixo.lodestone.client.visualscript.Function;

public class FBranch extends Function {

    @Override
    public void run() {
        pullInputs();
        execute();
    }

    @Override
    public void set() {
        setInputs();
        setOutputs();
        setLinks("First","Second");
    }
}
