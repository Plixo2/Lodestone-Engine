package net.plixo.paper.client.avs.old.components.event.buildIn;


import net.plixo.paper.client.avs.old.components.variable.Variable;
import net.plixo.paper.client.avs.old.components.event.Event;

public class EventOnTick extends Event {

    public EventOnTick() {
        super("onTick");

    }


    @Override
    public void execute() {
    }


    @Override
    public void executePrev(Variable var) {
    }

    @Override
    public void setTypes() {
        this.size = 1;
        super.setTypes();
    }

}
