package net.plixo.paper.client.avs.old.components.event;


import net.plixo.paper.client.avs.old.components.variable.Variable;
import net.plixo.paper.client.avs.old.components.function.other.Execute;

public abstract class Event extends Execute {


    public Event(String name) {
        super(name);
    }

    public abstract void executePrev(Variable var);

}
