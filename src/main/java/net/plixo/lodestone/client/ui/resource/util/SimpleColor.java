package net.plixo.lodestone.client.ui.resource.util;

import net.plixo.lodestone.client.util.UColor;

public class SimpleColor {

    public int value = 0;

    public SimpleColor(int r, int g, int b) {
        value = UColor.fromInts(r, g, b);
    }

    public SimpleColor(int r, int g, int b, int a) {
        value = UColor.fromInts(r, g, b, a);
    }

    public SimpleColor(int value) {
        this.value = value;
    }

    public SimpleColor() {
    }

    public int getRed() {
        return UColor.getRed(value);
    }

    public int getGreen() {
        return UColor.getGreen(value);
    }

    public int getBlue() {
        return UColor.getBlue(value);
    }

    @Override
    public String toString() {
        return "SimpleColor{" +
                "value=" + value +
                ", red=" + getRed() +
                ", green=" + getGreen() +
                ", blue=" + getBlue() +
                '}';
    }
}
