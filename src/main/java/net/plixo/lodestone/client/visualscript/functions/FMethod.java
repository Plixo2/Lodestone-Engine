package net.plixo.lodestone.client.visualscript.functions;

import net.plixo.lodestone.client.visualscript.functions.events.FEvent;

import java.lang.reflect.Method;

public class FMethod extends FEvent {

   Object object;
    Method method;
    public FMethod(Object object , Method method) {
        this.object = object;
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public void set() {
        String[] strings = new String[method.getParameterTypes().length];
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            strings[i] = method.getParameterTypes()[i].getSimpleName();
        }
        setOutputs(strings);

        String returnType =  method.getReturnType().getSimpleName();
        if(!returnType.equalsIgnoreCase("void")) {
            setInputs(returnType);
        } else {
            setInputs();
        }

        setLinks("");
    }
    public String getClassName() {
        String clazz = getObject().getClass().getSimpleName();
        if(clazz.contains("$")) {
            return clazz.split("\\$")[0];
        }
        return clazz;
    }

    public static String getMethodName(Method method) {
        String clazz = method.getName();
        if(clazz.contains("$")) {
            String name = clazz.split("\\$")[0];
            return name;
        }
        return clazz;
    }

    @Override
    public String getIDString() {
            return super.getIDString()+"-"+getClassName()+"-"+getMethodName(getMethod());
    }

    @Override
    public String getDisplayName() {
        return getMethodName(getMethod());
    }
}
