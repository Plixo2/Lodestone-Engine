package net.plixo.lodestone.client.manager;

import com.google.gson.*;
import net.plixo.lodestone.client.engine.core.Behavior;
import net.plixo.lodestone.client.engine.core.GameObject;
import net.plixo.lodestone.client.util.io.SaveUtil;
import net.plixo.lodestone.client.util.UMath;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Mapping;
import org.plixo.jrcos.Serializer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class RClient {

    public static ArrayList<Behavior> standardBehavior = new ArrayList<>();
    public static CopyOnWriteArrayList<GameObject> allEntities = new CopyOnWriteArrayList<>();

    public static void addEntity(GameObject entity) {
        allEntities.add(entity);
    }
    public static void removeEntity(GameObject entity) {
        if (!allEntities.contains(entity)) {
            return;
        }
        RClient.allEntities.remove(entity);
    }

    public static Behavior newInstanceByName(String name) {
        for (Behavior b : standardBehavior) {
            if (b.name.equalsIgnoreCase(name)) {
                try {
                    return (Behavior) b.getClass().getConstructors()[0].newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    UMath.print(e);
                }
            }
        }
        return null;
    }

    public static void loadEntities() {
        allEntities.clear();

        File load = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.META);
        JsonElement element = SaveUtil.loadFromJson(load);

        try {
            Mapping.overwriteLists = true;
           allEntities = (CopyOnWriteArrayList<GameObject>) Initializer.getObject(allEntities,element);
        } catch (Exception e) {
            UMath.print(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveEntities() {
        File file = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.META);
        try {
            System.out.println("Save entities");
            JsonElement element = Serializer.getJson(allEntities);
            SaveUtil.saveJsonObj(file, element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
