package net.plixo.paper.client.engine.behaviors;

import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.simple.SimpleColor;
import net.plixo.paper.client.util.simple.SimpleParagraph;
import test.zoo.zoo.Rhino;
import test.zoo.zoo.Zoo;

import java.io.File;

public class Renderer extends Behavior {

    Resource<File> fileResource = new Resource<>("T_File", new File(""));
    Resource<Integer> integerResource = new Resource<>("T_Integer", 0);
    Resource<Float> floatResource = new Resource<>("T_Float", 0f);
    Resource<Boolean> booleanResource = new Resource<>("T_Boolean", true);
    Resource<String> stringResource = new Resource<>("T_String", "Hello");
    Resource<SimpleParagraph> simpleParagraphResource = new Resource<>("T_Paragraph", new SimpleParagraph("Hello World!"));
    Resource<Vector3d> vector3dResource = new Resource<>("T_Vector", new Vector3d(0, 0, 0));
    Resource<SimpleColor> colorResource = new Resource<>("T_Color", new SimpleColor(255, 255, 255, 255));
    Resource<Enum<?>> enumResource = new Resource<>("T_Enum", SaveUtil.FileFormat.Code);
    Resource<Test> engineResource = new Resource<>("T_UI", new Test());

    public Renderer() {
        super("Renderer");
        setSerializableResources(simpleParagraphResource,engineResource, fileResource, integerResource, floatResource, booleanResource, stringResource, vector3dResource, colorResource, enumResource);
    }

    public static class Test {
        public String strValue = "str";
        public boolean aBoolean = false;
        public int anInt = 17;
        public float anFloat = 5.5f;
        public GameObject gameObject = new GameObject();
        public Zoo zoo = new Zoo();

        public Test() {
            zoo.spawn(Rhino.class);
        }
    }

}
