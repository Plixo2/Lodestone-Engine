package net.plixo.lodestone.client.manager;

import net.plixo.lodestone.client.engine.behaviors.BJava;
import net.plixo.lodestone.client.engine.behaviors.BRender;
import net.plixo.lodestone.client.engine.behaviors.BVisualCode;
import net.plixo.lodestone.client.engine.core.GameObject;
import net.plixo.lodestone.client.engine.core.Meta;
import net.plixo.lodestone.client.util.ClassPaths;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.SaveUtil;
import net.plixo.lodestone.client.util.serialiable.Options;
import net.plixo.lodestone.client.visualscript.VisualCode;

import java.io.File;
import java.util.ArrayList;

public class RAssets {

    static VisualCode currentScript = null;
    static GameObject currentEntity;
    static Meta currentMeta;

    public static void setCurrentScript(VisualCode script) {
        saveScript();
        currentScript = script;
    }

    public static void saveScript() {
        if (currentScript != null) {
            RFunctions.saveToFile(currentScript);
        }
    }

    public static VisualCode getLoadedScript() {
        return currentScript;
    }

    public static void setCurrentMeta(Meta script) {
        saveMeta();
        currentMeta = script;
    }

    public static void saveMeta() {
        if (currentMeta != null) {
            currentMeta.save();
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

            Options.options = Options.load(Options.options);

            loadBehaviors();
            loadEntities();
            REditor.register();
            UMath.print("Loaded");
        } catch (Exception e) {
            UMath.print("Error at Loading: " + e);
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            System.out.println("Start Saving");

            Options.options.save();
            System.out.println("Saved Options");

            RClient.saveEntities();
            System.out.println("Saved Entities");
            saveScript();
            System.out.println("Saved Script");
            saveMeta();
            System.out.println("Saved Meta");
//            EditorManager.editor.close();
            System.out.println("Saved Editor File");
            UMath.print("Saved");
        } catch (Exception e) {
            UMath.print("Error at Saving: " + e);
            e.printStackTrace();
        }
    }

    public static void compile() {
        try {
            System.out.println("Compiling...");
            ClassPaths.generate();
            RScript.deleteTemp();
            System.out.println("Deleted temp File");
            loadFunctions();
            UMath.print("Compiled");
        } catch (Exception e) {
            UMath.print("Error at compiling: " + e);
            e.printStackTrace();
        }
    }

    static void loadBehaviors() {
        RClient.standardBehavior.clear();
        RClient.standardBehavior.add(new BVisualCode());
        RClient.standardBehavior.add(new BRender());
        RClient.standardBehavior.add(new BJava());
        System.out.println("Loaded " + RClient.standardBehavior.size() + " Behaviors");
    }

    static void loadEntities() {
        try {
            RClient.loadEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Loaded " + RClient.allEntities.size() + " Entities");
    }

    static void loadFunctions() {
        RFunctions.queriedObjects.clear();
        File library = SaveUtil.getFolderFromName("");
        if (!library.exists()) {
            SaveUtil.makeFolder(library);
            return;
        }
        ArrayList<File> files = new ArrayList<>();
        UMath.findFiles(library.getAbsolutePath(), files, SaveUtil.FileFormat.CODE);
        for (File file : files) {
            try {
                Object object = RScript.loadClassFromFile(file);
                if (object != null) {
                    RFunctions.queriedObjects.add(object);
                }
            } catch (Exception e) {
                UMath.print("Error at loading " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
