package net.plixo.paper;

import com.google.gson.JsonPrimitive;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.events.ClientEvent;
import net.plixo.paper.client.manager.AssetLoader;
import net.plixo.paper.client.manager.ClientManager;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import net.plixo.paper.client.visualscript.CustomFunction;
import org.plixo.jrcos.Mapping;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.io.File;

/**
 * Second main class.
 * Used for functions independent from forge.
 */
public class Lodestone {
    static long lastMS = 0;
    public static LodestoneEngine lodestoneEngine;
    CustomFunction n;


    /**
     * First function called (only once)
     * Initialises {@link LodestoneEngine} parameter.
     * Calls load function.
     */
    public static void startClient() {


        Mapping.throwObjectNullException = true;
        Mapping.classLoader = Lodestone.class.getClassLoader();
        Mapping.primitives.put(Vector3d.class, new Mapping.IObjectValue<Vector3d>() {
            @Override
            public Object toObject(JsonPrimitive jsonPrimitive) {
                String value = jsonPrimitive.getAsString();
                return Util.getVecFromString(value);
            }

            @Override
            public Vector3d getDefault() {
                return Vector3d.ZERO;
            }

            @Override
            public String toString(Vector3d vector3d) {
                return Util.getStringFromVector(vector3d);
            }
        });



        Mapping.primitives.put(File.class, new Mapping.IObjectValue<File>() {
            @Override
            public Object toObject(JsonPrimitive jsonPrimitive) {
                return new File(jsonPrimitive.getAsString());
            }

            @Override
            public File getDefault() {
                return SaveUtil.getFolderFromName("");
            }

            @Override
            public String toString(File file) {
                return file.getAbsolutePath();
            }
        });


        lodestoneEngine = new LodestoneEngine();
        AssetLoader.load();
        AssetLoader.compile();
        ColorLib.load();
    }


    /**
     * Setups the save function.
     * Calls the save function in {@link ClientManager}.
     */
    public static void save() {
        AssetLoader.save();
    }

    public static void update(ClientEvent event) {
        Lodestone.lodestoneEngine.onEvent(event);
    }

}
