package net.plixo.paper.client.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.plixo.paper.client.engine.meta.CodeMeta;
import net.plixo.paper.client.engine.meta.Meta;
import net.plixo.paper.client.util.SaveUtil;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class MetaManager {


    public static File getMeta(File file) {
        String name = FilenameUtils.getBaseName(file.getAbsolutePath());
        File meta = new File(file.getParent() + "/" + name + "." + SaveUtil.FileFormat.Meta.format);
        if (!meta.exists()) {
            SaveUtil.makeFile(meta);
        }
        return meta;
    }

    public static JsonObject getJsonObject(File meta) {
        if (meta == null) {
            return null;
        }
        JsonElement obj = SaveUtil.loadFromJson(new JsonParser(), meta);
        if (!(obj instanceof JsonObject)) {
            return null;
        }
        return obj.getAsJsonObject();
    }

    public static Meta getMetaByFile(File file) {
        if(file == null) {
            return null;
        }
        if (SaveUtil.FileFormat.getFromFile(file) == SaveUtil.FileFormat.Code) {
            return new CodeMeta(file);
        }
        return null;
    }
}
