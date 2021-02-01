package net.plixo.paper.client.engine.buildIn.blueprint.function;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.editor.blueprint.DrawFuntion;
import net.plixo.paper.client.engine.buildIn.blueprint.function.other.Connection;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.VariableType;

public class Function {

	public static Minecraft mc = Minecraft.getInstance();
	public Variable customData;

	public DrawFuntion drawFunction;

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
		for (int index = 0; index < i.length; index++) {
			if (inputs[i[index]] == null) {
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

	public void setDrawFunction(DrawFuntion drawFunction) {
		this.drawFunction = drawFunction;
	}

	public void setTypes() {
		drawFunction.init();
		this.inputs = new Connection[inputTypes.length];
	}

	public Variable value(int index) {
		return inputs[index].variable;
	};

}
