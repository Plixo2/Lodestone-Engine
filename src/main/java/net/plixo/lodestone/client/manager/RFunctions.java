package net.plixo.lodestone.client.manager;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.engine.meta.MVisualCode;
import net.plixo.lodestone.client.util.CodeUtil;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.SaveUtil;
import net.plixo.lodestone.client.visualscript.Function;
import net.plixo.lodestone.client.visualscript.VisualCode;
import net.plixo.lodestone.client.visualscript.functions.*;
import net.plixo.lodestone.client.visualscript.functions.values.*;
import net.plixo.lodestone.client.visualscript.functions.logic.FBranch;
import net.plixo.lodestone.client.visualscript.functions.logic.FEqual;
import net.plixo.lodestone.client.visualscript.functions.logic.FIf;
import net.plixo.lodestone.client.visualscript.functions.logic.FNot;
import net.plixo.lodestone.client.visualscript.functions.math.FAdd;
import net.plixo.lodestone.client.visualscript.functions.math.FDivide;
import net.plixo.lodestone.client.visualscript.functions.math.FMinus;
import net.plixo.lodestone.client.visualscript.functions.math.FMultiply;
import net.plixo.lodestone.client.visualscript.functions.player.FJump;
import net.plixo.lodestone.client.visualscript.functions.player.FgetGround;
import net.plixo.lodestone.client.visualscript.injection.InjectedScript;
import net.plixo.lodestone.client.ui.visualscript.UIFunction;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Serializer;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class RFunctions {

    public static ArrayList<Object> queriedObjects = new ArrayList<>();

    public static ArrayList<Function> getUtil() {
        ArrayList<Function> functionArrayList = new ArrayList<>();

        functionArrayList.addAll(getSuper(new CodeUtil()));
        //functionArrayList.addAll(getOther(new CodeUtil()));

        return functionArrayList;
    }

    public static ArrayList<Function> getStandard() {
        ArrayList<Function> functionArrayList = new ArrayList<>();

        functionArrayList.add(new FIf());
        functionArrayList.add(new FEqual());
        functionArrayList.add(new FNot());

        functionArrayList.add(new FBranch());
        functionArrayList.add(new FFor());


        functionArrayList.add(new FAdd());
        functionArrayList.add(new FMinus());
        functionArrayList.add(new FMultiply());
        functionArrayList.add(new FDivide());

        functionArrayList.add(new FPrint());
        functionArrayList.add(new FScript());
        functionArrayList.add(new FEvaluate());

        functionArrayList.add(new FgetGround());
        functionArrayList.add(new FJump());


        functionArrayList.add(new FInt());
        functionArrayList.add(new FFloat());
        functionArrayList.add(new FBoolean());
        functionArrayList.add(new FText());





        return functionArrayList;
    }

    public static ArrayList<Function> getLoadedClasses() {
        ArrayList<Function> functionArrayList = new ArrayList<>();
        for (Object object : queriedObjects) {
            functionArrayList.addAll(getSuper(object));
        }
        return functionArrayList;
    }


    public static ArrayList<FSuperMethod> getSuper(Object... objects) {
        ArrayList<FSuperMethod> functionArrayList = new ArrayList<>();

        for (Object object : objects) {
            for (Method declaredMethod : object.getClass().getDeclaredMethods()) {
                if (!declaredMethod.getName().contains("$"))
                    functionArrayList.add(new FSuperMethod(object, declaredMethod));

            }
          //  for (int i = 0; i < object.getClass().getMethods().length - 9; i++) {
           //     Method method = object.getClass().getMethods()[i];
              //  functionArrayList.add(new FSuperMethod(object, method));
           // }
        }

        return functionArrayList;
    }

    public static ArrayList<FMethod> getEvent(Object... objects) {
        ArrayList<FMethod> functionArrayList = new ArrayList<>();


        for (Object object : objects) {
            for (Method declaredMethod : object.getClass().getDeclaredMethods()) {
                if (!declaredMethod.getName().contains("$"))
                    functionArrayList.add(new FMethod(object, declaredMethod));

            }
        }

        return functionArrayList;
    }

    public static ArrayList<Function> getFromSuperclass(Object object) {
        ArrayList<Function> functionArrayList = new ArrayList<>();
        functionArrayList.addAll(getStandard());
        functionArrayList.addAll(getLoadedClasses());
        functionArrayList.addAll(getUtil());
        functionArrayList.addAll(getSuper(object));
        functionArrayList.addAll(getEvent(object));

        return functionArrayList;
    }

    public static Function getInstanceByName(String name, Object object) {

        for (Function function : getFromSuperclass(object)) {
            if (function.getIDString().equalsIgnoreCase(name)) {
                return function;
            }
        }

        return null;
    }


    public static VisualCode loadFromFile(File file) {

        VisualCode script = new VisualCode(file);
        MVisualCode MVisualCode = (MVisualCode) RMeta.getMetaByFile(file);
        Class<?> clazz = MVisualCode.classResource.value;
        InjectedScript<?> injectedScript = new InjectedScript<>(clazz);
        script.object = injectedScript.create();
        try {
            HashMap<Function, JsonObject> linkMap = new HashMap<>();
            HashMap<Function, JsonObject> inputMap = new HashMap<>();


            JsonArray element = SaveUtil.loadFromJson(file).getAsJsonArray();
            for (int i = 0; i < element.size(); i++) {
                JsonObject object = element.get(i).getAsJsonObject();
                UUID uuid = UUID.fromString(object.get("Id").getAsString());
                String classPath = object.get("Class").getAsString();
                String ClassName = object.get("Name").getAsString();
                boolean state = object.get("Small").getAsBoolean();
                String displayName = object.get("Display").getAsString();
                float x = object.get("X").getAsFloat();
                float y = object.get("Y").getAsFloat();


                Function instance = getInstanceByName(ClassName, script.object);
                if(instance == null) {
                    System.out.println(ClassName);
                    System.out.println(script.object.getClass().getSimpleName());
                }
                Objects.requireNonNull(instance);
                instance.setId(uuid);
                instance.setCustomDisplayString(displayName);
                script.getFunctions().add(instance);
                instance.set();
                instance.setUI(new UIFunction(instance));
                instance.getUI().isSmall = state;

                JsonObject resource = object.get("Resources").getAsJsonObject();


                JsonObject links = object.get("Links").getAsJsonObject();
                JsonObject inputs = object.get("Inputs").getAsJsonObject();
                linkMap.put(instance, links);
                inputMap.put(instance, inputs);

                for (Resource<?> setting : instance.getSettings()) {
                    try {
                        JsonObject jsonObject = resource.get(setting.getName()).getAsJsonObject();
                        setting = (Resource<?>) Initializer.getObject(setting, jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                instance.getUI().setDimensions(x, y, instance.getUI().getWidth(), 20);
            }

            try {
                for (Function function : script.getFunctions()) {
                    JsonObject link = linkMap.get(function);
                    JsonObject input = inputMap.get(function);
                    Objects.requireNonNull(link);
                    Objects.requireNonNull(input);

                    for (int i = 0; i < function.getLinks().length; i++) {
                        String id = link.get("" + i).getAsString();
                        if (id.isEmpty()) {
                            continue;
                        }
                        UUID uuid = UUID.fromString(id);

                        function.getLinks()[i] = script.getByUUID(uuid);
                    }

                    for (int i = 0; i < function.getInput().length; i++) {
                        String id = input.get("" + i).getAsString();
                        int linkIndex = -1;
                        JsonElement element1 = input.get("L" + i);
                        if (element1 != null) {
                            linkIndex = element1.getAsInt();
                        }
                        if (id.isEmpty()) {
                            continue;
                        }
                        UUID uuid = UUID.fromString(id);

                        try {
                            Function Function = script.getByUUID(uuid);
                            if (Function != null && linkIndex >= 0) {
                                function.getInput()[i] = Function.getOutput()[linkIndex];
                            }
                        } catch (Exception e) {
                            UMath.print(e);
                            e.printStackTrace();
                        }
                    }
                }

                injectedScript.registerMethods(script.getFunctions());


                //create (object instance via InjectedScript) , inject it and override FMethod and FSuperMethod


            } catch (Exception e) {
                UMath.print(e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            UMath.print(e.getMessage());
            e.printStackTrace();
        }
        return script;
    }

    public static void saveToFile(VisualCode script) {
        JsonArray array = new JsonArray();

        for (Function function : script.getFunctions()) {
            JsonObject body = new JsonObject();

            body.addProperty("Id", function.getId().toString());
            body.addProperty("Name", function.getIDString());
            body.addProperty("Class", function.getClass().getName());
            body.addProperty("Small", function.getUI().isSmall);
            body.addProperty("Display",function.getCustomDisplayString());
            body.addProperty("X", function.getUI().x);
            body.addProperty("Y", function.getUI().y);

            JsonObject resources = new JsonObject();
            for (Resource<?> setting : function.getSettings()) {
                try {
                    JsonElement element = Serializer.getJson(setting);
                    resources.add(setting.getName(), element);
                } catch (Exception e) {
                    UMath.print(e);
                    e.printStackTrace();
                }
            }
            body.add("Resources", resources);


            JsonObject links = new JsonObject();
            for (int i = 0; i < function.getLinks().length; i++) {
                Function link = function.getLinks()[i];
                links.addProperty("" + i, link == null ? "" : link.getId().toString());
            }
            body.add("Links", links);

            JsonObject inputs = new JsonObject();
            for (int i = 0; i < function.getInput().length; i++) {
                Function.Output input = function.getInput()[i];
                inputs.addProperty("" + i, input == null ? "" : input.function.getId().toString());
                if (input != null) {
                    int linkI = -1;
                    if (input.function.getOutput().length > 0)
                        for (int i1 = 0; i1 < input.function.getOutput().length; i1++) {
                            Function.Output out = input.function.getOutput()[i1];
                            if (out == input) {
                                linkI = i1;
                                break;
                            }
                        }
                    if (linkI == -1) {
                        UMath.print("Something went wrong");
                    }
                    inputs.addProperty("L" + i, linkI);
                } else {
                    inputs.addProperty("L" + i, "-1");
                }
            }
            body.add("Inputs", inputs);

            array.add(body);
        }
        SaveUtil.saveJsonObj(script.location, array);

    }
}
