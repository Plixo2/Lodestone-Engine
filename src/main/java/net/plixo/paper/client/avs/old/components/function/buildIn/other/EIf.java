package net.plixo.paper.client.avs.old.components.function.buildIn.other;


import net.plixo.paper.client.avs.old.components.function.other.Execute;
import net.plixo.paper.client.avs.old.components.variable.VariableType;

public class EIf extends Execute {

    public EIf() {
        super("If");
    }

    @Override
    public void execute() {

    }

    void next(int index) {
        if (nextConnection != null) {

            Execute next = nextConnection[index];
            if (next != null) {
                next.hasCalculated = true;

                next.reTrace();
                next.execute();
                next.postExecute();
            }
        }
    }

    @Override
    public void postExecute() {
        if (isNotNull(0)) {
            next(value(0).booleanValue ? 0 : 1);
        }
    }

    @Override
    public void setTypes() {
        this.inputTypes = new VariableType[1];
        this.inputTypes[0] = VariableType.BOOLEAN;
        this.names = new String[] {"condition"};
        this.size = 2;
        super.setTypes();
    }

}