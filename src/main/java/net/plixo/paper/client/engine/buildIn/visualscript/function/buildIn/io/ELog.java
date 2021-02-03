package net.plixo.paper.client.engine.buildIn.visualscript.function.buildIn.io;


import net.plixo.paper.client.engine.buildIn.visualscript.function.other.Execute;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.VariableType;
import net.plixo.paper.client.util.Util;

public class ELog extends Execute {

	public ELog() {
		super("Log");
	}

	@Override
	public void execute() {

		for(int i = 0; i< this.inputTypes.length; i++) {
			if(isNotNull(i)) {
				log(value(i));
			}
		}
		
	}

	void log(Variable var)  {
		Util.print(var.type.name() + ": " + var.toString());
	}
	
	@Override
	public void setTypes() {
		this.inputTypes = new VariableType[]{VariableType.FLOAT , VariableType.INT ,VariableType.BOOLEAN ,VariableType.STRING , VariableType.VECTOR };
		this.size = 1;
		super.setTypes();
	}

}
