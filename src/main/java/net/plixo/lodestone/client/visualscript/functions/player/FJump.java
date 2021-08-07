package net.plixo.lodestone.client.visualscript.functions.player;


import net.plixo.lodestone.client.visualscript.Function;

public class FJump extends Function {


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
