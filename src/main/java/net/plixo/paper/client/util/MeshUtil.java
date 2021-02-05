package net.plixo.paper.client.util;

import net.plixo.paper.client.engine.buildIn.mesh.Mesh;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MeshUtil {
    public static Mesh loadFromFile(File file) {
        ArrayList<String> list = SaveUtil.loadFromFile(file);

        return new Mesh();
    }
}
