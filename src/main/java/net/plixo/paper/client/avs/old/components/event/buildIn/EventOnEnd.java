package net.plixo.paper.client.avs.old.components.event.buildIn;


import net.plixo.paper.client.avs.old.components.event.Event;
import net.plixo.paper.client.avs.old.components.variable.Variable;

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
