package net.plixo.paper.client.engine.ecs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.manager.MetaManager;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Serializer;

import java.io.File;

public class Meta {

    File origin;

    public Meta(File origin) {
        this.origin = origin;
        //TODO load here
    }

    public transient Resource[] list;
    public void setResources(Resource... values) {
        list = values;
    }


    public void save() {
        try {
            JsonElement element = Serializer.getJson(this);
            SaveUtil.saveJsonObj(MetaManager.getMeta(origin), element);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
/*
    public void saveMetaOrNah() {
        JsonArray obj = new JsonArray();
        for (Resource resource : serialized) {
           // JsonObject jobj = resource.serialize();
//TODO ...
            try {
               JsonElement element = Serializer.getJson(resource);
                obj.add(element);
            } catch (Exception e) {
              Util.print(e);
              e.printStackTrace();
            }
            //String name = resource.clazz.getName();
            //jobj.addProperty("Type", name);
            //obj.add(resource.name, jobj);
        }
        SaveUtil.saveJsonObj(meta, obj);
    }

 */
}
