package net.plixo.lodestone.client.engine.meta;

import net.plixo.lodestone.client.engine.core.Meta;
import net.plixo.lodestone.client.engine.core.Resource;

import java.io.File;

public class MCode extends Meta {
    public Resource<Integer> inputs = new Resource<>("Inputs",0);
    public Resource<Integer> outputs = new Resource<>("Outputs",0);
    public Resource<Integer> links = new Resource<>("Links",0);

    public MCode(File origin) {
        super(origin);
        setResources(inputs,outputs,links);
    }
}
