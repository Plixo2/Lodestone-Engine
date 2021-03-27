package net.plixo.paper.client.engine.meta;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.manager.MetaManager;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;

public class Meta {

    File origin;
    File meta;
    JsonObject data;
    public Resource[] serialized;

    public Meta(File origin) {
        this.origin = origin;
        this.meta = MetaManager.getMeta(origin);
        data = MetaManager.getJsonObject(meta);
    }

    public boolean hasValue() {
        return data != null;
    }

    public Resource get(int i) {
        return serialized[i];
    }

    protected void setSerializedResources(Resource... serialized) {
        this.serialized = serialized;
    }

    protected Resource getResource(String name, Class clazz) {
        Object obj = null;

        if (hasValue()) {
            JsonElement jsonElement = data.get(name);
            if (jsonElement instanceof JsonObject) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonElement element = jsonObject.get("Value");
                if (jsonObject.get("Type").isJsonPrimitive()) {
                    String type = jsonObject.get("Type").getAsString();
                    if (type.equalsIgnoreCase(clazz.getName())) {
                        if (clazz == String.class) {
                            obj = element.getAsString();
                        } else if (clazz == Float.class) {
                            obj = element.getAsFloat();
                        } else if (clazz == Integer.class) {
                            obj = element.getAsInt();
                        } else if (clazz == Vector3d.class) {
                            obj = Util.getVecFromString(element.getAsString());
                        } else if (clazz == Boolean.class) {
                            obj = element.getAsBoolean();
                        } else if (clazz == File.class) {
                            obj = new File(element.getAsString());
                        }
                    }
                }
            }
        }
        return new Resource(name, clazz, obj);
    }

    public void saveMeta() {
        JsonObject obj = new JsonObject();
        for (Resource resource : serialized) {
            JsonObject jobj = resource.serialize();
            String name = resource.clazz.getName();
            jobj.addProperty("Type", name);
            obj.add(resource.name, jobj);
        }
        SaveUtil.saveJsonObj(meta, obj);
    }
}
