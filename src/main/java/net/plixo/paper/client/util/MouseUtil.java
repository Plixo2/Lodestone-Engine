package net.plixo.paper.client.util;

public class MouseUtil {

    static float dWheel = 0;

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
