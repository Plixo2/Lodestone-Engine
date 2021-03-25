package net.plixo.paper.client.avs.components.event.buildIn;


import net.plixo.paper.client.avs.components.event.Event;
import net.plixo.paper.client.avs.components.variable.Variable;
import net.plixo.paper.client.avs.components.variable.VariableType;

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
        this.outputs = new Variable[]{str = new Variable(VariableType.STRING, "Hello")};
        this.size = 1;
        super.setTypes();
    }

}
