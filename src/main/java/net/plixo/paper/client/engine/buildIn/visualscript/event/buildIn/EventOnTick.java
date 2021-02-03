package net.plixo.paper.client.engine.buildIn.visualscript.event.buildIn;


import net.plixo.paper.client.engine.buildIn.visualscript.event.Event;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;

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
