package net.plixo.paper.client.visualscript.functions.events;

public class StartEvent extends Event {

    @Override
    public void set() {
        set(0,0,1);
    }

    @Override
    public void run() {
        execute();
    }
}
