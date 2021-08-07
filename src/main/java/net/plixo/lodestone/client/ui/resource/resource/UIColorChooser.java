package net.plixo.lodestone.client.ui.resource.resource;

import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.util.UColor;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UIColorChooser extends UICanvas {

    int customColor = 0;
    String colorName = "";
    UIButton button;

    public UIColorChooser() {
        setColor(0);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault( UColor.getBackground(0.3f));
        drawHoverEffect();

       // float strWidth = UGui.getStringWidth(colorName);
       // UGui.drawRect(x + strWidth + 6, y + 1, x + strWidth + 4 + height, y + height - 1, customColor);

        UGui.drawStringWithShadow(colorName, x + 4, y + height / 2, UColor.getTextColor());


        super.drawScreen(mouseX, mouseY);
    }

    public int getCustomColor() {
        return customColor;
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        button = new UIButton();
        button.setAction(() -> {

            JFrame jFrame = new JFrame("choose wisely...");
            jFrame.setSize(750, 500);
            JColorChooser colorChooser = new JColorChooser();
            colorChooser.setColor(customColor);
            colorChooser.setPreviewPanel(new JPanel());

            colorChooser.setChooserPanels(new AbstractColorChooserPanel[]{ColorChooserComponentFactory.getDefaultChooserPanels()[1]});

            jFrame.getContentPane().add(colorChooser);
            jFrame.setVisible(true);

            jFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    setCustomColor(colorChooser.getColor().getRGB());
                }
            });
        });
        button.setDisplayName(">");
        button.setRoundness(2);
        button.setTickAction(() -> button.setColor(customColor));
        button.setColor(UColor.getMainColor());
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }

    void updateColorName() {
        colorName = UColor.getColorName(customColor);
    }

    public void setCustomColor(int color) {
        this.customColor = color;
        updateColorName();
    }

}
