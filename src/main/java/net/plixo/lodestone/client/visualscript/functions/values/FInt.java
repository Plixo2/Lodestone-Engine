package net.plixo.lodestone.client.visualscript.functions.values;

import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.visualscript.Function;

public class FInt extends Function {

    Resource<Integer> integerResource;

    @Override
    public void calculate() {
        pullInputs();
        output(0, integerResource.value);
    }

    @Override
    public void set() {
        setInputs();
        setOutputs("value");
        setLinks();

        integerResource = new Resource<>("value", 0);
        setSettings(integerResource);
    }
}
