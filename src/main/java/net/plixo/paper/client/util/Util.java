package net.plixo.paper.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.manager.EditorManager;
import net.plixo.paper.client.tabs.TabConsole;
import net.plixo.paper.client.ui.tabs.UIConsole;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;


/**
 * Standard Util class
 */
public class Util {

    /**
     * Minecraft instance
     */
    public static Minecraft mc = Minecraft.getInstance();

    /**
     * @param value value to clamp between two values
     * @param max   max of the value
     * @param min   min of the value
     * @return clamped result
     */
    public static int clamp(int value, int max, int min) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * @param value value to clamp between two values
     * @param max   max of the value
     * @param min   min of the value
     * @return clamped result
     */
    public static double clampDouble(double value, double max, double min) {

        return Math.max(min, Math.min(value, max));
    }

    /**
     * @param value value to clamp between two values
     * @param max   max of the value
     * @param min   min of the value
     * @return clamped result
     */
    public static float clampFloat(float value, float max, float min) {

        return Math.max(min, Math.min(value, max));
    }

    /**
     * using {@code Gui.getStringWidth()} to cut a String a the max distance
     *
     * @param name String to cut
     * @param max  max display length
     * @return cut String
     */
    public static String displayTrim(String name, float max) {

        String chars = "";

        for (char c : name.toCharArray()) {
            String toStr = Character.toString(c);
            if (Gui.getStringWidth(chars) > max) {
                return chars;
            }
            chars += toStr;
        }

        return name;
    }

    /**
     * @param str   input
     * @param split characters the split the String
     * @return String split
     */
    public static String[] getAllStringArgument(String str, String split) {
        if (!str.contains(split)) {
            return new String[]{str};
        }
        return str.split(split);
    }

    /**
     * @param str number as String
     * @return is String a number
     */
    public static boolean isNumeric(String str) {

        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c) && c != '.' && c != '-') {
                return false;
            }
        }
        if (str.contains("-")) {
            int l = str.split(Pattern.quote("-")).length - 1;
            if (!str.startsWith("-") || l > 1) {
                return false;
            }
        }
        if (str.contains(".")) {
            int l = str.split(Pattern.quote(".")).length - 1;
            if (l > 1) {
                return false;
            }
        }

        return !str.endsWith("-") && !str.endsWith(".");
    }


    /**
     * Standard print for System Console and {@link TabConsole} for debugging
     *
     * @param obj String as Obj
     */
    public static void print(Object obj) {
        UIConsole.consoleLines.add(new UIConsole.ConsoleLine(obj + ""));
        mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(obj + ""));
    }

    public static void print(Object... obj) {

        Util.print(">");
        for (Object o : obj) {
            UIConsole.consoleLines.add(new UIConsole.ConsoleLine(o + ""));
            mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(o + ""));
        }
        Util.print("<");
    }

    /**
     * @param str     input
     * @param arg     String that should get replaced
     * @param replace String that should be placed
     * @return replaced String
     */
    public static String replace(String str, String arg, String replace) {
        if (str.contains(arg)) {
            return str.replace(arg, replace);
        }
        return str;
    }

    /**
     * Construct a Vector from a String (e.g. "1,2,3" for x=1, y = 2, z = 3)
     *
     * @param str Input
     * @return constructed String
     */
    public static Vector3d getVecFromString(String str) {
        if (str.contains(",")) {
            String[] slits = str.split(",");
            if (slits.length >= 3) {
                if (Util.isNumeric(slits[0]) && Util.isNumeric(slits[1]) && Util.isNumeric(slits[2])) {
                    try {
                        double arg0 = Double.parseDouble(slits[0]);
                        double arg1 = Double.parseDouble(slits[1]);
                        double arg2 = Double.parseDouble(slits[2]);
                        return new Vector3d(arg0, arg1, arg2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new Vector3d(0, 0, 0);
    }

    public static void findFiles(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    findFiles(file.getAbsolutePath(), files);
                }
            }
    }

    /**
     * revered Method of {@code getVecFromString()}
     *
     * @param vec input
     * @return constructed String
     */
    public static String getStringFromVector(Vector3d vec) {
        return vec.x + "," + vec.y + "," + vec.z;
    }


    public static void findFiles(String directoryName, ArrayList<File> files, SaveUtil.FileFormat format) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile() && SaveUtil.FileFormat.getFromFile(file) == format) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    findFiles(file.getAbsolutePath(), files, format);
                }
            }
    }
    public interface IObject<O> {
        public void run(O object);
    }
    public interface IGetObject<O> {
        public O run();
    }
}
