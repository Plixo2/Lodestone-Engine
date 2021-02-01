package net.plixo.paper.client.engine.buildIn.blueprint.function.buildIn.other;


import net.plixo.paper.client.engine.buildIn.blueprint.function.other.Execute;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.VariableType;

public class ELoop extends Execute {

	Variable index;

	public ELoop() {
		super("Loop");
	}
	
	@Override
	public void execute() {

	}
	
	
	void last(int index) {
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

	void next(int index) {
		if (nextConnection != null) {
			Execute next = nextConnection[index];
			if (next != null) {
			
				next.hasCalculated = false;
				next.reTrace();
				next.execute();
				next.postExecute();
				next.hasCalculated = true;
			}
		}
	}
	
	@Override
	public void postExecute() {
		
		
		if(isNotNull(0)) {
			for(int i = 0; i < value(0).intValue; i++) {

				reset(this);

				index.setValue(i);
				next(1);
			}
		}
		
		last(0);
	}
 
	void reset(Execute fun) {
		

		//TODO recursion with error at length
		for(int i = 0 ; i < fun.nextConnection.length; i++) {
			Execute next = fun.nextConnection[i];
			if(next != null) {
			next.hasCalculated = false;
			reset(next);
			}
		}
	}

	@Override
	public void setTypes() {
		this.outputs = new Variable[] { index = new Variable(VariableType.INT , "Index")};
		this.names = new String[] {"Size"};
		this.inputTypes = new VariableType[] { VariableType.INT};
		
		this.size = 2;
		super.setTypes();
	}

}
