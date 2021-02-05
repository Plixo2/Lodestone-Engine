package net.plixo.paper.client.engine.buildIn.mesh;

import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.MeshUtil;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;

public class MeshBehavior extends Behavior {

    public MeshBehavior() {
        super("Mesh Renderer");
        setSerializableResources(new Resource("File", File.class, null));
    }


    //TODO Fix loading ...
    /*
    public Mesh getMeshToRender() {
        Resource res = getResource(0);
        Util.print(res.getAsFile());
        if (res.hasValue()) {

            File file = res.getAsFile();
            if(file.exists() && file.getName().endsWith(SaveUtil.FileFormat.Model.format)) {
                return MeshUtil.loadFromFile(file);
            }
        }
        return null;
    }
*/
}
