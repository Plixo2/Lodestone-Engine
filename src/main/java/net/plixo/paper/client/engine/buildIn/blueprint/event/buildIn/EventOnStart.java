package net.plixo.paper.client.engine.buildIn.blueprint.event.buildIn;


import net.plixo.paper.client.engine.buildIn.blueprint.event.Event;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.VariableType;

public class EventOnStart extends Event {

    Variable str;
    public EventOnStart() {
        super("onStart");
    }


    @Override
    public void execute() {
    }


    @Override
    public void executePrev(Variable var) {
        str.setValue("Hello");
    }

    @Override
    public void setTypes() {
        this.outputs = new Variable[] {str = new Variable(VariableType.STRING , "Hello")};
        this.size = 1;
        super.setTypes();
    }

}
