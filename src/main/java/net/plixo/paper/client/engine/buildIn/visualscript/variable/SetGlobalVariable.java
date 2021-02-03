package net.plixo.paper.client.engine.buildIn.visualscript.variable;


import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.buildIn.visualscript.function.other.Execute;

@SuppressWarnings("unused")
public class SetGlobalVariable extends Execute {

    public SetGlobalVariable() {
        super("setGlobalVariable");
    }


    @Override
    public void execute() {
        if (isNotNull(0, 1)) {
            int index = value(0).intValue;
            Vector3d value = value(1).vectorValue;
            TheManager.globals.get(index).vectorValue = new Vector3d(value.x, value.y, value.z);

        }
    }

    @Override
    public void setTypes() {

        this.inputTypes = new VariableType[]{VariableType.INT, VariableType.BOOLEAN, VariableType.FLOAT, VariableType.INT, VariableType.STRING, VariableType.VECTOR};
        this.names = new String[]{"Index", "Boolean", "Float", "Int", "String", "Vector"};
        this.size = 1;
        super.setTypes();
    }

}
