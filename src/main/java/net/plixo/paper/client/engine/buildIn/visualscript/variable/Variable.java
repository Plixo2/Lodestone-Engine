package net.plixo.paper.client.engine.buildIn.visualscript.variable;

import net.minecraft.util.math.vector.Vector3d;

public class Variable {

	public boolean booleanValue = false;
	public float floatValue = 0;
	public int intValue = 0;
	public String stringValue = "";
	public VariableType type;
	public String name;
	public Vector3d vectorValue = new Vector3d(0, 0, 0);

	public Variable(VariableType type, String name) {
		this.type = type;
		this.name = name;
	}

	public void setValue(Object obj) {
		switch (type) {
		case FLOAT:
			floatValue = (obj instanceof Double) ? (float) ((double) obj) : (float) obj;
			break;
		case INT:
			intValue = (int) obj;
			break;
		case BOOLEAN:
			booleanValue = (boolean) obj;
			break;
		case STRING:
			stringValue = (String) obj;
			break;
		case VECTOR:
			vectorValue = (Vector3d) obj;
			break;

		default:
			break;
		}
	}

	@Override
	public String toString() {
		switch (this.type) {
		case FLOAT:
			return String.valueOf(floatValue);
		case INT:
			return String.valueOf(intValue);
		case BOOLEAN:
			return String.valueOf(booleanValue);
		case STRING:
			return stringValue;
		case VECTOR:
			return vectorValue.x + "," + vectorValue.y + "," + vectorValue.z;
		default:
			return "";
		}
	}
	public Object asJavaObject()  {
		Object obj = 0;

		switch (type) {
			case FLOAT:
				obj = floatValue;
				break;
			case INT:
				obj =intValue;
				break;
			case BOOLEAN:
				obj = booleanValue;
				break;
			case STRING:
				obj =stringValue;
				break;
			case VECTOR:
				obj = vectorValue;
				break;

			default:
				break;
		}
		return obj;
	}
}