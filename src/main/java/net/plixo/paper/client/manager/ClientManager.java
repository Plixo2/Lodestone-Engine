package net.plixo.paper.client.manager;

import com.google.gson.*;
import net.plixo.paper.client.engine.behaviors.Java_Addon;
import net.plixo.paper.client.engine.behaviors.Renderer;
import net.plixo.paper.client.engine.behaviors.Visual_Script;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.GameObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Mapping;
import org.plixo.jrcos.Serializer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class ClientManager {

    public static ArrayList<Behavior> standardBehavior = new ArrayList<>();
    public static CopyOnWriteArrayList<GameObject> allEntities = new CopyOnWriteArrayList<>();

    public static void addEntity(GameObject entity) {
        allEntities.add(entity);
    }
    public static boolean removeEntity(GameObject entity) {
        if (!allEntities.contains(entity)) {
            return false;
        }
        ClientManager.allEntities.remove(entity);
        return true;
    }

    public static Behavior newInstanceByName(String name) {
        for (Behavior b : standardBehavior) {
            if (b.name.equalsIgnoreCase(name)) {
                try {
                    return (Behavior) b.getClass().getConstructors()[0].newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.print(e);
                }
            }
        }
        return null;
    }

    public static void loadEntities() {
        allEntities.clear();

        File load = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.Meta);
        JsonElement element = SaveUtil.loadFromJson(load);

        try {
            Mapping.overwriteLists = true;
           allEntities = (CopyOnWriteArrayList<GameObject>) Initializer.getObject(allEntities,element);
        } catch (Exception e) {
            Util.print(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveEntities() {
        File file = SaveUtil.getFileFromName("entities", SaveUtil.FileFormat.Meta);
        try {
            System.out.println("Save entities");
            JsonElement element = Serializer.getJson(allEntities);
            SaveUtil.saveJsonObj(file, element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
