package net.plixo.paper.client.editor.visualscript;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.UI.IGuiEvent;
import net.plixo.paper.client.engine.components.visualscript.Module;
import net.plixo.paper.client.engine.components.visualscript.VisualScriptManager;
import net.plixo.paper.client.engine.components.visualscript.event.Event;
import net.plixo.paper.client.engine.components.visualscript.function.Function;
import net.plixo.paper.client.engine.components.visualscript.function.other.Connection;
import net.plixo.paper.client.engine.components.visualscript.function.other.Execute;
import net.plixo.paper.client.engine.components.visualscript.variable.Variable;
import net.plixo.paper.client.engine.components.visualscript.variable.VariableType;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("DuplicatedCode")
public class Canvas implements IGuiEvent {
    Module mod;
    public ArrayList<DrawFunction> functions = new ArrayList<>();
    public String name;
    public JsonParser parser;

    public Canvas(Module mod) {
        this.mod = mod;
        name = mod.name;
    }

    public void addFunction(Function function, float x, float y) {

        DrawFunction draw = new DrawFunction(function);
        function.setTypes();
        draw.x = x;
        draw.y = y;

        functions.add(draw);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        for (int i = functions.size() - 1; i >= 0; i--) {
            functions.get(i).drawOnScreen(mouseX, mouseY);
        }
    }

    public void execute(String eventName, Variable var) {
        try {

            for (DrawFunction function : functions) {
                function.function.hasCalculated = false;
            }

            for (DrawFunction function : functions) {
                if (function.function instanceof Event) {
                    Event event = (Event) function.function;
                    if (event.name.equalsIgnoreCase(eventName)) {
                        event.executePrev(var);
                        event.execute();
                        event.postExecute();
                    }
                }
            }

        } catch (Exception e) {
            Util.print(e.toString() + e.getMessage());
            e.printStackTrace();
        }
    }

    public Function getDragFunction() {

        if (VisualScriptManager.dragTab < 0) {
            return null;
        }
        if (VisualScriptManager.dragTab >= functions.size()) {
            return null;
        }

        return functions.get(VisualScriptManager.dragTab).function;
    }

    @Override
    public void init() {

        parser = new JsonParser();
        loadFromFile();
    }

    void loadFromFile() {
        try {
            parser = new JsonParser();
            // functions.clear();
            ArrayList<DrawFunction> newFunc = new ArrayList<>();

            File file = mod.location;

            JsonElement obj = SaveUtil.loadFromJson(parser, file);
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

                            Function function = VisualScriptManager.getFromList(name, "");
                            if (function == null) {
                                Util.print("Cant find Function for \"" + name + "\"");
                                Util.print("The loading system might fail completely");
                                continue;
                            }
                            DrawFunction drawFunc = new DrawFunction(function);
                            drawFunc.x = x;
                            drawFunc.y = y;

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

                            drawFunc.init();
                            function.setTypes();
                            newFunc.add(drawFunc);

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

                                Function toFunction = newFunc.get(toFunctionIndex).function;

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
                                Execute toFunction = (Execute) newFunc.get(toFunctionIndex).function;
                                execute.nextConnection[toConnectionIndex] = toFunction;

                            }
                        }
                    }

                }
            }

            functions = newFunc;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        int i = 0;
        for (DrawFunction function : functions) {
            if (function.mouseClicked(this, i, mouseX, mouseY, mouseButton)) {
                break;
            }
            i += 1;
        }
    }
    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        for (DrawFunction function : functions) {
            function.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void removeFunction(DrawFunction function) {
        this.functions.remove(function);
        for (DrawFunction f : functions) {
            if (f.function instanceof Execute) {
                Execute execute = (Execute) f.function;

                for (int i = 0; i < execute.size; i++) {
                    Execute next = execute.nextConnection[i];
                    if (next == null) {
                        continue;
                    }
                    if (!containsFunction(next)) {
                        Util.print("Remove Execute Function" + next);
                        execute.nextConnection[i] = null;
                    }
                }

                for (int i = 0; i < execute.size; i++) {
                    Execute next = execute.nextConnection[i];
                    if (next == null) {
                        continue;
                    }
                    if (!containsFunction(next)) {
                        Util.print("Remove Execute Function" + next);
                        execute.nextConnection[i] = null;
                    }
                }

            }

            for (int i = 0; i < f.function.inputTypes.length; i++) {
                if (f.function.inputs[i] != null) {

                    Function connected = f.function.inputs[i].function;
                    if (!containsFunction(connected)) {
                        Util.print("Remove Normal Function" + connected);
                        f.function.inputs[i] = null;
                    }
                }
            }
        }

    }

    public boolean containsFunction(Function function) {
        for (DrawFunction f : functions) {
            if (f.function.equals(function)) {
                return true;
            }
        }
        return false;

    }

    public void saveToFile() {
        File file = mod.location;

        JsonObject head = new JsonObject();

        JsonArray names = new JsonArray();
        for (DrawFunction drawFunction : functions) {
            Function function = drawFunction.function;

            JsonObject Object = new JsonObject();

            Object.addProperty("Name", function.name);
            Object.addProperty("posX", drawFunction.x);
            Object.addProperty("posY", drawFunction.y);

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
                    int toFunction = functions.indexOf(connection.function.drawFunction);
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
                        int toFunction = functions.indexOf(execute.nextConnection[j].drawFunction);

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
