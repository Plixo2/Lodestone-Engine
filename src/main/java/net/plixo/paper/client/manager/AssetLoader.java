package net.plixo.paper.client.manager;

import net.plixo.paper.client.avs.newVersion.VisualScript;
import net.plixo.paper.client.avs.newVersion.functions.*;
import net.plixo.paper.client.avs.newVersion.nFunction;
import net.plixo.paper.client.engine.behaviors.Java_Addon;
import net.plixo.paper.client.engine.behaviors.Renderer;
import net.plixo.paper.client.engine.behaviors.Visual_Script;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.meta.Meta;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;
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



    public static void reloadAndCompile() {
        setCurrentMeta(null);
        setCurrentEntity(null);
        setCurrentScript(null);

        ScriptManager.deleteTemp();
        loadBehaviors();
        loadFunctions();
        loadEntities();
        EditorManager.register();
    }

    public static void save() {
        ClientManager.saveEntities();
        saveScript();
        saveMeta();
    }

    static void loadBehaviors() {
        ClientManager.standardBehavior.clear();
        ClientManager.standardBehavior.add(new Visual_Script());
        ClientManager.standardBehavior.add(new Renderer());
        ClientManager.standardBehavior.add(new Java_Addon());
    }

    static void loadEntities() {
        try {
            ClientManager.loadEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void loadFunctions() {
        FunctionManager.functions.clear();
        FunctionManager.functions.add(new Event());
        FunctionManager.functions.add(new getGround());
        FunctionManager.functions.add(new If());
        FunctionManager.functions.add(new Jump());
        FunctionManager.functions.add(new Print());
        FunctionManager.functions.add(new Script());
        FunctionManager.functions.add(new Equal());
        FunctionManager.functions.add(new Not());

        File library = SaveUtil.getFolderFromName("");
        if (!library.exists()) {
            SaveUtil.makeFolder(library);
            return;
        }
        ArrayList<File> files = new ArrayList<>();
        Util.findFiles(library.getAbsolutePath(), files, SaveUtil.FileFormat.Code);

        for (File file : files) {
            try {
                Util.print("File: " + file.getName());
                Object object = ScriptManager.loadClassFromFile(file);
                if (object instanceof nFunction) {
                    nFunction function = ((nFunction) object);
                    FunctionManager.functions.add(function);
                } else {
                    Util.print("Object is not a nFunction: " + object);
                    if (object != null) {
                        Util.print(object.getClass());
                    }
                }
            } catch (Exception e) {
                Util.print("Error at loading " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
