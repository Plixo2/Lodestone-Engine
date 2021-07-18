package net.plixo.paper.client.engine.meta;

import net.plixo.paper.client.engine.ecs.Meta;
import net.plixo.paper.client.engine.ecs.Resource;

import java.io.File;

public class CodeMeta extends Meta {
    public Resource<Integer> inputs = new Resource<>("Inputs",0);
    public Resource<Integer> outputs = new Resource<>("Outputs",0);
    public Resource<Integer> links = new Resource<>("Links",0);

    public CodeMeta(File origin) {
        super(origin);
        setResources(inputs,outputs,links);
    }
}
