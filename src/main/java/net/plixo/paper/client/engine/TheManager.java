package net.plixo.paper.client.engine;

import java.io.File;
import java.util.ArrayList;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.engine.buildIn.blueprint.Blueprint;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.VariableType;
import net.plixo.paper.client.engine.buildIn.scripting.Script;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;


public class TheManager {

    public static JsonParser parser;
    public static ArrayList<Behavior> standartBehavior = new ArrayList<>();
    public static ArrayList<Variable> globals = new ArrayList<>();
    public static ArrayList<GameObject> allEntitys = new ArrayList<>();
    public static ArrayList<UniformFunction> functions = new ArrayList<>();


    public static Behavior newInstanceByName(String name) {
        for (Behavior b : standartBehavior) {
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
        standartBehavior.clear();
        standartBehavior.add(new Script());
        standartBehavior.add(new Blueprint());
    }

    public static void addEntity(GameObject entity) {
        allEntitys.add(entity);
        entity.init();
    }

    public static boolean removeEntity(GameObject entity) {
        if (!allEntitys.contains(entity)) {
            return false;
        }
        TheManager.allEntitys.remove(entity);
        return true;

    }

    public static void load(){
        loadEntiys();
        loadGlobals();
        loadResources();
        loadFunctions();
    }
    public static void loadEntiys() {
        parser = new JsonParser();
        allEntitys.clear();

        File load = SaveUtil.getFileFromName("entitys", SaveUtil.FileFormat.Other);
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
                    JsonArray behaviors = customObj.get("Behaviors").getAsJsonArray();
                    for (int j = 0; j < behaviors.size(); j++) {
                        JsonElement behavior = behaviors.get(j);
                        if (behavior instanceof JsonObject) {
                            JsonObject behaviorObj = (JsonObject) behavior;
                            String behaviorname = behaviorObj.get("Name").getAsString();
                            Behavior instance = newInstanceByName(behaviorname);
                            if (instance != null) {
                                e.components.add(instance);
                            } else {
                                System.out.println("Error:" + behaviorname);
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

                    Vector3d vec = new Vector3d(Float.valueOf(args[0]), Float.valueOf(args[1]), Float.valueOf(args[2]));

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
            for (GameObject var : allEntitys) {
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
    public static void loadFunctions() {

        functions.clear();
        File file = SaveUtil.getFileFromName("functions", SaveUtil.FileFormat.Other);
        JsonElement element = SaveUtil.loadFromJson(TheManager.parser, file);


        if (element instanceof JsonArray) {
            JsonArray arr = (JsonArray) element;
            for (int j = 0; j < arr.size(); j++) {
                JsonElement ele = arr.get(j);
                if (ele instanceof JsonObject) {
                    JsonObject obj = (JsonObject) ele;
                    UniformFunction function = new UniformFunction(obj.get("Name").getAsString());
                    JsonElement output = obj.get("Output");
                    if(output instanceof  JsonObject) {
                        JsonObject outObj = (JsonObject) output;
                        VariableType variableType = VariableType.getType(outObj.get("Type").getAsString());
                        Variable var = new Variable(variableType, outObj.get("Name").getAsString());
                        function.output = var;
                    }

                    JsonArray array = obj.get("List").getAsJsonArray();
                    for (int i = 0; i < array.size(); i++) {
                        JsonElement variable = array.get(i);
                        if (variable instanceof JsonObject) {
                            JsonObject customObj = (JsonObject) variable;
                            String type = customObj.get("Type").getAsString();
                            String varName = customObj.get("Name").getAsString();
                            VariableType variableType = VariableType.getType(type);
                            Variable var = new Variable(variableType, varName);
                            function.addVariable(var);
                        }
                    }
                    functions.add(function);
                }
            }
        }

    }

    public static void save(){
        saveFunctions();
        saveEntitys();
        saveGlobals();
        saveResources();
    }
    public static void saveFunctions() {

        File file = SaveUtil.getFileFromName("functions", SaveUtil.FileFormat.Other);
        JsonArray jsonArray = new JsonArray();

        for (UniformFunction function : functions) {
            JsonObject custom = new JsonObject();
            JsonArray array = new JsonArray();

            for (Variable var : function.variableArrayList()) {
                JsonObject varObj = new JsonObject();
                varObj.addProperty("Name", var.name);
                varObj.addProperty("Type", var.type.name());
                array.add(varObj);
            }
            custom.addProperty("Name", function.getName());
            JsonObject output = new JsonObject();
            output.addProperty("Name" , function.output.name);
            output.addProperty("Type" , function.output.type.name());
            custom.add("Output", output);
            custom.add("List", array);
            jsonArray.add(custom);
        }

        SaveUtil.saveJsonObj(file, jsonArray);

    }
    public static void saveEntitys() {
        File file = SaveUtil.getFileFromName("entitys", SaveUtil.FileFormat.Other);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();

        for (GameObject var : allEntitys) {
            JsonObject custom = new JsonObject();
            custom.addProperty("Entity", var.name);

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

        for (GameObject var : allEntitys) {
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
