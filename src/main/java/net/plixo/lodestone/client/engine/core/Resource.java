package net.plixo.lodestone.client.engine.core;

public class Resource<O> {

    String name;
    public O value;
    public Class<?> clazz;

    public Resource(String name, O value) {
        this.name = name;
        this.value = value;
        if(value == null) {
            throw new NullPointerException("Value is null");
        }
        clazz = value.getClass();
    }



    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String clazzName = clazz == null ? "null" : ((clazz.isInterface() ? "interface " : (clazz.isPrimitive() ? "" : "class=")) + clazz.getName());
        return "Resource{" +
                "name='" + name + '\'' +
                ", value=" + value +", "+ clazzName+
                '}';
    }
}
