package net.plixo.paper.client.engine.components.timeline;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.plixo.paper.client.editor.tabs.TabTimeline;
import net.plixo.paper.client.util.SaveUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class Timeline {

    public ArrayList<Timepoint> timepoints = new ArrayList<>();

    static JsonParser parser = new JsonParser();

    public String name;
    public File file;

    public Timeline(String name) {
        this.name = name;
    }

    public void add(Timepoint timepoint) {
        this.timepoints.add(timepoint);
        sort();
    }

    void sort() {
        timepoints.sort(Comparator.comparingDouble(point -> point.progess));
    }

    public static Timeline loadFromFile(File file) {

        Timeline newTimeline = new Timeline(FilenameUtils.removeExtension(file.getName()));
        newTimeline.file = file;
        try {
            JsonElement element = SaveUtil.loadFromJson(parser, file);
            if (element instanceof JsonObject) {
                JsonObject object = (JsonObject) element;
                JsonArray array = object.get("List").getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject element1 = (JsonObject) array.get(i);
                    float progress = element1.get("pos").getAsFloat();
                    float level = element1.get("level").getAsFloat();
                    Timepoint point = new Timepoint(progress, level);
                    newTimeline.timepoints.add(point);
                    newTimeline.sort();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newTimeline;
    }

    public void saveToFile() {
        JsonObject obj = new JsonObject();

        JsonArray array = new JsonArray();

        for (Timepoint timepoint : timepoints) {
            JsonObject point = new JsonObject();
            point.addProperty("pos", timepoint.progess);
            point.addProperty("level", timepoint.yLevel);
            array.add(point);
        }
        obj.add("List", array);

        SaveUtil.saveJsonObj(file, obj);
    }


    public static class Timepoint {
        public float progess = 0;
        public float yLevel = 0;

        public Timepoint(float progess, float level) {
            this.progess = progess;
            this.yLevel = level;
        }
    }
}
