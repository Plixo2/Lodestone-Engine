package net.plixo.paper.client.avs.newVersion;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.plixo.paper.client.util.SaveUtil;

import java.io.File;

public class FunctionManager {

    public VisualScript loadFromFile(File file) {
        VisualScript script = new VisualScript(file);
        try {
            JsonElement element = SaveUtil.loadFromJson(new JsonParser(),file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return script;
    }

    public void saveToFile(VisualScript script) {
        JsonObject jsonObject = new JsonObject();

        for (nFunction function : script.functions) {
            JsonObject body = new JsonObject();

            body.addProperty("Name",function.getName());
            jsonObject.add(function.id+"",body);
        }


        SaveUtil.saveJsonObj(script.location,jsonObject);

    }
}
