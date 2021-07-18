package net.plixo.paper.client.visualscript.functions.events;

public class TickEvent extends Event {

    @Override
    public void set() {
        setInputs();
        setOutputs();
        setLinks("Event");
    }

    @Override
    public void run() {
        execute();
    }
}
