package net.plixo.paper.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.editor.tabs.TabConsole;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;

import java.util.regex.Pattern;


public class Util {

    public static Minecraft mc = Minecraft.getInstance();

    public static int clamp(int value, int max, int min) {
        return Math.max(min, Math.min(value, max));
    }

    public static double clampDouble(double value, double max, double min) {

        return Math.max(min, Math.min(value, max));
    }

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

    public static String[] getAllStringArgument(String str, String split) {
        if (!str.contains(split)) {
            return new String[]{str};
        }
        return str.split(split);
    }


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


    public static void loadIntoVar(Variable var, String name) {
        if (!name.isEmpty()) {

            boolean isTrue = name.toLowerCase().startsWith("tr");
            boolean isFalse = name.toLowerCase().startsWith("fa");
            boolean isOne = name.equalsIgnoreCase("1");
            boolean isZero = name.equalsIgnoreCase("0");

            var.vectorValue = new Vector3d(1, 1, 1);
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

    public static void print(Object obj) {

        TabConsole.consoleLines.add(new TabConsole.ConsoleLine(obj + ""));
        mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(obj + ""));
    }

    public static String replace(String str, String arg, String replace) {
        if (str.contains(arg)) {
            return str.replace(arg, replace);
        }
        return str;
    }
}
