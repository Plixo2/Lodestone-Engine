package net.plixo.paper.client.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("unused")
public class SaveUtil {

    public enum FileFormat {
        Code("js"), Hud("hud"), Other("json"), VisualScript("vs") , Model("obj");

        public String format;

        FileFormat(String format) {
            this.format = format;
        }

    }

    static String format = ".json";

    public static File getFileFromName(String name, FileFormat format) {

        return new File("Paper/" + name + "." + format.format);
    }

    public static File getFolderFromName(String name) {

        return new File("Paper/" + name);
    }

    public static ArrayList<String> listFolders(File file) {
        try {
            ArrayList<String> names = new ArrayList<>();

            if (!file.exists()) {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    file.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            File[] directories = file.listFiles(File::isDirectory);
            assert directories != null;
            for (File files : directories) {
                names.add(files.getName());
            }
            return names;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<String> loadFromFile(File file) {
        try {
            if (!file.exists()) {
                makeFile(file);
            }

            ArrayList<String> list = new ArrayList<>();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                list.add(data);
            }
            scanner.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static JsonElement loadFromJson(JsonParser parser, File file) {
        try {
            if (!file.exists()) {
                makeFile(file);
                return null;
            }
            return parser.parse(new java.io.FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void makeFile(File file) {

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void makeFolder(File file) {

        if (!file.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean save(File file, ArrayList<String> lines, boolean extra) {
        try {

            if (!file.exists()) {
                makeFile(file);
            }

            FileWriter fw = new FileWriter(file);
            for (String str : lines) {
                fw.write(str);
                if (extra)
                    fw.write(System.getProperty("line.separator"));
            }

            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean saveJsonObj(File file, JsonElement json) {

        try {
            if (!file.exists()) {
                makeFile(file);
            }

            FileWriter fw = new FileWriter(file);
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
