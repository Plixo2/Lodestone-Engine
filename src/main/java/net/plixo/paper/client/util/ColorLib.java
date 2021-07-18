package net.plixo.paper.client.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class ColorLib {

    public static HashMap<Integer, String> colorNameList = new HashMap<>();
    static ArrayList<Integer> sortedList = new ArrayList<>();
   // ifYouSayYouCantLiveWithoutMeSoWhyArentYouDeadYetWhyYou'reStillBreathingIfYouSayYouCantLiveWithoutMeThanWhyArentYouDeadYetWhyDoYouSeeThat
    public static void load() {
        try {
            InputStream in = ColorLib.class.getResourceAsStream("/colors.json");
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
                        Util.print(e);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        sortedList.sort(Comparator.comparingInt(i -> i));
    }

    static String readStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buffer = new byte[512];

        int read;

        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, read);
        }

        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

    static int findBest(int target) {
        if (sortedList.isEmpty())
            return target;

        int iRed = ColorLib.getRed(target);
        int iGreen = ColorLib.getGreen(target);
        int iBlue = ColorLib.getBlue(target);

        int best = target;
        int lastDistance = Integer.MAX_VALUE;

        for (int i : sortedList) {
            int distance = Math.abs(ColorLib.getRed(i) - iRed) + Math.abs(ColorLib.getGreen(i) - iGreen) + Math.abs(ColorLib.getBlue(i) - iBlue);
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
        fraction *= (Options.options.contrast.value/100f);
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
        r = Util.clamp(r, 255, 0);
        g = Util.clamp(g, 255, 0);
        b = Util.clamp(b, 255, 0);
        return 255 << 24 |
                r << 16 |
                g << 8 |
                b;
    }

    public static int fromInts(int r, int g, int b,int a) {
        r = Util.clamp(r, 255, 0);
        g = Util.clamp(g, 255, 0);
        b = Util.clamp(b, 255, 0);
        a = Util.clamp(a, 255, 0);
        return a << 24 |
                r << 16 |
                g << 8 |
                b;
    }


}
