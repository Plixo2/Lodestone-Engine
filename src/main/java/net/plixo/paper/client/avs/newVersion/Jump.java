package net.plixo.paper.client.avs.newVersion;


import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.Util;

public class Jump extends nFunction {


    @Override
    public void run() {
        pullInputs();
        int speed = setting(0).getAsInteger();
        for (int i = 0; i < speed; i++) {
            mc.player.jump();
        }
        execute();
    }

    @Override
    public void set() {
        set(0,0,1);
        Resource resource = new Resource("Jump Speed",Integer.class,1);
        custom(resource);
    }
}
