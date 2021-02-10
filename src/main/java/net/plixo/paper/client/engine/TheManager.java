package net.plixo.paper.client.engine;

import com.google.gson.*;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.UI.elements.UICanvas;
import net.plixo.paper.client.engine.behaviors.OutlineRender;
import net.plixo.paper.client.engine.behaviors.Script;
import net.plixo.paper.client.engine.behaviors.VisualScript;
import net.plixo.paper.client.engine.components.visualscript.variable.Variable;
import net.plixo.paper.client.engine.components.visualscript.variable.VariableType;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class TheManager {

    public static JsonParser parser;
    public static ArrayList<Behavior> standardBehavior = new ArrayList<>();
    public static ArrayList<Variable> globals = new ArrayList<>();
    public static CopyOnWriteArrayList<GameObject> allEntities = new CopyOnWriteArrayList<>();


    public static Behavior newInstanceByName(String name) {
        for (Behavior b : standardBehavior) {
            if (b.name.equalsIgnoreCase(name)) {
                try {
                    return (Behavior) b.getClass().getConstructors()[0].newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void register() {
        standardBehavior.clear();
        standardBehavior.add(new Script());
        standardBehavior.add(new VisualScript());
        standardBehavior.add(new OutlineRender());
    }

    public static void addEntity(GameObject entity) {
        allEntities.add(entity);
        entity.init();
    }

    public static boolean removeEntity(GameObject entity) {
        if (!allEntities.contains(entity)) {
            return false;
        }
        TheManager.allEntities.remove(entity);
        return true;

    }

    public static void load() {
        try {
            loadEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            loadGlobals();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            loadResources();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadEntities() {
        parser = new JsonParser();
        allEntities.clear();

        File load = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.Other);
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
                        JsonElement behavior = behaviors.get(j);
                        if (behavior instanceof JsonObject) {
                            JsonObject behaviorObj = (JsonObject) behavior;
                            String behaviorName = behaviorObj.get("Name").getAsString();
                            Behavior instance = newInstanceByName(behaviorName);
                            if (instance != null) {
                                e.components.add(instance);
                            } else {
                                System.out.println("Error:" + behaviorName);
                            }
                        }
                    }
                    addEntity(e);
                }
            }
        }
    }

    public static void loadGlobals() {
        parser = new JsonParser();
        globals.clear();
        File load = SaveUtil.getFileFromName("globals", SaveUtil.FileFormat.Other);
        JsonElement element = SaveUtil.loadFromJson(parser, load);

        if (element instanceof JsonObject) {
            JsonObject obj = (JsonObject) element;
            JsonArray array = obj.get("List").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonElement custom = array.get(i);
                if (custom instanceof JsonObject) {
                    JsonObject customObj = (JsonObject) custom;
                    String VarType = customObj.get("Type").getAsString();
                    String varName = customObj.get("Name").getAsString();

                    boolean varBoolean = customObj.get("Boolean").getAsBoolean();
                    int varInt = customObj.get("Int").getAsInt();
                    float varFloat = customObj.get("Float").getAsFloat();
                    String varString = customObj.get("String").getAsString();
                    String varVector = customObj.get("Vector").getAsString();

                    varVector = Util.replace(varVector, ")", "");
                    varVector = Util.replace(varVector, "(", "");
                    String[] args = Util.getAllStringArgument(varVector, ",");

                    Vector3d vec = new Vector3d(Float.parseFloat(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2]));

                    VariableType type = VariableType.valueOf(VarType);

                    Variable var = new Variable(type, varName);
                    var.booleanValue = varBoolean;
                    var.intValue = varInt;
                    var.floatValue = varFloat;
                    var.stringValue = varString;
                    var.vectorValue = vec;
                    globals.add(var);
                }
            }

        }
    }

    public static void loadResources() {
        parser = new JsonParser();
        File load = SaveUtil.getFileFromName("resources", SaveUtil.FileFormat.Other);
        JsonElement element = SaveUtil.loadFromJson(parser, load);

        if (element instanceof JsonObject) {
            JsonObject mainObj = (JsonObject) element;
            for (GameObject var : allEntities) {
                JsonElement entityElement = mainObj.get(var.name);
                if (entityElement instanceof JsonObject) {
                    JsonObject entityObj = (JsonObject) entityElement;
                    for (Behavior behavior : var.components) {
                        JsonElement behaviorElement = entityObj.get(behavior.name);
                        if (behaviorElement instanceof JsonObject) {
                            JsonObject behaviorObj = (JsonObject) behaviorElement;
                            for (Resource res : behavior.serializable) {
                                JsonElement resElement = behaviorObj.get(res.name);
                                if (resElement instanceof JsonObject) {
                                    JsonObject resObj = (JsonObject) resElement;
                                    JsonElement valueElement = resObj.get("Value");
                                    if (valueElement instanceof JsonPrimitive) {
                                        res.fromString(valueElement.getAsString());
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public static void save() {
        saveEntities();
        saveGlobals();
        saveResources();
    }

    public static void saveEntities() {
        File file = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.Other);
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
                JsonObject name = new JsonObject();
                name.addProperty("Name", behavior.name);
                behaviors.add(name);
            }

            custom.add("Behaviors", behaviors);
            array.add(custom);
        }
        obj.add("List", array);
        SaveUtil.saveJsonObj(file, obj);
    }

    public static void saveGlobals() {
        File file = SaveUtil.getFileFromName("globals", SaveUtil.FileFormat.Other);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();

        for (Variable var : globals) {
            JsonObject custom = new JsonObject();
            custom.addProperty("Name", var.name);
            custom.addProperty("Type", var.type.name());
            custom.addProperty("Boolean", var.booleanValue);
            custom.addProperty("Int", var.intValue);
            custom.addProperty("Float", var.floatValue);
            custom.addProperty("String", var.stringValue);
            custom.addProperty("Vector", var.vectorValue.toString());
            array.add(custom);
        }
        obj.add("List", array);
        SaveUtil.saveJsonObj(file, obj);
    }

    public static void saveResources() {
        File file = SaveUtil.getFileFromName("resources", SaveUtil.FileFormat.Other);
        JsonObject array = new JsonObject();

        for (GameObject var : allEntities) {
            JsonObject entityObj = new JsonObject();
            for (Behavior behavior : var.components) {
                JsonObject behaviorObj = new JsonObject();
                for (Resource res : behavior.serializable) {
                    JsonObject resObj = res.serialize();
                    behaviorObj.add(res.name, resObj);
                }
                entityObj.add(behavior.name, behaviorObj);
            }
            array.add(var.name, entityObj);
        }
        SaveUtil.saveJsonObj(file, array);
    }

}
