package net.plixo.paper.client.engine.components.visualscript.event;


import net.plixo.paper.client.engine.components.visualscript.function.other.Execute;
import net.plixo.paper.client.engine.components.visualscript.variable.Variable;

public abstract class Event extends Execute {


    public Event(String name) {
        super(name);
    }

    public abstract void executePrev(Variable var);

}
