package net.plixo.lodestone.client.ui.resource.util;

import net.plixo.lodestone.client.util.UMath;

public class SimpleSlider {
    public float value = 0;
    public transient float min , max;

    public SimpleSlider() {

    }

    public SimpleSlider(float value , float min,float max) {
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public void setValue(float value) {
        this.value = UMath.clampFloat(value,max,min);
    }

    public float getValue() {
        value = UMath.clampFloat(value,max,min);
        return value;
    }
}
