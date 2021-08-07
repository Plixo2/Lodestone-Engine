package net.plixo.lodestone.client.visualscript.functions.values;

import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.visualscript.Function;

public class FText extends Function {

    Resource<String> stringResource;

    @Override
    public void calculate() {
        pullInputs();
        output(0, stringResource.value);
    }

    @Override
    public void set() {
        setInputs();
        setOutputs("value");
        setLinks();

        stringResource = new Resource<>("value", "Hello");
        setSettings(stringResource);
    }
}
