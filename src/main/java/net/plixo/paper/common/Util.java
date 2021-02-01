package net.plixo.paper.common;

public class Util {
    public static int clamp(int value, int max, int min) {
        return Math.max(min, Math.min(value, max));
    }

    public static float clampFloat(double value, double max, double min) {
        return (float) Math.max(min, Math.min(value, max));
    }
}
