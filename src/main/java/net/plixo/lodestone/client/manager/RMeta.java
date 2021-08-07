package net.plixo.lodestone.client.manager;

import com.google.gson.JsonElement;
import net.plixo.lodestone.client.engine.meta.MCode;
import net.plixo.lodestone.client.engine.core.Meta;
import net.plixo.lodestone.client.engine.meta.MVisualCode;
import net.plixo.lodestone.client.util.io.SaveUtil;
import org.apache.commons.io.FilenameUtils;
import org.plixo.jrcos.Initializer;

import java.io.File;

public class RMeta {


    public static File getMeta(File file) {
        String name = FilenameUtils.getBaseName(file.getAbsolutePath());
        File meta = new File(file.getParent() + "/" + name + "." + SaveUtil.FileFormat.META.format);
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

        if (SaveUtil.FileFormat.getFromFile(file) == SaveUtil.FileFormat.CODE) {
            meta = new MCode(file);
        }
        if (SaveUtil.FileFormat.getFromFile(file) == SaveUtil.FileFormat.VISUAL_SCRIPT) {
            meta = new MVisualCode(file);
        }

        try {
            if(meta != null) {
                JsonElement element = SaveUtil.loadFromJson(RMeta.getMeta(file));
                meta = (Meta) Initializer.getObject(meta,element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return meta;
    }
}
