package net.plixo.paper.client.engine.buildIn.blueprint.event.buildIn;


import net.plixo.paper.client.engine.buildIn.blueprint.event.Event;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;

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
