package net.plixo.paper.client.avs.newVersion;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.plixo.paper.client.avs.newVersion.functions.*;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class FunctionManager {

    public static ArrayList<nFunction> functions = new ArrayList<>();
    public static nFunction getInstanceByName(String name) {
        for (nFunction function : functions) {
            if(function.getName().equalsIgnoreCase(name)) {
                try {
                    return (nFunction) function.getClass().getConstructors()[0].newInstance();
                }catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return null;
    }
    public static nFunction getInstanceByClassName(String clazz) {
        try {
            return (nFunction) Class.forName(clazz).getConstructors()[0].newInstance();
        }catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }

        return null;
    }

    public static void register() {
        FunctionManager.functions.add(new Event());
        FunctionManager.functions.add(new getGround());
        FunctionManager.functions.add(new If());
        FunctionManager.functions.add(new Jump());
        FunctionManager.functions.add(new Print());
        FunctionManager.functions.add(new Script());
    }

    public static VisualScript loadFromFile(File file) {
        VisualScript script = new VisualScript(file);
        try {
            HashMap<nFunction , JsonObject> linkMap = new HashMap<>();
            HashMap<nFunction , JsonObject> inputMap = new HashMap<>();

            JsonArray element = SaveUtil.loadFromJson(new JsonParser(),file).getAsJsonArray();
            for (int i = 0; i < element.size(); i++) {
                JsonObject object = element.get(i).getAsJsonObject();
                UUID uuid = UUID.fromString(object.get("Id").getAsString());
                String className = object.get("Class").getAsString();
                float x = object.get("X").getAsFloat();
                float y = object.get("Y").getAsFloat();
                nFunction instance = FunctionManager.getInstanceByClassName(className);
                Objects.requireNonNull(instance);
                instance.id = uuid;
                script.getFunctions().add(instance);
                instance.set();
                instance.ui = new nUIFunction(instance);


                JsonObject resource = object.get("Resources").getAsJsonObject();


                JsonObject links = object.get("Links").getAsJsonObject();
                JsonObject inputs = object.get("Inputs").getAsJsonObject();
                linkMap.put(instance,links);
                inputMap.put(instance,inputs);

              try {
                  for (Resource setting : instance.settings) {
                      JsonObject jsonObject = resource.get(setting.name).getAsJsonObject();
                      setting.fromString(jsonObject.get("Value").getAsString());
                  }
              } catch (Exception e) {
                Util.print(e.getMessage());
                e.printStackTrace();
              }

              instance.ui.setDimensions(x, y, 120, 20);
            }

            try {
                for (nFunction function : script.getFunctions()) {
                    JsonObject link = linkMap.get(function);
                    JsonObject input = inputMap.get(function);
                    Objects.requireNonNull(link);
                    Objects.requireNonNull(input);

                    for (int i = 0; i < function.links.length; i++) {
                        String id = link.get(""+i).getAsString();
                        if(id.isEmpty()) {
                            continue;
                        }
                        UUID uuid = UUID.fromString(id);

                        function.links[i] = script.getByUUID(uuid);
                    }

                    for (int i = 0; i < function.input.length; i++) {
                        String id = input.get(""+i).getAsString();
                        if(id.isEmpty()) {
                            continue;
                        }
                        UUID uuid = UUID.fromString(id);

                        function.input[i] = script.getByUUID(uuid).output[i];
                    }
                }
                

            } catch (Exception e) {
              Util.print(e.getMessage());
              e.printStackTrace();
            }
           

        } catch (Exception e) {
            Util.print(e.getMessage());
            e.printStackTrace();
        }
        return script;
    }



    public static void saveToFile(VisualScript script) {
        JsonArray array = new JsonArray();

        Util.print(script.getFunctions().size());
        for (nFunction function : script.getFunctions()) {
            JsonObject body = new JsonObject();

            body.addProperty("Id",function.id.toString());
            body.addProperty("Class",function.getClass().getName());
            body.addProperty("X",function.ui.x);
            body.addProperty("Y",function.ui.y);

            JsonObject resources = new JsonObject();
            for (Resource setting : function.settings) {
                resources.add(setting.name,setting.serialize());
            }
            body.add("Resources",resources);

            JsonObject links = new JsonObject();
            for (int i = 0; i < function.links.length; i++) {
                nFunction link = function.links[i];
                links.addProperty(""+i,link == null ? "" : link.id.toString());
            }
            body.add("Links",links);

            JsonObject inputs = new JsonObject();
            for (int i = 0; i < function.input.length; i++) {
                nFunction.Output input = function.input[i];
                inputs.addProperty(""+i,input == null ? "" : input.function.id.toString());
            }
            body.add("Inputs",inputs);


            array.add(body);
        }
        SaveUtil.saveJsonObj(script.location,array);

    }
}
