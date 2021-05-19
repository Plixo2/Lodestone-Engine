package net.plixo.paper.client.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used for Input and output File handling
 */
public class SaveUtil {

    /**
     * Used to have different formats separately and globally
     */
    public enum FileFormat {
        Code("java"), Hud("hud"), Other("json"), VisualScript("vs"), Model("obj"), Asset("asset") , Meta("meta") ;

        public String format;

        /**
         * @param format File extension for File use
         */
        FileFormat(String format) {
            this.format = format;
        }

        public static FileFormat getFromFile(File file) {
            String extension = FilenameUtils.getExtension(file.getAbsolutePath());
            for (FileFormat value : FileFormat.values()) {
                if(value.format.equals(extension)) {
                    return value;
                }
            }
            return null;
        }
    }

    /**
     * @param name File name
     * @param format {@link FileFormat} as extension
     * @return constructed File
     */
    public static File getFileFromName(String name, FileFormat format) {
        return new File("Paper/" + name + "." + format.format);
    }

    /**
     * @param name Folder name
     * @return constructed File from Folder
     */
    public static File getFolderFromName(String name) {
        return new File("Paper/" + name);
    }

    /**
     * @param file Folder location
     * @return array of all Folders in File
     */
    public static ArrayList<String> listFolders(File file) {
        try {
            ArrayList<String> names = new ArrayList<>();

            if (!file.exists()) {
                try {
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

    /**
     * @param file File to read
     * @return String array for lines
     */
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

    /**
     * @param parser {@link JsonParser} to parse (can be {@code new JsonParser()})
     * @param file File to read
     * @return constructed {@link JsonElement}
     */
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

    /**
     * @param file the File to construct
     */
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

    /**
     * @param file to Folder to construct
     */
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

    /**
     * @param file File to save the Array
     * @param lines array of the lines
     * @param extra should add extra line breakJsonParser?
     * @return was save successful?
     */
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

    /**
     *
     * @param file  File to save the Element
     * @param json {@link JsonElement} to save
     * @return was save successful?
     */
    public static void saveJsonObj(File file, JsonElement json) {

        try {
            if (!file.exists()) {
                makeFile(file);
            }

            FileWriter fw = new FileWriter(file);
            fw.write(json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path Path to File
     * @return String with linebreak chars
     */
    public static String loadAsString(String path) {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Couldn't find the file at " + path);
        }

        return result.toString();
    }

}
