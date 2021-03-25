package net.plixo.paper.client.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.avs.components.event.buildIn.EventOnEnd;
import net.plixo.paper.client.avs.components.event.buildIn.EventOnKey;
import net.plixo.paper.client.avs.components.event.buildIn.EventOnStart;
import net.plixo.paper.client.avs.components.event.buildIn.EventOnTick;
import net.plixo.paper.client.avs.components.function.Function;

import net.plixo.paper.client.avs.components.function.buildIn.custom.JavaScriptFunction;
import net.plixo.paper.client.avs.components.function.buildIn.io.ELog;
import net.plixo.paper.client.avs.components.function.buildIn.other.EBranch;
import net.plixo.paper.client.avs.components.function.buildIn.other.EIf;
import net.plixo.paper.client.avs.components.function.buildIn.other.ELoop;
import net.plixo.paper.client.avs.components.function.other.Connection;
import net.plixo.paper.client.avs.components.function.other.Execute;
import net.plixo.paper.client.avs.components.variable.Variable;
import net.plixo.paper.client.avs.components.variable.VariableType;
import net.plixo.paper.client.avs.newVersion.VisualScript;
import net.plixo.paper.client.avs.ui.DrawFunction;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;


public class VisualScriptManager {

    public static ArrayList<Function> allFunctions = new ArrayList<>();
    /*
    public static int draggedType = 0;
    public static int dragIndex = -1;

    public static int dragTab = -1;
    */

    public static Function getFromList(String name) {
        ArrayList<Function> functions = new ArrayList<>();

        for (Function parentFunction : allFunctions) {
            Class<?> c;
            try {
                c = parentFunction.getClass();
                for (Constructor<?> aw : c.getConstructors()) {
                    if (aw.getParameterCount() == 0) {
                        Object object = aw.newInstance();
                        Function function = (Function) object;
                        if (function instanceof JavaScriptFunction) {
                            JavaScriptFunction parent = (JavaScriptFunction) parentFunction;
                            JavaScriptFunction child = (JavaScriptFunction) function;
                            child.setParameter(parent.name, parent.function, parent.output,parent.execution, parent.input);
                        }
                        functions.add(function);
                        break;
                    } else {
                        Util.print("Error at loading a class" + parentFunction.name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        for (Function function : functions) {
            if (function.name.equalsIgnoreCase(name)) {
                return function;
            }
        }

        return null;
    }

    public static void register() {
        allFunctions.clear();
        allFunctions.add(new EventOnKey());
        allFunctions.add(new EventOnTick());
        allFunctions.add(new EventOnStart());
        allFunctions.add(new EventOnEnd());
        allFunctions.add(new ELog());

        allFunctions.add(new EIf());
        allFunctions.add(new EBranch());
        allFunctions.add(new ELoop());


        File library = SaveUtil.getFolderFromName("library");
        if (!library.exists()) {
            SaveUtil.makeFolder(library);
            return;
        }
        ArrayList<File> files = new ArrayList<>();
        findFiles(library.getAbsolutePath(), files);


        for (File file : files) {
            try {
                ArrayList<JavaScriptFunction> functions = JavaScriptFunction.loadFromFile(file);
                allFunctions.addAll(functions);
            }
            catch (Exception e) {
                e.printStackTrace();
                Util.print(e.getMessage());
            }
        }
    }

    public static void findFiles(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile() && file.getName().endsWith(SaveUtil.FileFormat.Code.format)) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    findFiles(file.getAbsolutePath(), files);
                }
            }
    }


    public static VisualScript loadFromFile(File file) {
        VisualScript script = new VisualScript(file);
        try {
            ArrayList<Function> newFunc = new ArrayList<>();
            JsonElement obj = SaveUtil.loadFromJson(new JsonParser(), file);
            if (obj instanceof JsonObject) {

                JsonObject toObj = (JsonObject) obj;
                JsonElement array = toObj.get("List");

                HashMap<Function, JsonArray> Variables = new HashMap<>();
                HashMap<Function, JsonArray> Executions = new HashMap<>();
                if (array.isJsonArray()) {
                    JsonArray nameArray = toObj.get("List").getAsJsonArray();

                    for (int i = 0; i < nameArray.size(); i++) {
                        JsonElement element = nameArray.get(i);
                        if (element instanceof JsonObject) {
                            JsonObject keys = (JsonObject) element;
                            String name = keys.get("Name").getAsString();
                           float x = keys.get("posX").getAsFloat();
                            float y = keys.get("posY").getAsFloat();

                            Function function = getFromList(name);
                            if (function == null) {
                                Util.print("Cant find Function for \"" + name + "\"");
                                Util.print("The loading system might fail completely");
                                continue;
                            }
                           // DrawFunction drawFunc = new DrawFunction(function);
                            function.x = x;
                            function.y = y;

                            JsonElement custom = keys.get("Custom");
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

                                Vector3d vec = new Vector3d(Float.parseFloat(args[0]), Float.parseFloat(args[1]),
                                        Float.parseFloat(args[2]));

                                VariableType type = VariableType.valueOf(VarType);

                                Variable var = new Variable(type, varName);
                                var.booleanValue = varBoolean;
                                var.intValue = varInt;
                                var.floatValue = varFloat;
                                var.stringValue = varString;
                                var.vectorValue = vec;

                                function.customData = var;
                            }


                            function.setTypes();
                            newFunc.add(function);

                            JsonElement vars = keys.get("Variables");
                            JsonElement exe = keys.get("Execution");

                            if (vars instanceof JsonArray) {
                                Variables.put(function, (JsonArray) vars);
                            } else {
                                Variables.put(function, null);
                            }

                            if (exe instanceof JsonArray) {
                                Executions.put(function, (JsonArray) exe);
                            } else {
                                Executions.put(function, null);
                            }
                        }
                    }

                    for (Function function : Variables.keySet()) {

                        JsonArray VarArray = Variables.get(function);

                        for (int i = 0; i < VarArray.size(); i++) {
                            JsonElement element = VarArray.get(i);
                            if (element instanceof JsonObject) {
                                JsonObject value = (JsonObject) element;
                                int toFunctionIndex = value.get("to").getAsInt();

                                int connectedIndex = value.get("from").getAsInt();
                                int toConnectionIndex = value.get("at").getAsInt();

                                Function toFunction = newFunc.get(toFunctionIndex);
                                function.inputs[connectedIndex] = new Connection(toFunction, toConnectionIndex, false);
                            }
                        }
                    }

                    for (Function function : Executions.keySet()) {
                        JsonArray VarArray = Executions.get(function);
                        for (int i = 0; i < VarArray.size(); i++) {
                            JsonElement element = VarArray.get(i);

                            if (element instanceof JsonObject) {
                                JsonObject value = (JsonObject) element;

                                int toFunctionIndex = value.get("to").getAsInt();
                                int toConnectionIndex = value.get("at").getAsInt();

                                Execute execute = (Execute) function;
                                Execute toFunction = (Execute) newFunc.get(toFunctionIndex);
                                execute.nextConnection[toConnectionIndex] = toFunction;
                            }
                        }
                    }
                }
            }
            script.functions = newFunc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return script;
    }

    public static void saveToFile(VisualScript script) {
        File file = script.location;
        JsonObject head = new JsonObject();

        JsonArray names = new JsonArray();
        for (Function function : script.functions) {
            Util.print(function);
            JsonObject Object = new JsonObject();

            Object.addProperty("Name", function.name);
            Object.addProperty("posX", function.x);
            Object.addProperty("posY", function.y);

            JsonObject custom = new JsonObject();
            if (function.customData != null) {
                custom.addProperty("Name", function.customData.name);
                custom.addProperty("Type", function.customData.type.name());
                custom.addProperty("Boolean", function.customData.booleanValue);
                custom.addProperty("Int", function.customData.intValue);
                custom.addProperty("Float", function.customData.floatValue);
                custom.addProperty("String", function.customData.stringValue);
                custom.addProperty("Vector", function.customData.vectorValue.toString());
                Object.add("Custom", custom);
            }

            JsonArray arrayListVar = new JsonArray();
            for (int j = 0; j < function.inputs.length; j++) {
                Connection connection = function.inputs[j];
                if (connection != null) {
                    int toFunction = script.functions.indexOf(connection.function);
                    int toFunctionIndex = connection.connectionIndex;
                    JsonObject str = new JsonObject();

                    str.addProperty("from", "" + j);
                    str.addProperty("to", "" + toFunction);
                    str.addProperty("at", "" + toFunctionIndex);
                    arrayListVar.add(str);
                }
            }

            JsonArray arrayListExe = new JsonArray();
            if (function instanceof Execute) {
                Execute execute = (Execute) function;

                for (int j = 0; j < execute.size; j++) {
                    if (execute.nextConnection != null && execute.nextConnection[j] != null) {
                        int toFunction = script.functions.indexOf(execute.nextConnection[j]);

                        JsonObject str = new JsonObject();

                        str.addProperty("to", "" + toFunction);
                        str.addProperty("at", "" + j);

                        arrayListExe.add(str);
                    }
                }
            }

            Object.add("Variables", arrayListVar);
            Object.add("Execution", arrayListExe);

            names.add(Object);
        }
        head.add("List", names);

        SaveUtil.saveJsonObj(file, head);
    }

}

