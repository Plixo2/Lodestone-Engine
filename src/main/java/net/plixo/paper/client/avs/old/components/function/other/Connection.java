package net.plixo.paper.client.avs.old.components.function.other;

import net.plixo.paper.client.avs.old.components.function.Function;
import net.plixo.paper.client.avs.old.components.variable.Variable;

public class Connection {

    public int connectionIndex;
    public Function function;
    public boolean isExecutePin;
    public Variable variable;

    public Connection(Function function, int connectionIndex, boolean isExecutePin) {
        if (!isExecutePin)
            this.variable = function.outputs[connectionIndex];
        this.function = function;
        this.connectionIndex = connectionIndex;
        this.isExecutePin = isExecutePin;
    }
}
