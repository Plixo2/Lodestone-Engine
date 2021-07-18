package net.plixo.paper.client.manager;

import com.google.gson.JsonElement;
import net.plixo.paper.client.engine.meta.CodeMeta;
import net.plixo.paper.client.engine.ecs.Meta;
import net.plixo.paper.client.util.SaveUtil;
import org.apache.commons.io.FilenameUtils;
import org.plixo.jrcos.Initializer;

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

    public static Meta getMetaByFile(File file) {

        if(file == null) {
            return null;
        }



        Meta meta = null;

        if (SaveUtil.FileFormat.getFromFile(file) == SaveUtil.FileFormat.Code) {
            meta = new CodeMeta(file);
        }

        try {
            if(meta != null) {
                JsonElement element = SaveUtil.loadFromJson(MetaManager.getMeta(file));
                meta = (Meta) Initializer.getObject(meta,element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return meta;
    }
}
