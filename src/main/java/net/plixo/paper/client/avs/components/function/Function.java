package net.plixo.paper.client.avs.components.function;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.avs.ui.DrawFunction;
import net.plixo.paper.client.avs.components.function.other.Connection;
import net.plixo.paper.client.avs.components.variable.Variable;
import net.plixo.paper.client.avs.components.variable.VariableType;

public class Function {

    //temporary
    public float x,y;

    public static Minecraft mc = Minecraft.getInstance();
    public Variable customData;
    public boolean hasCalculated = false;
    public Connection[] inputs = new Connection[0];
    public VariableType[] inputTypes = new VariableType[0];
    public String name;
    public String[] names;
    public Variable[] outputs = new Variable[0];

    public Function(String name) {
        this.name = name;
    }

    public void execute() {
    }

    public boolean isNotNull(int... i) {
        for (int j : i) {
            if (inputs[j] == null) {
                return false;
            }
        }
        return true;
    }

    public void reTrace() {
        for (Connection supFunctions : inputs) {
            if (supFunctions != null && !supFunctions.function.hasCalculated) {
                if (supFunctions.function.inputTypes.length > 0) {
                    supFunctions.function.reTrace();
                }
                supFunctions.function.execute();
                supFunctions.function.hasCalculated = true;
            }
        }
    }

    public void setTypes() {
        this.inputs = new Connection[inputTypes.length];
    }

    public Variable value(int index) {
        return inputs[index].variable;
    }
}
