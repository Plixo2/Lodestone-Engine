package net.plixo.lodestone;

import com.google.gson.JsonPrimitive;
import net.minecraft.util.math.vector.Vector3d;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.engine.events.Event;
import net.plixo.lodestone.client.manager.RAssets;
import net.plixo.lodestone.client.manager.RClient;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;
import net.plixo.lodestone.client.util.io.SaveUtil;
import org.plixo.jrcos.Mapping;

import java.io.File;

/**
 * Second main class.
 * Used for functions independent from forge.
 */
public class Lodestone {
    public static LodestoneEngine lodestoneEngine;


    /**
     * First function called (only once)
     * Initialises {@link LodestoneEngine} parameter.
     * Calls load function.
     */
    public static void startClient() {

        UGui.setGlyphFontRenderer("Verdana");
        Mapping.throwObjectNullException = true;
        Mapping.classLoader = Lodestone.class.getClassLoader();
        Mapping.primitives.put(Vector3d.class, new Mapping.IObjectValue<Vector3d>() {
            @Override
            public Object toObject(JsonPrimitive jsonPrimitive) {
                String value = jsonPrimitive.getAsString();
                return UMath.getVecFromString(value);
            }

            @Override
            public Vector3d getDefault() {
                return Vector3d.ZERO;
            }

            @Override
            public String toString(Vector3d vector3d) {
                return UMath.getStringFromVector(vector3d);
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
        RAssets.load();
        RAssets.compile();
        UColor.load();
    }


    /**
     * Setups the save function.
     * Calls the save function in {@link RClient}.
     */
    public static void save() {
        RAssets.save();
    }

    public static void update(Event event) {
        Lodestone.lodestoneEngine.onEvent(event);
    }

}
