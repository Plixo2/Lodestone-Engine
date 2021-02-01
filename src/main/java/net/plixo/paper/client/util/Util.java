package net.plixo.paper.client.util;

import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class Util {

	static int charSelected = 0;
	static int lineSelected = 0;
	public static Minecraft mc = Minecraft.getInstance();

	public static String addChar(String str, String ch, int position) {
		return str.substring(0, position) + ch + str.substring(position);
	}

	public static int clamp(int value, int max, int min) {
		return Math.max(min, Math.min(value, max));
	}

	public static float clampFloat(double value, double max, double min) {
		return (float) Math.max(min, Math.min(value, max));
	}

	public static void clickCursor(int hoverIndex, float mouseX, float mouseY, ArrayList<String> code) {
		lineSelected = hoverIndex;
		lineSelected = clamp(lineSelected, code.size() - 1, 0);

		if (code.size() > 0) {
			String str = code.get(lineSelected);
			float red = mouseX - 16;

			charSelected = 0;
			String newStr = "";
			int index = 0;
			for (char c : str.toCharArray()) {
				String toString = Character.toString(c);
				newStr += toString;
				index += 1;

				float width = Gui.getStringWidth(newStr);
				if (width < red) {
					charSelected = index;
				}
			}
		}
	}

	public static String[] colorArgs(String[] args, int index, String colorCode) {

		args[index] = colorCode + args[index] + "�r";

		return args;
	}

	public static String combine(int from, int to, String[] args) {

		to = clamp(to, args.length - 1, from);

		String str = "";
		for (int i = from; i <= to; i++) {
			str += args[i];
		}
		return str;
	}

	public static String combineWithSpace(int from, int to, String[] args) {

		to = clamp(to, args.length - 1, from);

		String str = "";
		for (int i = from; i <= to; i++) {
			str += " " + args[i];
		}
		return str;
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
			return new String[] { str };
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
		if(str.contains("-")) {
			int l = str.split(Pattern.quote("-")).length-1;
			if(!str.startsWith("-") || l > 1) {
				return false;
			} 
		}
		if(str.contains(".")) {
			int l = str.split(Pattern.quote(".")).length-1;
			if(l > 1) {
				return false;
			} 
		}
		
		if(str.endsWith("-") || str.endsWith(".")) {
			return false;
		}
		return true;
	}



	public static void loadIntoVar(Variable var, String name) {
		if (!name.isEmpty() && !name.isEmpty()) {

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
							double arg0 = Double.valueOf(slits[0]);
							double arg1 = Double.valueOf(slits[1]);
							double arg2 = Double.valueOf(slits[2]);
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
					var.floatValue = Float.valueOf(name);
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

	public static String mark(String l, String arg, String code, String resetCode) {

		if (l.contains(arg)) {
			l = l.replace(arg, code + arg + resetCode);
		}

		return l;
	}

	public static void moveCursor(int keyCode, char typedChar, ArrayList<String> code) {
		if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
			if (code.size() > 0) {
				String str = code.get(lineSelected);
				if (str.length() >= 1) {

					if (charSelected == 0 && lineSelected >= 1) {
						String beginn = code.get(lineSelected - 1);
						code.set(lineSelected - 1, beginn + str);
						code.remove(lineSelected);
						charSelected = beginn.length();
						lineSelected -= 1;
					} else {
						if (lineSelected == 0 && charSelected == 0) {
							return;
						}
						int toLocalRange = clamp(charSelected, str.length(), 0);
						String finalString = str.substring(0, toLocalRange - 1) + str.substring(toLocalRange);
						code.set(lineSelected, finalString);
						charSelected = toLocalRange - 1;
					}

				} else {
					code.remove(lineSelected);

				}
			}
		} else if (keyCode == GLFW.GLFW_KEY_ENTER) {
			if (code.size() == 0) {
				code.add("");
			} else {
				String str = code.get(lineSelected);
				int toLocalRange = clamp(charSelected, str.length(), 0);
				String left = str.substring(0, toLocalRange);
				String right = str.substring(toLocalRange);

				code.set(lineSelected, left);
				code.add(lineSelected + 1, right);

				lineSelected = lineSelected + 1;
				charSelected = 0;
			}

		} else if (keyCode == GLFW.GLFW_KEY_DOWN) {
			lineSelected += 1;
		} else if (keyCode == GLFW.GLFW_KEY_UP) {
			lineSelected -= 1;
		} else if (keyCode == GLFW.GLFW_KEY_LEFT) {
			charSelected -= 1;
		} else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
			charSelected += 1;
		} else {
			if (code.size() > 0) {

				if (SharedConstants.isAllowedCharacter(typedChar)) {

					String str = code.get(lineSelected);
					String toString = Character.toString(typedChar);

					int toLocalRange = clamp(charSelected, str.length(), 0);
					String finalStr = addChar(str, toString, toLocalRange);
					code.set(lineSelected, finalStr);
					charSelected += 1;
				}
			}
		}

		lineSelected = clamp(lineSelected, code.size() - 1, 0);

		if (code.size() > 0) {
			String str = code.get(lineSelected);
			charSelected = clamp(charSelected, str.length(), 0);
		}
	}

	public static void print(Object obj) {

		TheEditor.printLn(obj);
		mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(obj+""));
	}

	public static String replace(String str, String arg, String replace) {
		if (str.contains(arg)) {
			return str.replace(arg, replace);
		}
		return str;
	}
}
