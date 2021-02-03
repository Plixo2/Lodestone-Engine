package net.plixo.paper.client.engine;

import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.VariableType;

import java.util.ArrayList;

public class UniformFunction {

    ArrayList<Variable> outputs = new ArrayList<>();
    public Variable output = new Variable(VariableType.BOOLEAN, "output");
    String name;

    public UniformFunction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addVariable(Variable var) {
        this.outputs.add(var);
    }

    public ArrayList<Variable> variableArrayList() {
        return outputs;
    }

    @SuppressWarnings("unused")
    public void execute(Variable... vars) {
        //TODO javascript and blueprint function
    }

    //TODO add select function
    //TODO add rightClick option menu in viewport for options
}
