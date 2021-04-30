package net.plixo.paper.client.manager;

import com.google.gson.*;
import net.plixo.paper.client.engine.behaviors.Renderer;
import net.plixo.paper.client.engine.behaviors.Visual_Script;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClientManager {

    public static JsonParser parser;
    public static ArrayList<Behavior> standardBehavior = new ArrayList<>();
    public static CopyOnWriteArrayList<GameObject> allEntities = new CopyOnWriteArrayList<>();

    public static void addEntity(GameObject entity) {
        allEntities.add(entity);
        entity.onEvent(ClientEvent.InitEvent.event);
    }
    public static boolean removeEntity(GameObject entity) {
        if (!allEntities.contains(entity)) {
            return false;
        }
        ClientManager.allEntities.remove(entity);
        return true;
    }

    public static Behavior newInstanceByName(String name) {
        for (Behavior b : standardBehavior) {
            if (b.name.equalsIgnoreCase(name)) {
                try {
                    return (Behavior) b.getClass().getConstructors()[0].newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.print(e.getMessage());
                }
            }
        }
        return null;
    }
    public static void register() {
        standardBehavior.clear();
        standardBehavior.add(new Visual_Script());
        standardBehavior.add(new Renderer());
    }

    public static void load() {
        try {
            loadEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void save() {
        saveEntities();
    }


    public static void loadEntities() {
        parser = new JsonParser();
        allEntities.clear();

        File load = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.Meta);
        JsonElement element = SaveUtil.loadFromJson(parser, load);

        if (element instanceof JsonObject) {

            JsonObject obj = (JsonObject) element;
            JsonArray array = obj.get("List").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonElement entity = array.get(i);
                if (entity instanceof JsonObject) {
                    JsonObject customObj = (JsonObject) entity;
                    String name = customObj.get("Entity").getAsString();
                    GameObject e = new GameObject(name);

                    String pos = customObj.get("Position").getAsString();
                    String size = customObj.get("Scale").getAsString();
                    String rot = customObj.get("Rotation").getAsString();

                    e.position = Util.getVecFromString(pos);
                    e.scale = Util.getVecFromString(size);
                    e.rotation = Util.getVecFromString(rot);

                    JsonArray behaviors = customObj.get("Behaviors").getAsJsonArray();
                    for (int j = 0; j < behaviors.size(); j++) {
                            JsonObject behaviorObj = behaviors.get(j).getAsJsonObject();
                            String behaviorName = behaviorObj.get("Name").getAsString();
                            Behavior instance = newInstanceByName(behaviorName);

                            if (instance != null) {
                                e.components.add(instance);

                                for (Resource res : instance.serializable) {
                                    JsonObject resource = behaviorObj.get(res.name).getAsJsonObject();
                                    String resourceName = resource.get("Value").getAsString();
                                    res.fromString(resourceName);
                                }


                            } else {
                                System.out.println("Error:" + behaviorName);
                            }
                    }
                    addEntity(e);
                }
            }
        }
    }
    public static void saveEntities() {
        File file = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.Meta);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();

        for (GameObject var : allEntities) {
            JsonObject custom = new JsonObject();
            custom.addProperty("Entity", var.name);

            custom.addProperty("Position", Util.getStringFromVector(var.position));
            custom.addProperty("Scale", Util.getStringFromVector(var.scale));
            custom.addProperty("Rotation", Util.getStringFromVector(var.rotation));

            JsonArray behaviors = new JsonArray();
            for (Behavior behavior : var.components) {

                JsonObject data = new JsonObject();
                data.addProperty("Name",behavior.name);
                for (Resource res : behavior.serializable) {
                    JsonObject resObj = res.serialize();
                    data.add(res.name, resObj);
                }

                behaviors.add(data);
            }

            custom.add("Behaviors", behaviors);
            array.add(custom);
        }
        obj.add("List", array);
        SaveUtil.saveJsonObj(file, obj);
    }

}
