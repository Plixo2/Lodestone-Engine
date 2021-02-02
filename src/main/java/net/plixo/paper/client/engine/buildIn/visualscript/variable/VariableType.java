package net.plixo.paper.client.engine.buildIn.visualscript.variable;

public enum VariableType {
	BOOLEAN, FLOAT, INT, STRING, VECTOR;

	public static  VariableType getType(String name) {
		for(VariableType t : VariableType.values()) {
			if(name.equalsIgnoreCase(t.name())) {
				return t;
			}
		}
		return null;
	}

	public boolean canConnect(VariableType other) {
		return this == other;
	}
	
	public int getColor() {
		switch (this) {
		case FLOAT:
			return 0xFF04A46A;
		case INT:
			return 0xFF3498DB;
		case BOOLEAN:
			return 0xFFD41E21;
		case STRING:
			return 0xFFFF751A;
		case VECTOR:
			return 0xFFFECB04;

		default:
			return 0xFFe6e339;
		}
	}
	
	public String getStringColor() {
		switch (this) {
		case FLOAT:
			return "§2";
		case INT:
			return "§9";
		case BOOLEAN:
			return "§4";
		case STRING:
			return "§6";
		case VECTOR:
			return "§e";

		default:
			return "";
		}
		
	}
}
