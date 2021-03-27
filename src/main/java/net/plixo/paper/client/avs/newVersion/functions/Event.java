package net.plixo.paper.client.avs.newVersion.functions;

import net.plixo.paper.client.avs.newVersion.nFunction;

public class Event extends nFunction {
    @Override
    public void set() {
        set(0,0,1);
    }

    @Override
    public void run() {
        execute();
    }
}
