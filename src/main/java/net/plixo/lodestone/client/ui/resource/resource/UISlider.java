package net.plixo.lodestone.client.ui.resource.resource;

import net.plixo.animation.Animation;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.resource.util.SimpleSlider;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.UMath;

public class UISlider extends UIElement {

    SimpleSlider simpleSlider;

    public void setSimpleSlider(SimpleSlider simpleSlider) {
        this.simpleSlider = simpleSlider;
        percent = (simpleSlider.getValue() - simpleSlider.min) / (simpleSlider.max- simpleSlider.min);
    }
    float percent = 0;
    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault(UColor.getBackground(0.3f));
        float sliderLeft = x + 5;
        float sliderWidth = width - 10;
        float y = this.y + height * 0.66f;
        UGui.drawRect(sliderLeft, y - 1, sliderLeft + sliderWidth, y, UColor.getBrighter(getColor()));
        float c = (simpleSlider.getValue() - simpleSlider.min) / (simpleSlider.max- simpleSlider.min);
        final float percent2 = percent;
        Animation.animate(f -> this.percent = f,() -> percent2,c,0.25f);

        UGui.drawCircle(sliderLeft + sliderWidth * this.percent, y-0.5f, 3, UColor.getMainColor());
        double round = Math.round(simpleSlider.getValue()*100)/100d;
        UGui.drawString(""+round,sliderLeft,this.y + 7,UColor.getTextColor());
        if (dragged) {
            float mousePercent = UMath.clampFloat((mouseX - sliderLeft) / sliderWidth, 1, 0);
            simpleSlider.setValue(simpleSlider.min + (simpleSlider.max - simpleSlider.min) * mousePercent);
        }

        super.drawScreen(mouseX, mouseY);
    }

    boolean dragged = false;

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 0) {
            dragged = true;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(float mouseX, float mouseY, int state) {
        dragged = false;
    }
}
