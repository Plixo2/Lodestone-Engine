package net.plixo.lodestone.client.visualscript.functions.player;

import net.plixo.lodestone.client.visualscript.Function;

public class FgetGround extends Function {

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
