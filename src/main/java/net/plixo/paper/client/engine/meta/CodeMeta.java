package net.plixo.paper.client.engine.meta;

import java.io.File;

public class CodeMeta extends Meta {
    public CodeMeta(File origin) {
        super(origin);
        setSerializedResources(getResource("in",Integer.class),getResource("out",Integer.class),getResource("links",Integer.class));
    }
}
