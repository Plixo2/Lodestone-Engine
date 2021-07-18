package net.plixo.paper.client.util.simple;

import net.plixo.paper.client.util.ColorLib;

public class SimpleColor {

    public int value = 0;

    public SimpleColor(int r, int g, int b) {
        value = ColorLib.fromInts(r, g, b);
    }

    public SimpleColor(int r, int g, int b, int a) {
        value = ColorLib.fromInts(r, g, b, a);
    }

    public SimpleColor(int value) {
        this.value = value;
    }

    public SimpleColor() {

    }

    public int getRed() {
        return ColorLib.getRed(value);
    }

    public int getGreen() {
        return ColorLib.getGreen(value);
    }

    public int getBlue() {
        return ColorLib.getBlue(value);
    }

    @Override
    public String toString() {
        return "SimpleColor{" +
                "value=" + value+
                "red=" + getRed() +
                "green=" + getGreen() +
                "blue=" + getBlue() +
        '}';
    }
}
