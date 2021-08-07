package net.plixo.lodestone.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.tabs.UIConsole;
import net.plixo.lodestone.client.util.io.SaveUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;


/**
 * Standard Util class
 */
public class UMath {

    /**
     * Minecraft instance
     */
    public static Minecraft mc = Minecraft.getInstance();


    public static int clamp(int value, int max, int min) {
        return Math.max(min, Math.min(value, max));
    }


    public static double clampDouble(double value, double max, double min) {

        return Math.max(min, Math.min(value, max));
    }

    public static float clampFloat(float value, float max, float min) {

        return Math.max(min, Math.min(value, max));
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


    public static void print(Object obj) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        UIConsole.consoleLines.add(formatter.format(calendar.getTime()) + " "+obj);
        mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(obj + " "));
    }

    public static void print(Object... obj) {
        print(Arrays.toString(obj));
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
                if (UMath.isNumeric(slits[0]) && UMath.isNumeric(slits[1]) && UMath.isNumeric(slits[2])) {
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


    public static String displayTrim(String name, float max) {

        String chars = "";

        for (char c : name.toCharArray()) {
            String toStr = Character.toString(c);
            if (UGui.getStringWidth(chars) > max) {
                return chars;
            }
            chars += toStr;
        }

        return name;
    }
}
