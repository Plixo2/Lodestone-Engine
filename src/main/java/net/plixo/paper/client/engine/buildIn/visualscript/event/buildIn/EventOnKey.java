package net.plixo.paper.client.engine.buildIn.visualscript.event.buildIn;


import net.plixo.paper.client.engine.buildIn.visualscript.event.Event;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.VariableType;
import org.lwjgl.glfw.GLFW;

public class EventOnKey extends Event {

    Variable key;
    Variable name;
    Variable state;

    public EventOnKey() {
        super("onKey");
    }

    @Override
    public void execute() {
    }


    @Override
    public void executePrev(Variable var) {
        key.setValue(var.intValue);
        state.setValue(var.booleanValue);
        String KeyName = GLFW.glfwGetKeyName(var.intValue, 0);
        name.setValue(KeyName);
    }

    @Override
    public void setTypes() {

        this.outputs = new Variable[]{key = new Variable(VariableType.INT, "Index"), state = new Variable(VariableType.BOOLEAN, "State"), name = new Variable(VariableType.STRING, "name")};
        this.size = 1;
        super.setTypes();
    }


}
