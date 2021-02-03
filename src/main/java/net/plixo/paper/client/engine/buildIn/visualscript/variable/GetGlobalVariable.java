package net.plixo.paper.client.engine.buildIn.visualscript.variable;


import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.buildIn.visualscript.function.Function;

public class GetGlobalVariable extends Function {

	Variable bool;
	Variable Float;
	Variable Int;
	Variable str;
	Variable vec;

	public GetGlobalVariable() {
		super("getGlobalVariable");

	}

	@Override
	public void execute() {
		if (isNotNull(0)) {
			bool.setValue(TheManager.globals.get(value(0).intValue).booleanValue);
			Float.setValue(TheManager.globals.get(value(0).intValue).floatValue);
			Int.setValue(TheManager.globals.get(value(0).intValue).intValue);
			str.setValue(TheManager.globals.get(value(0).intValue).stringValue);
			vec.setValue(TheManager.globals.get(value(0).intValue).vectorValue);
		}
	}

	@Override
	public void setTypes() {
		this.outputs = new Variable[] { bool = new Variable(VariableType.BOOLEAN, "Boolean"),
				Float = new Variable(VariableType.FLOAT, "Float"),
				Int = new Variable(VariableType.INT, "Int"),
				str = new Variable(VariableType.STRING, "String"),
				vec = new Variable(VariableType.VECTOR, "Vector") };
		this.inputTypes = new VariableType[] { VariableType.INT };
		this.names = new String[] { "Index" };
		super.setTypes();
	}

}
