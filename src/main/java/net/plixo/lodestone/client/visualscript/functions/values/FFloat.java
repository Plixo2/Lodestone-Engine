package net.plixo.lodestone.client.visualscript.functions.values;

import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.visualscript.Function;

public class FFloat extends Function {

    Resource<Float> floatResource;

    @Override
    public void calculate() {
        pullInputs();
        output(0, floatResource.value);
    }

    @Override
    public void set() {
        setInputs();
        setOutputs("value");
        setLinks();

        floatResource = new Resource<>("value", 0f);
        setSettings(floatResource);
    }
}
