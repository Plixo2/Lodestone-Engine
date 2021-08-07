package net.plixo.lodestone.client.visualscript.functions.values;

import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.visualscript.Function;

public class FBoolean extends Function {

    Resource<Boolean> booleanResource;

    @Override
    public void calculate() {
        pullInputs();
        output(0, booleanResource.value);
    }

    @Override
    public void set() {
        setInputs();
        setOutputs("value");
        setLinks();

        booleanResource = new Resource<>("value", false);
        setSettings(booleanResource);
    }
}
