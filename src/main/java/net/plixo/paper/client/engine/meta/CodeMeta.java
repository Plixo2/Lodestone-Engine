package net.plixo.paper.client.engine.meta;

import java.io.File;

public class CodeMeta extends Meta {
    public CodeMeta(File origin) {
        super(origin);
        setSerializedResources(getResource("Output",String.class),getResource("Input",String.class),getResource("Execution",Boolean.class));
    }
}
