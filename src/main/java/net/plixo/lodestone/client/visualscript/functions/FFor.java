package net.plixo.lodestone.client.visualscript.functions;

import net.plixo.lodestone.client.visualscript.Function;

public class FFor extends Function {
    @Override
    public void run() {
        pullInputs();
        Object input = input(0);

        if(hasInput(0)) {
            Object arrayObject = input(0);
            if(arrayObject != null) {
                if(arrayObject.getClass().isArray()) {
                    Object[] objects = ((Object[]) arrayObject);
                    for (int i = 0; i < objects.length; i++) {
                        output(0,objects[i]);
                        output(1,i);
                        execute(1);
                    }

                }
            }
        }

        execute(0);
    }

    @Override
    public void set() {
        setInputs("Array");
        setOutputs("Object","Index");
        setLinks("End","Do");
    }
}
