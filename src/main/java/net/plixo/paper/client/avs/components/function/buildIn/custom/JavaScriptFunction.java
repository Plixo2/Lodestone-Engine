package net.plixo.paper.client.avs.components.function.buildIn.custom;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.plixo.paper.client.avs.components.variable.Variable;
import net.plixo.paper.client.avs.components.variable.VariableType;
import net.plixo.paper.client.engine.components.scripting.ScriptManager;
import net.plixo.paper.client.avs.components.function.other.Connection;
import net.plixo.paper.client.avs.components.function.other.Execute;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.engine.meta.CodeMeta;
import net.plixo.paper.client.util.Util;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import java.io.File;
import java.util.ArrayList;


public class JavaScriptFunction extends Execute {

    Variable out;
    public ScriptObjectMirror function;
    public VariableType output;
    public VariableType[] input;
    public boolean execution;

    public JavaScriptFunction() {
        super("JS Function");
    }


    public void setParameter(String name, ScriptObjectMirror function, VariableType output, boolean execution, VariableType... input) {
        this.name = name;
        this.function = function;
        this.output = output;
        this.input = input;
        this.execution = execution;
    }

    @Override
    public void execute() {


        try {
            Object[] asJava = new Object[inputs.length];
            for (int index = 0; index < inputs.length; index++) {
                Connection connection = inputs[index];
                if (connection == null) {
                    asJava[index] = null;
                    continue;
                }
                Variable var = connection.variable;
                asJava[index] = var.asJavaObject();
            }

            Object o = function.call(function, asJava);
            if (o instanceof Double && out.type == VariableType.INT) {
                o = (int) Math.round((Double) o);
            }

            if (o != null && out != null) {
                try {
                    Util.loadIntoVar(out, o.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.print(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Util.print(e.getMessage());
        }
    }


    public boolean isFull() {
        for (Connection input : inputs) {
            if (input == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setTypes() {

        setNames();

        this.outputs = new Variable[]{};
        if (output != null) {
            this.outputs = new Variable[]{out = new Variable(output, "Output")};
        }

        this.inputTypes = input.clone();

        if (execution) {
            this.size = 1;
        }
        super.setTypes();
    }

    void setNames() {
        String[] names = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            names[i] = input[i].toString();
        }
        this.names = names.clone();
    }

    public static ArrayList<JavaScriptFunction> loadFromFile(File file) {
        ArrayList<JavaScriptFunction> list = new ArrayList<>();
        CodeMeta codeMeta = new CodeMeta(file);
        if (codeMeta.hasValue()) {
            Resource out = codeMeta.get(0);
            Resource in = codeMeta.get(1);
            Resource execute = codeMeta.get(2);

            VariableType output = VariableType.getType(out.getAsString());
            VariableType[] input = VariableType.getListByNames(in.getAsString());
            boolean Execution = execute.getAsBoolean();

            ScriptEngine engine = ScriptManager.getNewEngine();
            ScriptManager.setup(file, engine);
            Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            for (String name : b.keySet()) {
                Object object = b.get(name);
                if (object instanceof ScriptObjectMirror) {
                    ScriptObjectMirror mirror = (ScriptObjectMirror) object;
                    if (mirror.isFunction()) {
                        JavaScriptFunction jsFunction = new JavaScriptFunction();
                        jsFunction.setParameter(name, mirror, output, Execution, input);
                        list.add(jsFunction);
                    }
                }
            }
        }
        return list;
    }

}
