package net.plixo.lodestone.client.engine.meta;

import net.plixo.lodestone.client.engine.core.Meta;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.visualscript.injection.classes.CModule;

import java.io.File;

public class MVisualCode extends Meta {
    public Resource<Class<?>> classResource = new Resource<>("Inputs", CModule.class);

    public MVisualCode(File origin) {
        super(origin);
        setResources(classResource);
    }
}
