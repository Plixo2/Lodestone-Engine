package net.plixo.paper.client.engine.buildIn.visualscript.event;


import net.plixo.paper.client.engine.buildIn.visualscript.function.other.Execute;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;

public abstract class Event extends Execute {


    public Event(String name) {
        super(name);
    }

    public abstract void executePrev(Variable var);

}
