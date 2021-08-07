package net.plixo.lodestone.client.engine.core;

import com.google.gson.JsonElement;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.manager.RMeta;
import net.plixo.lodestone.client.util.io.SaveUtil;
import org.plixo.jrcos.Serializer;

import java.io.File;

public abstract class Meta {

    File origin;

    public Meta(File origin) {
        this.origin = origin;
    }

    public transient Resource<?>[] list;
    public void setResources(Resource<?>... values) {
        list = values;
    }


    public void save() {
        try {
            JsonElement element = Serializer.getJson(this);
            SaveUtil.saveJsonObj(RMeta.getMeta(origin), element);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
