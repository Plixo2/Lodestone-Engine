package net.plixo.paper.client.visualscript.functions.player;


import net.plixo.paper.client.visualscript.Function;
import net.plixo.paper.client.engine.ecs.Resource;

public class Jump extends Function {


    @Override
    public void run() {
        pullInputs();
        mc.player.jump();
        execute();
    }

    @Override
    public void set() {
        setInputs();
        setOutputs();
        setLinks("");
    }
}
