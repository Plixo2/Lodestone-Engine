package net.plixo.paper.client.visualscript.functions.player;

import net.plixo.paper.client.visualscript.Function;

public class getGround extends Function {

    @Override
    public void calculate() {
        pullInputs();
        output(0,mc.player.isOnGround());
    }

    @Override
    public void set() {
        setInputs();
        setOutputs("Ground");
        setLinks();
    }
}
