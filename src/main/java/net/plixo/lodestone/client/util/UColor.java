package net.plixo.lodestone.client.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.plixo.lodestone.client.util.serialiable.Options;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class UColor {

    public static HashMap<Integer, String> colorNameList = new HashMap<>();
    static ArrayList<Integer> sortedList = new ArrayList<>();
   // ifYouSayYouCantLiveWithoutMeSoWhyArentYouDeadYetWhyYoureStillBreathingIfYouSayYouCantLiveWithoutMeThanWhyArentYouDeadYetWhyDoYouSeeThat
    public static void load() {
        try {
            InputStream in = UColor.class.getResourceAsStream("/colors.json");
            JsonElement element = new JsonParser().parse(new InputStreamReader(in));
            if (element instanceof JsonArray) {
                JsonArray array = element.getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    try {
                    JsonObject object = array.get(i).getAsJsonObject();
                    int INT = Integer.parseInt(object.get("hex").getAsString().replace("#", "").replace(",", ""), 16);
                    sortedList.add(INT);
                    String name = object.get("name").getAsString().replace("-"," ");
                    colorNameList.put(INT, name);
                    } catch (Exception e) {
                        UMath.print(e);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            UMath.print(e);
            e.printStackTrace();
        }
        sortedList.sort(Comparator.comparingInt(i -> i));
    }

    static int findBest(int target) {
        if (sortedList.isEmpty())
            return target;

        int iRed = UColor.getRed(target);
        int iGreen = UColor.getGreen(target);
        int iBlue = UColor.getBlue(target);

        int best = target;
        int lastDistance = Integer.MAX_VALUE;

        for (int i : sortedList) {
            int distance = Math.abs(UColor.getRed(i) - iRed) + Math.abs(UColor.getGreen(i) - iGreen) + Math.abs(UColor.getBlue(i) - iBlue);
            if (distance < lastDistance) {
                lastDistance = distance;
                best = i;
            }
        }

        return best;
    }

    public static String getColorName(int color) {
        String a = colorNameList.get(color);
        if (a != null) {
            return a;
        }
        return colorNameList.getOrDefault(findBest(color), "?");
    }

    public static int getMainColor() {
        return Options.options.mainColor.value.value;
    }

    public static int getBackground(float fraction) {
        fraction += 0.2f;
        fraction *= (Options.options.contrast.value/10f);
        return interpolateColor(Options.options.backgroundColor.value.value, Options.options.backgroundFade.value.value, fraction * 0.3f);
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

    public static int green() {
        return 0xFF05A66B;
    }

    public static int getTextColor() {
        return Options.options.textColor.value.value;
    }


    public static int getDarker(int color1) {
        return interpolateColor(color1, Options.options.darkerFadeColor.value.value, Options.options.darkerFade.value);
    }

    public static int getBrighter(int color1) {
        return interpolateColor(color1, Options.options.brighterFadeColor.value.value, Options.options.brighterFade.value);
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

    public static int interpolateColorAlpha(int color1, int color2, float fraction) {

        int a1 = (color1 >> 24) & 0xff;
        int a2 = (color2 >> 24) & 0xff;
        int r1 = (color1 >> 16) & 0xff;
        int r2 = (color2 >> 16) & 0xff;
        int g1 = (color1 >> 8) & 0xff;
        int g2 = (color2 >> 8) & 0xff;
        int b1 = color1 & 0xff;
        int b2 = color2 & 0xff;

        return (int) ((a2 - a1) * fraction + a1) << 24 |
                (int) ((r2 - r1) * fraction + r1) << 16 |
                (int) ((g2 - g1) * fraction + g1) << 8 |
                (int) ((b2 - b1) * fraction + b1);
    }

    public static int setAlpha(int color1, int alpha) {

        int r1 = (color1 >> 16) & 0xff;
        int g1 = (color1 >> 8) & 0xff;
        int b1 = color1 & 0xff;

        return alpha << 24 |
                r1 << 16 |
                b1 << 8 |
                g1;
    }


    public static int getRed(int color) {
        return (color >> 16) & 0xff;
    }

    public static int getGreen(int color) {
        return (color >> 8) & 0xff;
    }

    public static int getBlue(int color) {
        return color & 0xff;
    }

    public static int fromInts(int r, int g, int b) {

        r = UMath.clamp(r, 255, 0);
        g = UMath.clamp(g, 255, 0);
        b = UMath.clamp(b, 255, 0);

        return 255 << 24 |
                r << 16 |
                g << 8 |
                b;
    }

    public static int fromInts(int r, int g, int b,int a) {

        r = UMath.clamp(r, 255, 0);
        g = UMath.clamp(g, 255, 0);
        b = UMath.clamp(b, 255, 0);
        a = UMath.clamp(a, 255, 0);

        return a << 24 |
                r << 16 |
                g << 8 |
                b;
    }


}
