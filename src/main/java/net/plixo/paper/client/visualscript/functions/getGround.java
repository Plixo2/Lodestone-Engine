package net.plixo.paper.client.visualscript.functions;

import net.plixo.paper.client.visualscript.Function;

public class getGround extends Function {

    @Override
    public void calculate() {
        pullInputs();
        output(0,mc.player.isOnGround());
    }

    @Override
    public void set() {
        set(0,1,0);
    }
}
