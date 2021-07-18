package net.plixo.paper.client.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

/**
 * Mouse Util class or using wheel scrolling inside the UI
 */
public class MouseUtil {

    static float dWheel = 0;

    public static boolean isKeyDown(int key) {
        return GLFW.glfwGetMouseButton(Minecraft.getInstance().getMainWindow().getHandle(), key) == GLFW.GLFW_PRESS;
    }

    public static void addDWheel(float delta) {
        dWheel += delta;
    }

    public static float getDWheel() {
        return dWheel;
    }

    public static void resetWheel() {
        dWheel = 0;
    }

}
