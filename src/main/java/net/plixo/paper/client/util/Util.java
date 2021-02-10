package net.plixo.paper.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.editor.tabs.TabConsole;
import net.plixo.paper.client.engine.components.visualscript.variable.Variable;

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
     * @param value  value to clamp between two values
     * @param max  max of the value
     * @param min  min of the value
     * @return clamped result
     */
    public static int clamp(int value, int max, int min) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * @param value  value to clamp between two values
     * @param max  max of the value
     * @param min  min of the value
     * @return clamped result
     */
    public static double clampDouble(double value, double max, double min) {

        return Math.max(min, Math.min(value, max));
    }

    /**
     * using {@code Gui.getStringWidth()} to cut a String a the max distance
     * @param name  String to cut
     * @param max  max display length
     * @return  cut String
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
     *
     * @param str  input
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
     *
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
     *
     * @param var Variable where custom data should get put in
     * @param name data as String
     */
    public static void loadIntoVar(Variable var, String name) {
        if (!name.isEmpty()) {

            boolean isTrue = name.equalsIgnoreCase("true");
            boolean isFalse = name.equalsIgnoreCase("false");
            boolean isOne = name.equalsIgnoreCase("1");
            boolean isZero = name.equalsIgnoreCase("0");

            var.vectorValue = new Vector3d(0, 0, 0);
            var.booleanValue = false;
            var.floatValue = 0;
            var.intValue = 0;

            if (name.contains(",")) {
                String[] slits = name.split(",");
                if (slits.length >= 3) {
                    if (isNumeric(slits[0]) && isNumeric(slits[1]) && isNumeric(slits[2])) {
                        try {
                            double arg0 = Double.parseDouble(slits[0]);
                            double arg1 = Double.parseDouble(slits[1]);
                            double arg2 = Double.parseDouble(slits[2]);
                            var.vectorValue = new Vector3d(arg0, arg1, arg2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (isOne) {
                var.booleanValue = true;
            }
            if (isZero) {
                var.booleanValue = false;
            }
            if (isNumeric(name)) {
                try {
                    var.floatValue = Float.parseFloat(name);
                    var.intValue = Math.round(var.floatValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (isTrue) {
                var.floatValue = 1;
                var.intValue = 1;
                var.booleanValue = true;
            } else if (isFalse) {
                var.floatValue = 0;
                var.intValue = 0;
                var.booleanValue = true;
            }
            var.stringValue = name;
        }
    }

    /**
     * Standard print for System Console and {@link TabConsole} for debugging
     * @param obj String as Obj
     */
    public static void print(Object obj) {

        TabConsole.consoleLines.add(new TabConsole.ConsoleLine(obj + ""));
        mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(obj + ""));
    }

    /**
     *
     * @param str input
     * @param arg String that should get replaced
     * @param replace String that should be placed
     * @return  replaced String
     */
    public static String replace(String str, String arg, String replace) {
        if (str.contains(arg)) {
            return str.replace(arg, replace);
        }
        return str;
    }

    /**
     * Construct a Vector from a String (e.g. "1,2,3" for x=1, y = 2, z = 3)
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
        return new Vector3d(0,0,0);
    }

    /**
     * revered Method of {@code getVecFromString()}
     * @param vec input
     * @return constructed String
     */
    public static String getStringFromVector(Vector3d vec) {
        return vec.x + "," + vec.y + "," + vec.z;
    }
}
