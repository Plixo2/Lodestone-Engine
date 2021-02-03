package net.plixo.paper.client.engine.buildIn.visualscript.function.buildIn.other;


import net.plixo.paper.client.engine.buildIn.visualscript.function.other.Execute;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.VariableType;

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
			next ( value(0).booleanValue ? 0 : 1);
		}
	}

	@Override
	public void setTypes() {
		this.inputTypes = new VariableType[1];
		this.inputTypes[0] = VariableType.BOOLEAN;
		this.size = 2;
		super.setTypes();
	}

}
