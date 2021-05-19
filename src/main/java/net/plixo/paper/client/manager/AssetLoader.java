package net.plixo.paper.client.manager;

import net.plixo.paper.client.engine.behaviors.Java_Addon;
import net.plixo.paper.client.engine.behaviors.Renderer;
import net.plixo.paper.client.engine.behaviors.Visual_Script;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.meta.Meta;
import net.plixo.paper.client.util.ClassPaths;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import net.plixo.paper.client.visualscript.Function;
import net.plixo.paper.client.visualscript.VisualScript;
import net.plixo.paper.client.visualscript.functions.Jump;
import net.plixo.paper.client.visualscript.functions.Object;
import net.plixo.paper.client.visualscript.functions.Print;
import net.plixo.paper.client.visualscript.functions.events.KeyEvent;
import net.plixo.paper.client.visualscript.functions.events.StartEvent;
import net.plixo.paper.client.visualscript.functions.events.StopEvent;
import net.plixo.paper.client.visualscript.functions.events.TickEvent;
import net.plixo.paper.client.visualscript.functions.getGround;
import net.plixo.paper.client.visualscript.functions.logic.Branch;
import net.plixo.paper.client.visualscript.functions.logic.Equal;
import net.plixo.paper.client.visualscript.functions.logic.If;
import net.plixo.paper.client.visualscript.functions.logic.Not;
import net.plixo.paper.client.visualscript.functions.math.Add;
import net.plixo.paper.client.visualscript.functions.math.Divide;
import net.plixo.paper.client.visualscript.functions.math.Minus;
import net.plixo.paper.client.visualscript.functions.math.Multiply;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class AssetLoader {

    static VisualScript currentScript = null;
    static GameObject currentEntity;
    static Meta currentMeta;

    public static void setCurrentScript(VisualScript script) {
        saveScript();
        currentScript = script;
    }

    public static void saveScript() {
        if (currentScript != null) {
            FunctionManager.saveToFile(currentScript);
        }
    }

    public static VisualScript getLoadedScript() {
        return currentScript;
    }

    public static void setCurrentMeta(Meta script) {
        saveMeta();
        currentMeta = script;
    }

    public static void saveMeta() {
        if (currentMeta != null) {
            currentMeta.saveMeta();
        }
    }


    public static Meta getLoadedMeta() {
        return currentMeta;
    }

    public static void setCurrentEntity(GameObject entity) {
        currentEntity = entity;
    }

    public static GameObject getLoadedEntity() {
        return currentEntity;
    }


    public static void load() {
        try {
            System.out.println("Loading...");
            setCurrentMeta(null);
            setCurrentEntity(null);

            loadBehaviors();
            loadEntities();
            loadEntities();
            loadEntities();
            EditorManager.register();
            Util.print("Loaded");
        } catch (Exception e) {
            Util.print("Error at Loading: " + e);
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            System.out.println("Start Saving");
            ClientManager.saveEntities();
            System.out.println("Saved Entities");
            saveScript();
            System.out.println("Saved Script");
            saveMeta();
            System.out.println("Saved Meta");
            EditorManager.editor.close();
            System.out.println("Saved Editor File");
            Util.print("Saved");
        } catch (Exception e) {
            Util.print("Error at Saving: " + e);
            e.printStackTrace();
        }
    }

    public static void compile() {
        try {
            System.out.println("Compiling...");
            ClassPaths.generate();
            ScriptManager.deleteTemp();
            System.out.println("Deleted temp File");
            loadFunctions();
            Util.print("Compiled");
        } catch (Exception e) {
            Util.print("Error at compiling: " + e);
            e.printStackTrace();
        }
    }

    static void loadBehaviors() {
        ClientManager.standardBehavior.clear();
        ClientManager.standardBehavior.add(new Visual_Script());
        ClientManager.standardBehavior.add(new Renderer());
        ClientManager.standardBehavior.add(new Java_Addon());
        System.out.println("Loaded " + ClientManager.standardBehavior.size() + " Behaviors");
    }

    static void loadEntities() {
        try {
            ClientManager.loadEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Loaded " + ClientManager.allEntities.size() + " Entities");
    }

    static void loadFunctions() {
        FunctionManager.functions.clear();
        FunctionManager.MetaNameMap.clear();
        FunctionManager.functions.add(new TickEvent());
        FunctionManager.functions.add(new KeyEvent());
        FunctionManager.functions.add(new StartEvent());
        FunctionManager.functions.add(new StopEvent());

        FunctionManager.functions.add(new If());
        FunctionManager.functions.add(new Equal());
        FunctionManager.functions.add(new Not());
        FunctionManager.functions.add(new Branch());

        FunctionManager.functions.add(new Add());
        FunctionManager.functions.add(new Minus());
        FunctionManager.functions.add(new Multiply());
        FunctionManager.functions.add(new Divide());

        FunctionManager.functions.add(new Print());
        FunctionManager.functions.add(new Object());
        FunctionManager.functions.add(new getGround());
        FunctionManager.functions.add(new Jump());

        File library = SaveUtil.getFolderFromName("");
        if (!library.exists()) {
            SaveUtil.makeFolder(library);
            return;
        }
        ArrayList<File> files = new ArrayList<>();
        Util.findFiles(library.getAbsolutePath(), files, SaveUtil.FileFormat.Code);

        for (File file : files) {
            try {
                FunctionManager.MetaNameMap.put(FilenameUtils.removeExtension(file.getName()), MetaManager.getMetaByFile(file));
                java.lang.Object object = ScriptManager.loadClassFromFile(file);
                if (object instanceof Function) {
                    Class c = object.getClass();
                    if (c != null) {
                        Method[] methodsb = c.getDeclaredMethods();
                        for (int i = 0; i < methodsb.length; i++) {
                            // Util.print("B: " + methodsb[i].getName());
                            //TODO something with this
                        }
                    }
                    Function function = ((Function) object);
                    FunctionManager.functions.add(function);
                } else {
                    Util.print(file.getName() + " is not a nFunction");
                    if (object != null) {
                        Util.print("Class: " + object.getClass());
                    }
                }
            } catch (Exception e) {
                Util.print("Error at loading " + file.getName());
                e.printStackTrace();
            }
        }
        System.out.println("Loaded " + FunctionManager.functions.size() + " Functions");
    }
}
