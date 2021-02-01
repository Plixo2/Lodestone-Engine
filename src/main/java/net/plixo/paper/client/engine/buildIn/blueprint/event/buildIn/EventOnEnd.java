package net.plixo.paper.client.engine.buildIn.blueprint.event.buildIn;


import net.plixo.paper.client.engine.buildIn.blueprint.event.Event;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.VariableType;

public class EventOnEnd extends Event {


    public EventOnEnd() {
        super("onEnd");
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
