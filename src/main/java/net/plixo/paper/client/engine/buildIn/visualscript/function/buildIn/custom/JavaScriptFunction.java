package net.plixo.paper.client.engine.buildIn.visualscript.function.buildIn.custom;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.Lodestone;
import net.plixo.paper.client.engine.buildIn.visualscript.function.Function;
import net.plixo.paper.client.engine.buildIn.visualscript.function.other.Execute;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.VariableType;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FilenameUtils;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class JavaScriptFunction extends Function {

    Variable out;
    public File file;
    ScriptEngine engine;

    public JavaScriptFunction() {
        super("js");
    }


    public void set(File file) {
        this.file = file;
        String name = FilenameUtils.getBaseName(file.getName());
        this.name = "js: " + name;
    }

    @Override
    public void execute() {


        if (isFull()) {

            try {
                Invocable invocable = (Invocable) engine;
                Object obj = engine.get("execute");

                Object[] asJava = new Object[inputs.length];

                for (int index = 0; index < inputs.length; index++) {
                    Variable var = value(index);
                    asJava[index] = var.asJavaObject();
                }

                if (obj != null) {
                    Object o = invocable.invokeFunction("execute", asJava);
                    if (o instanceof Double && out.type == VariableType.INT) {
                        o = (int) Math.round((Double) o);
                    }
                    if (o != null) {
                        out.setValue(o);
                    }
                } else {
                    Util.print("Cant find \"execute\" function");
                }
            } catch (Exception e) {
                Util.print(e.getMessage());
            }
        }
    }


    public boolean isFull() {
        for (int index = 0; index < inputs.length; index++) {
            if (inputs[index] == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setTypes() {

        VariableType[] varTypes = new VariableType[0];
        String[] args = new String[0];
        VariableType typeOut = VariableType.INT;
        String msg = "";

        try {
            set(file);
            engine = Lodestone.paperEngine.scriptEngineManager.getEngineByName("nashorn");
            engine.eval(new FileReader(file));

            Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            Object output = find("output", b);
            if (output instanceof String) {
                typeOut = VariableType.getType((String) output);
                if (typeOut == null) {
                    typeOut = VariableType.INT;
                    msg = "\"" + ((String) output) + "\" is not a Variable type.";
                    Util.print("\u00A7c" + msg);
                }
            } else {
                msg = ("\"output\" not found or not a String.");
                Util.print("\u00A7c" + msg);
            }
            Object input = find("input", b);
            if (input instanceof ScriptObjectMirror) {
                try {
                    ScriptObjectMirror result = (ScriptObjectMirror) input;
                    args = result.to(String[].class);
                    varTypes = new VariableType[args.length];
                    for (int i = 0; i < args.length; i++) {
                        varTypes[i] = VariableType.getType(args[i]);
                    }
                } catch (Exception e) {
                    msg = ("\"input\" is not a string array.");
                    Util.print("\u00A7c" + msg);
                }
            } else {
                if (input == null) {
                    msg = ("\"input\" not found.");
                    Util.print("\u00A7c" + msg);
                } else {
                    msg = ("\"input\" not valid.");
                    Util.print("\u00A7c" + msg);
                }
            }

            Object obj = engine.get("execute");
            if (obj == null) {
                msg = ("\"execute\" Function not found");
                Util.print("\u00A7c" + msg);
            }
        } catch (Exception e) {
            Util.print("\u00A7c" + e.getMessage());
            printInstruction();
            e.printStackTrace();
        }
        if (!msg.isEmpty()) {
            printInstruction();
        }
        this.outputs = new Variable[]{out = new Variable(typeOut, "Output")};
        this.inputTypes = varTypes.clone();
        this.names = args.clone();

//TODO isExecution Variable
    //    this.size = 1;
        super.setTypes();
    }

    public void printInstruction() {
        Util.print("<");
        Util.print("Declare \"Output\" as String.");
        Util.print("e.g. var output = \"float\";");
        Util.print("<");
        Util.print("Declare \"Input\" as an String Array.");
        Util.print("e.g. var input = [\"float\", \"float\"];");
        Util.print("<");
        Util.print("Variable Types: ");
        Util.print(VariableType.INT.getStringColor() + "\"Int\"");
        Util.print(VariableType.FLOAT.getStringColor() + "\"Float\"");
        Util.print(VariableType.BOOLEAN.getStringColor() + "\"Boolean\"");
        Util.print(VariableType.STRING.getStringColor() + "\"String\"");
        Util.print(VariableType.VECTOR.getStringColor() + "\"Vector\"");
        Util.print("<");
        Util.print("usage for Vectors:");
        Util.print("var vec3 = ");
        Util.print("Java.type(\"net.minecraft.util.math.vector.Vector3d\");");
        Util.print("e.g var vecOne = new vec3(1,1,1);");
        Util.print("<");
        Util.print("usage for Calculations: ");
        Util.print("function execute({inputs} {}");
        Util.print("e.g.:");
        Util.print("function execute(multiplicand,multiplier) {");
        Util.print("var product = multiplicand * multiplier;");
        Util.print("return product;");
        Util.print("}");
        Util.print("<");
    }

    public Object find(String name, Bindings b) {
        for (String str : b.keySet()) {
            if (str.equalsIgnoreCase(name)) {
                return b.get(str);
            }
        }
        return null;
    }
}
