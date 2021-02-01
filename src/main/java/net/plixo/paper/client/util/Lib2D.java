package net.plixo.paper.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Lib2D {

    public static Minecraft mc = Minecraft.getInstance();
    public static MatrixStack matrixStack;

    public static void setMatrix(MatrixStack matrix) {
        matrixStack = matrix;
    }

    public static void drawLinedRect(double left, double top, double right, double bottom, int color, float width) {

        double temp;

        if (left < right) {
            temp = left;
            left = right;
            right = temp;
        }

        if (top < bottom) {
            temp = top;
            top = bottom;
            bottom = temp;
        }

        float wHalf = width / 4;

        drawLine(left + wHalf, top, right - wHalf, top, color, width);
        drawLine(left + wHalf, bottom, right - wHalf, bottom, color, width);


        drawLine(left, top + wHalf, left, bottom - wHalf, color, width);
        drawLine(right, top + wHalf, right, bottom - wHalf, color, width);

    }

    public static void drawLinedRoundetRect(double left, double top, double right, double bottom, float radius, int color, float width) {

        if (radius <= 0.4f) {
            drawLinedRect(left, top, right, bottom, color, width);
            return;
        }

        double var5;
        double half = radius;

        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }

        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }

        circleLinedSection(left - half, top - half, radius, color, 0, 45, width);
        circleLinedSection(left - half, bottom + half, radius, color, 45, 90, width);
        circleLinedSection(right + half, top - half, radius, color, 135, 180, width);
        circleLinedSection(right + half, bottom + half, radius, color, 90, 135, width);

        drawLine(left - half, top, right + half, top, color, width);
        drawLine(left - half, bottom, right + half, bottom, color, width);
        drawLine(left, top - half, left, bottom + half, color, width);
        drawLine(right, top - half, right, bottom + half, color, width);
    }

    public static void drawLine(double left, double top, double right, double bottom, int color, float width) {

        set(color);

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(width);

        glBegin(GL_LINES);
        glVertex2d(left, top);
        glVertex2d(right, bottom);
        glEnd();

        reset();

    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {


        double temp;

        if (left < right) {
            temp = left;
            left = right;
            right = temp;
        }

        if (top < bottom) {
            temp = top;
            top = bottom;
            bottom = temp;
        }

        set(color);
        glBegin(GL_QUADS);

        glVertex2d(left, bottom);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glVertex2d(left, top);

        glEnd();
        reset();
    }

    public static void drawCircle(double x, double y, double radius, int color) {
        set(color);
        glBegin((int) 9);
        int i = 0;
        glVertex2d(x, y);
        while (i <= 360) {
            glVertex2d((x + Math.sin((double) i * Math.PI / 90.0) * radius), (y + Math.cos(i * Math.PI / 90.0) * radius));
            i += 3;
        }
        glEnd();
        reset();
    }

    public static void drawRoundetRect(double left, double top, double right, double bottom, float radius, int color) {

        if (radius <= 0.4f) {
            drawRect(left, top, right, bottom, color);
            return;
        }

        double temp;
        double half = radius;

        if (left < right) {
            temp = left;
            left = right;
            right = temp;
        }

        if (top < bottom) {
            temp = top;
            top = bottom;
            bottom = temp;
        }

        drawCircle(left - half, top - half, radius, color, 0, 45);
        drawCircle(left - half, bottom + half, radius, color, 45, 90);
        drawCircle(right + half, top - half, radius, color, 135, 180);
        drawCircle(right + half, bottom + half, radius, color, 90, 135);
        drawRect(left - half, top, right + half, bottom, color);
        drawRect(left, top - half, left - half, bottom + half, color);
        drawRect(right + half, top - half, right, bottom + half, color);
    }

    public static void drawCircle(double x, double y, double radius, int color, int from, int to) {
        set(color);
        glBegin((int) 9);
        int i = from;
        glVertex2d(x, y);
        while (i <= to) {
            glVertex2d((x + Math.sin((double) i * Math.PI / 90.0) * radius), (y + Math.cos(i * Math.PI / 90.0) * radius));
            i += 3;
        }
        glEnd();
        reset();
    }

    private static void circleLinedSection(double x, double y, float radius, int color, int from, int to, float width) {

        set(color);

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(width);

        glBegin(GL_LINES);
        int i = from;
        double toRadiant = (Math.PI / 90);
        while (i <= to) {
            glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);

            i+= 9;
            glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);

        }
        glEnd();
        reset();
    }

    private static void set(int color) {
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);

        float alpha = (float) (color >> 24 & 255) / 255.0f;
        float red = (float) (color >> 16 & 255) / 255.0f;
        float green = (float) (color >> 8 & 255) / 255.0f;
        float blue = (float) (color & 255) / 255.0f;
        glColor4f((float) red, (float) green, (float) blue, (float) alpha);
    }

    private static void reset() {
        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glColor4f(1, 1, 1, 1);
    }

    public static void activateScissor() {
        glEnable(GL_SCISSOR_TEST);
    }
    public static void deactivateScissor() {
        glDisable(GL_SCISSOR_TEST);
    }
    public static void createScissorBox(float x, float y, float x2, float y2) {

        float wdiff = x2 - x;
        float hdiff = y2 - y;
        double factor = mc.getMainWindow().getGuiScaleFactor();
        float bottomY = mc.currentScreen.height - y2;
        glScissor((int) (x * factor), (int) (bottomY * factor), (int) (wdiff * factor), (int) (hdiff * factor));
    }


    public static FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

    public static void drawCenteredString(String text, double x, double y, int color) {
        fontRenderer.drawString(matrixStack, text, (float) x - getStringWidth(text) / 2f, (float) y - 4, color);
    }
    public static void drawCenteredStringwithShadow(String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(matrixStack, text, (float) x - getStringWidth(text) / 2f, (float) y - 4, color);
    }
    public static void drawCenteredString(String text, double x, double y, int color, float width, float height) {
        fontRenderer.drawString(matrixStack, text, (float) x - getStringWidth(text) / 2f + width / 2, (float) y - 4 + height / 2, color);
    }
    public static void drawCenteredStringwithShadow(String text, double x, double y, int color, float width, float height) {
        fontRenderer.drawStringWithShadow(matrixStack, text, (float) x - getStringWidth(text) / 2f + width / 2, (float) y - 4 + height / 2, color);
    }
    public static void drawString(String text, double x, double y, int color) {
        fontRenderer.drawString(matrixStack, text, (float) x, (float) y - 4, color);
    }
    public static void drawStringwithShadow(String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(matrixStack, text, (float) x, (float) y - 4, color);
    }
    public static float getStringWidth(String text) {
        try {
            return fontRenderer.getStringWidth(text);
        } catch (Exception e) {
            return 0;
        }
    }

}
