package net.plixo.paper.client.ui.other;

import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;

//TODO extract Functions...
@Deprecated
public class RadialMenu {

    public String name;

    String[] options;
    float radiusMin, radiusMax;

    public boolean shown = false;

    float x, y;

    public RadialMenu(String name, float radiusMin, float radiusMax, String... options) {
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
        this.options = options;
        this.name = name;
    }

    public void draw(float mouseX, float mouseY) {
        if (shown) {
            GL11.glLineWidth(2);

            float toX = mouseX - x;
            float toY = mouseY - y;
            boolean outsideRadius = (toX * toX + toY * toY) > radiusMin * radiusMin;

            int inColor = ColorLib.getMainColor();

            if (outsideRadius) {
                inColor = Integer.MIN_VALUE;
            }

            Gui.drawCircle(x, y, radiusMin - 3, inColor);

            Gui.drawCenteredStringWithShadow(name, x, y, -1);

            int sections = options.length;

            int degreePerSection = 360 / sections;
            int index = 0;

            for (int i = 0; i < 360; i += degreePerSection) {

                double target = Math.toDegrees(Math.atan2(toX, toY));
                if (target < 0) {
                    target += 360;
                }

                int color = Integer.MIN_VALUE;
                boolean hovered = false;

                if (target > i && target < i + degreePerSection && outsideRadius) {
                    hovered = true;
                    color = ColorLib.getMainColor();
                }

                double radiantCenter = Math.toRadians(i + degreePerSection / 2.f);
                double centerX = x + Math.sin(radiantCenter) * (radiusMin + radiusMax) / 2;
                double centerY = y + Math.cos(radiantCenter) * (radiusMin + radiusMax) / 2;

                double RadiantRight = Math.toRadians(i + degreePerSection);
                double RightXMin = x + Math.sin(RadiantRight) * radiusMin;
                double RightYMin = y + Math.cos(RadiantRight) * radiusMin;

                double RightXMax = x + Math.sin(RadiantRight) * radiusMax;
                double RightYMax = y + Math.cos(RadiantRight) * radiusMax;

                drawCircle(x, y, color, i, i + degreePerSection, radiusMin, radiusMax);
                Gui.drawLine(RightXMin, RightYMin, RightXMax, RightYMax, -1, 2);

                // Gui.drawCircle(centerX, centerY, 10, -1);

                if (hovered) {
                    Gui.drawCenteredStringWithShadow(options[index], centerX, centerY - 1, -1);
                } else {
                    Gui.drawCenteredString(options[index], centerX, centerY, -1);
                }

                index += 1;
            }
            GL11.glLineWidth(2);
            drawCircle(x, y, radiusMin, -1);
            drawCircle(x, y, radiusMax, -1);
        }

    }

    void drawCircle(double x, double y, double radius, int color) {

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        float alpha = (float) (color >> 24 & 255) / 255.0f;
        float red = (float) (color >> 16 & 255) / 255.0f;
        float green = (float) (color >> 8 & 255) / 255.0f;
        float blue = (float) (color & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        int i = 0;
        int sections = 90;
        double toRadiant = (Math.PI / (sections / 2.f));
        while (i <= sections) {

            GL11.glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);
            ++i;

            GL11.glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);
        }
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    void drawCircle(double x, double y, int color, int from, int to, float innerRadius, float outerRadius) {

        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        float alpha = (float) (color >> 24 & 255) / 255.0f;
        float red = (float) (color >> 16 & 255) / 255.0f;
        float green = (float) (color >> 8 & 255) / 255.0f;
        float blue = (float) (color & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(GL11.GL_QUADS);
        int i = from;
        double toRadiant = (Math.PI / 180);
        while (i <= to) {
            GL11.glVertex2d(x + Math.sin(i * toRadiant) * innerRadius, y + Math.cos(i * toRadiant) * innerRadius);
            GL11.glVertex2d(x + Math.sin(i * toRadiant) * outerRadius, y + Math.cos(i * toRadiant) * outerRadius);

            ++i;
            if (i <= to) {
                GL11.glVertex2d(x + Math.sin(i * toRadiant) * outerRadius, y + Math.cos(i * toRadiant) * outerRadius);
                GL11.glVertex2d(x + Math.sin(i * toRadiant) * innerRadius, y + Math.cos(i * toRadiant) * innerRadius);
            }
        }
        GL11.glEnd();
        GL11.glColor4f(1, 1, 1, 1);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public int getSelectedSection(float mouseX, float mouseY) {

        int sections = options.length;
        int degreePerSection = 360 / sections;
        int index = 0;

        for (int i = 0; i < 360; i += degreePerSection) {

            float toX = mouseX - x;
            float toY = mouseY - y;

            double target = Math.toDegrees(Math.atan2(toX, toY));
            if (target < 0) {
                target += 360;
            }

            if (target > i && target < i + degreePerSection && (toX * toX + toY * toY) > radiusMin * radiusMin) {
                return index;
            }

            index += 1;
        }

        return -1;
    }

    public void hide() {
        shown = false;
    }

    public int mouseClicked(float mouseX, float mouseY, int mouseButton) {

        if (mouseButton == 2) {
            show(mouseX, mouseY);
        } else {

            if (shown) {
                hide();
                return getSelectedSection(mouseX, mouseY);
            }
        }
        return -1;
    }

    public void show(float mouseX, float mouseY) {
        shown = true;
        this.x = mouseX;
        this.y = mouseY;
    }
}
