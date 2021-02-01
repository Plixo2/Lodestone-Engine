package net.plixo.paper.client.engine.buildIn.blueprint.event;


import net.plixo.paper.client.engine.buildIn.blueprint.function.other.Execute;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;

public abstract class Event extends Execute {

	
	public Event(String name) {
		super(name);
	}
	
	public abstract void executePrev(Variable var);

 }
