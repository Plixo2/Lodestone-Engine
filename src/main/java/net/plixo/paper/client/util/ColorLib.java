package net.plixo.paper.client.util;

@SuppressWarnings("unused")
public class ColorLib {

    public static int getMainColor() {
        return 0xFF2f98f5;
    }

    public static int getBackground(float fraction) {
        return interpolateColor(0xFF202225, 0xFFFFFFFF, fraction * 0.3f);
    }


    public static int cyan() {
        return 0xFF1BBFAF;
    }

    public static int blue() {
        return 0xFF1E2D59;
    }

    public static int red() {
        return 0xFFF21D44;
    }

    public static int yellow() {
        return 0xFFF2A516;
    }

    public static int orange() {
        return 0xFFF25D27;
    }


    public static int utilLines() {
        return 0x6F000000;
    }

    public static int getVisualScriptMainColor() {
        return 0xFF202020;
    }


    public static int getDarker(int color1) {
        return interpolateColor(color1, 0xFF000000, 0.3f);
    }

    public static int getBrighter(int color1) {
        return interpolateColor(color1, 0xFFFFFFFF, 0.3f);
    }

    public static int interpolateColor(int color1, int color2, float fraction) {

        int a1 = (color1 >> 24) & 0xff;
        int a2 = (color2 >> 24) & 0xff;
        int r1 = (color1 >> 16) & 0xff;
        int r2 = (color2 >> 16) & 0xff;
        int g1 = (color1 >> 8) & 0xff;
        int g2 = (color2 >> 8) & 0xff;
        int b1 = color1 & 0xff;
        int b2 = color2 & 0xff;

        return a1 << 24 |
                (int) ((r2 - r1) * fraction + r1) << 16 |
                (int) ((g2 - g1) * fraction + g1) << 8 |
                (int) ((b2 - b1) * fraction + b1);
    }


}
