package net.plixo.paper.client.engine.buildIn.visualscript.function.other;


import net.plixo.paper.client.engine.buildIn.visualscript.function.Function;

public abstract class Execute extends Function {

	public Execute[] nextConnection;

	public int size;
	public Execute(String name) {
		super(name);
	}

	public void postExecute() {
		hasCalculated = true;
		if (nextConnection != null) {
			for (Execute next : nextConnection) {

				if (next != null) {
					next.hasCalculated = true;
					next.reTrace();
					next.execute();
					next.postExecute();
				}
			}
		}
	}

	@Override
	public void setTypes() {
		nextConnection = new Execute[size];
		super.setTypes();
	}

}
