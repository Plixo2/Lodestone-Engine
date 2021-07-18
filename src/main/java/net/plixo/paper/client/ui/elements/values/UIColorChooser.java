package net.plixo.paper.client.ui.elements.values;

import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

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

        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, ColorLib.getBackground(0.3f));
        int color = ColorLib.interpolateColorAlpha(0x00000000, 0x23000000, hoverProgress / 100f);
        Gui.drawRoundedRect(x, y, x + width, y + height, roundness, color);

        float strWidth = Gui.getStringWidth(colorName);
        Gui.drawRect(x + strWidth + 6, y + 1, x + strWidth + 4 + height, y + height - 1, customColor);

        Gui.drawStringWithShadow(colorName, x + 4, y + height / 2, -1);


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
            /*
            GUICanvas canvas = new GUICanvas();
            mc.displayGuiScreen(canvas);

            UICircle circle = new UICircle();
            circle.setDimensions(canvas.canvas.width / 2 - 30, canvas.canvas.height / 2 - 30, 60, 60);
            circle.setColor(-1);
            circle.setAction(() -> {
                mc.displayGuiScreen(screen);
                updateColorName();
            });
            circle.setDisplayName("OK");

            float radius = 100;
            UIContext drawContext = new UIContext();
            drawContext.setDimensions(canvas.canvas.width / 2, canvas.canvas.height / 2, 10, 10);
            drawContext.setColor(0xFFFF0000);
            drawContext.setDrawContext((mouseX, mouseY) -> {

                Gui.set(color);
                glLineWidth(10);
                glBegin(GL_LINE_STRIP);
                int i = 0;
                double theta;
                int c;
                while (i <= 180) {
                    theta = i * Math.PI / 90.0;
                    glVertex2d((drawContext.x + Math.sin(theta) * radius), (drawContext.y + Math.cos(theta) * radius));
                    c = Color.HSBtoRGB(i / 180f, 1, 1);
                    glColor4f(ColorLib.getRed(c) / 255f, ColorLib.getGreen(c) / 255f, ColorLib.getBlue(c) / 255f,1);
                    i += 3;
                }
                glEnd();
                Gui.reset();

                if (MouseUtil.isKeyDown(0)) {
                    float midX = mouseX - canvas.canvas.width / 2;
                    float midY = mouseY - canvas.canvas.height / 2;
                    float yaw = (float) Math.toDegrees(Math.atan2(midX, -midY)) + 180;
                    int hsb = Color.HSBtoRGB(yaw / 360f, 1, 1);
                    customColor = hsb;
                    circle.setColor(customColor);
                }
            });

            canvas.add(drawContext);
            canvas.add(circle);

             */


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
        button.displayName = ">";
        button.setRoundness(2);
        button.setColor(ColorLib.getMainColor());
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }

    void updateColorName() {
        colorName = ColorLib.getColorName(customColor);
    }

    public void setCustomColor(int color) {
        this.customColor = color;
        updateColorName();
    }

}
