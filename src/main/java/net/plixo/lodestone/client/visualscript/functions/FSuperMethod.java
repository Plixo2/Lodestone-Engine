package net.plixo.lodestone.client.visualscript.functions;

import net.plixo.lodestone.client.visualscript.Function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FSuperMethod extends Function {

    public Method method;
    public java.lang.Object object;
    public FSuperMethod(java.lang.Object object, Method method) {
        this.method = method;
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public java.lang.Object getObject() {
        return object;
    }

    @Override
    public void calculate() {
        pullInputs();

        java.lang.Object[] objects = new java.lang.Object[getInput().length];
        for (int i = 0; i < getInput().length; i++) {
            objects[i] = input(i);
        }
        try {

            Object invoke = method.invoke(object, objects);
            if(getOutput().length >= 1) {
                output(0, invoke);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        calculate();
        execute();
    }

    @Override
    public void set() {
        String[] strings = new String[method.getParameterTypes().length];
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            strings[i] = method.getParameterTypes()[i].getSimpleName();
        }

        setInputs(strings);


        String returnType =  method.getReturnType().getSimpleName();
        if(!returnType.equalsIgnoreCase("void")) {
            setOutputs(returnType);
        } else {
            setOutputs();
        }

        setLinks("");
    }

    public String getClassName() {
        String clazz = getObject().getClass().getSimpleName();
        if(clazz.contains("$")) {
            String name = clazz.split("\\$")[0];
            return name;
        }
        return clazz;
    }


    @Override
    public String getIDString() {
        return "Super-"+super.getIDString()+"-"+getClassName()+"-"+FMethod.getMethodName(getMethod());
    }

    @Override
    public String getDisplayName() {
        return getClassName()+ " : "+FMethod.getMethodName(getMethod());
    }
}
