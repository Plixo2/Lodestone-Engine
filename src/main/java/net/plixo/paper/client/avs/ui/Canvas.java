package net.plixo.paper.client.avs.ui;

import com.google.gson.JsonParser;
import net.plixo.paper.client.avs.components.Module;
import net.plixo.paper.client.avs.components.event.Event;
import net.plixo.paper.client.avs.components.function.Function;
import net.plixo.paper.client.avs.components.function.other.Execute;
import net.plixo.paper.client.avs.components.variable.Variable;
import net.plixo.paper.client.manager.VisualScriptManager;
import net.plixo.paper.client.ui.IGuiEvent;
import net.plixo.paper.client.util.Util;

import java.util.ArrayList;


public class Canvas implements IGuiEvent {
    /*
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
            Util.print(e.getMessage());
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

    */
}
