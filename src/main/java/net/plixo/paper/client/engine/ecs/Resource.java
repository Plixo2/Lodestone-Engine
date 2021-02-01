package net.plixo.paper.client.engine.ecs;

import java.io.File;

import com.google.gson.JsonObject;

public class Resource {

	public Class clazz;
	public String name;
	Object value;

	public Resource(String name, Class clazz, Object initValue) {
		this.clazz = clazz;
		this.name = name;
		this.value = initValue;
	}

	public void fromString(String str) {
		if (str.isEmpty()) {
			return;
		}
		if (isFile()) {
			setValue(new File(str));
		} else if (isBoolean()) {
			setValue(Boolean.valueOf(str));
		} else if (isFloat()) {
			setValue(Float.valueOf(str));
		} else if (isInteger()) {
			setValue(Integer.valueOf(str));
		} else if (isString()) {
			setValue(str);
		}
	}

	public boolean getAsBoolean() {
		if (!hasValue()) {
			return false;
		}
		return (boolean) value;
	}

	public File getAsFile() {
		if (!hasValue()) {
			return null;
		}
		return (File) value;
	}

	public float getAsFloat() {
		if (!hasValue()) {
			return 0;
		}
		return (float) value;
	}

	public int getAsInteger() {
		if (!hasValue()) {
			return 0;
		}
		return (int) value;
	}

	public String getAsString() {
		if (!hasValue()) {
			return "";
		}
		return (String) value;
	}

	public boolean hasValue() {
		return value != null;
	}

	public boolean isBoolean() {
		return clazz == Boolean.class;
	}

	public boolean isFile() {
		return clazz == File.class;
	}

	public boolean isFloat() {
		return clazz == Float.class;
	}

	public boolean isInteger() {
		return clazz == Integer.class;
	}

	public boolean isString() {
		return clazz == String.class;
	}

	public JsonObject serialize() {
		JsonObject obj = new JsonObject();
		//obj.addProperty("Class", clazz.toString());
		obj.addProperty("Value", toTxt());
		return obj;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	String toTxt() {
		if (!hasValue()) {
			return "";
		}
		if (isFile()) {
			return getAsFile().getPath();
		} else if (isBoolean()) {
			return getAsBoolean() + "";
		} else if (isFloat()) {
			return getAsFloat() + "";
		} else if (isInteger()) {
			return getAsInteger() + "";
		} else if (isString()) {
			return getAsString();
		}
		return "";
	}

}
