package net.plixo.paper.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;

public class Gui {

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

    public static void drawLinedRoundedRect(double left, double top, double right, double bottom, float radius, int color, float width) {

        if (radius <= 0.4f) {
            drawLinedRect(left, top, right, bottom, color, width);
            return;
        }

        double var5;

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

        circleLinedSection(left - (double) radius, top - (double) radius, radius, color, 0, 45, width);
        circleLinedSection(left - (double) radius, bottom + (double) radius, radius, color, 45, 90, width);
        circleLinedSection(right + (double) radius, top - (double) radius, radius, color, 135, 180, width);
        circleLinedSection(right + (double) radius, bottom + (double) radius, radius, color, 90, 135, width);

        drawLine(left - (double) radius, top, right + (double) radius, top, color, width);
        drawLine(left - (double) radius, bottom, right + (double) radius, bottom, color, width);
        drawLine(left, top - (double) radius, left, bottom + (double) radius, color, width);
        drawLine(right, top - (double) radius, right, bottom + (double) radius, color, width);
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
        glBegin(9);
        int i = 0;
        glVertex2d(x, y);
        double theta = 0;
        while (i <= 360) {
            theta = i * Math.PI / 90.0;

            glVertex2d((x + Math.sin(theta) * radius), (y + Math.cos(theta) * radius));
            i += 3;
        }
        glEnd();
        reset();
    }
    public static void drawOval(double x , double y , double width ,double height , int color ) {
        set(color);
        glBegin(9);
        int i = 0;
        double theta = 0;
        glVertex2d(x, y);
        while (i <= 360) {
            theta = i * Math.PI / 90.0;
            GL11.glVertex2d(x + (width/2 * Math.cos(theta)), (height/2 * Math.sin(theta)));
            i += 3;
        }
        glEnd();
        reset();
    }

    public static void drawRoundedRect(double left, double top, double right, double bottom, float radius, int color) {

        if (radius <= 0.4f) {
            drawRect(left, top, right, bottom, color);
            return;
        }

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

        drawCircle(left - (double) radius, top - (double) radius, radius, color, 0, 45);
        drawCircle(left - (double) radius, bottom + (double) radius, radius, color, 45, 90);
        drawCircle(right + (double) radius, top - (double) radius, radius, color, 135, 180);
        drawCircle(right + (double) radius, bottom + (double) radius, radius, color, 90, 135);
        drawRect(left - (double) radius, top, right + (double) radius, bottom, color);
        drawRect(left, top - (double) radius, left - (double) radius, bottom + (double) radius, color);
        drawRect(right + (double) radius, top - (double) radius, right, bottom + (double) radius, color);
    }

    public static void drawCircle(double x, double y, double radius, int color, int from, int to) {
        set(color);
        glBegin(9);
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

            i += 9;
            glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);

        }
        glEnd();
        reset();
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        Matrix4f mat = matrixStack.getLast().getMatrix();
        float startAlpha = (float)(startColor >> 24 & 255) / 255.0F;
        float startRed = (float)(startColor >> 16 & 255) / 255.0F;
        float startGreen = (float)(startColor >> 8 & 255) / 255.0F;
        float startBlue = (float)(startColor & 255) / 255.0F;
        float endAlpha = (float)(endColor >> 24 & 255) / 255.0F;
        float endRed = (float)(endColor >> 16 & 255) / 255.0F;
        float endGreen = (float)(endColor >> 8 & 255) / 255.0F;
        float endBlue = (float)(endColor & 255) / 255.0F;
        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(mat, (float)right, (float)top, (float)0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, (float)left, (float)top, (float)0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, (float)left, (float)bottom, (float)0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(mat, (float)right, (float)bottom, (float)0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    public static void drawSideGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        Matrix4f mat = matrixStack.getLast().getMatrix();
        float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
        float startRed = (float) (startColor >> 16 & 255) / 255.0F;
        float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
        float startBlue = (float) (startColor & 255) / 255.0F;
        float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
        float endRed = (float) (endColor >> 16 & 255) / 255.0F;
        float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
        float endBlue = (float) (endColor & 255) / 255.0F;
        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(mat, (float) right, (float) top, (float) 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(mat, (float) left, (float) top, (float) 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, (float) left, (float) bottom, (float) 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, (float) right, (float) bottom, (float) 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
    public static void set(int color) {
        glDisable(GL_TEXTURE_2D);
       glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);

        float alpha = (float) (color >> 24 & 255) / 255.0f;
        float red = (float) (color >> 16 & 255) / 255.0f;
        float green = (float) (color >> 8 & 255) / 255.0f;
        float blue = (float) (color & 255) / 255.0f;
        glColor4f(red, green, blue, alpha);
    }

    public static void reset() {
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

        float wDiff = x2 - x;
        float hDiff = y2 - y;
        double factor = mc.getMainWindow().getGuiScaleFactor();
        assert mc.currentScreen != null;
        float bottomY = mc.currentScreen.height - y2;
        glScissor((int) (x * factor), (int) (bottomY * factor), (int) (wDiff * factor), (int) (hDiff * factor));
    }


    public static FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

    public static void drawCenteredString(String text, double x, double y, int color) {
        fontRenderer.drawString(matrixStack, text, (float) x - getStringWidth(text) / 2f, (float) y - 4, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(matrixStack, text, (float) x - getStringWidth(text) / 2f, (float) y - 4, color);
    }

    public static void drawCenteredString(String text, double x, double y, int color, float width, float height) {
        fontRenderer.drawString(matrixStack, text, (float) x - getStringWidth(text) / 2f + width / 2, (float) y - 4 + height / 2, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color, float width, float height) {
        fontRenderer.drawStringWithShadow(matrixStack, text, (float) x - getStringWidth(text) / 2f + width / 2, (float) y - 4 + height / 2, color);
    }

    public static void drawString(String text, double x, double y, int color) {
        fontRenderer.drawString(matrixStack, text, (float) x, (float) y - 4, color);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) {
        fontRenderer.drawStringWithShadow(matrixStack, text, (float) x, (float) y - 4, color);
    }

    public static float getStringWidth(String text) {
        try {
            return fontRenderer.getStringWidth(text);
        } catch (Exception e) {
            return 0;
        }
    }
    public static void Bezier(int color, float width, Vector2f... list) {

        if (list.length < 2) {
            return;
        } else if (list.length == 2) {
            Gui.drawLine(list[0].x, list[0].y, list[1].x, list[1].y, color, width);
            return;
        }

        Vector2f first = list[0];
        Vector2f last = list[list.length - 1];


        // GL11.GL_LINES
        // GL11.GL_LINE_STRIP
        // GL11.GL_POINTS


        double ix = first.x - last.x;
        double iy = first.y - last.y;
        float precision = (float) (1 / Math.sqrt(iy * iy + ix * ix)) / 0.2f;
        precision = Math.min(0.07f, precision);

        GL11.glPushMatrix();
        GL11.glTranslated(first.x, first.y, 0);

        Gui.set(color);

        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);


        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2d(0, 0);
        float t = 0;
        while (t <= 1) {
            Vector2f pos = bezierCurvePixel(t, first, Arrays.copyOfRange(list, 1, list.length));
            GL11.glVertex2d(pos.x, pos.y);
            t += precision;
        }
        GL11.glVertex2d(last.x - first.x, last.y - first.y);
        GL11.glEnd();

        Gui.reset();
        GL11.glPopMatrix();
    }

    public static void Bezier(int color , int color2, float width, Vector2f... list) {

        if (list.length < 2) {
            return;
        } else if (list.length == 2) {
            Gui.drawLine(list[0].x, list[0].y, list[1].x, list[1].y, color, width);
            return;
        }

        Vector2f first = list[0];
        Vector2f last = list[list.length - 1];


        double ix = first.x - last.x;
        double iy = first.y - last.y;
        float precision = (float) (1 / Math.sqrt(iy * iy + ix * ix)) / 0.2f;
        precision = Math.min(0.07f, precision);

        GL11.glPushMatrix();
        GL11.glTranslated(first.x, first.y, 0);

        Gui.set(color);

        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);


        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2d(0, 0);
        float t = 0;
        while (t <= 1) {
            Vector2f pos = bezierCurvePixel(t, first, Arrays.copyOfRange(list, 1, list.length));

            color = ColorLib.interpolateColor(color , color2,t);
            float alpha = (float) (color >> 24 & 255) / 255.0f;
            float red = (float) (color >> 16 & 255) / 255.0f;
            float green = (float) (color >> 8 & 255) / 255.0f;
            float blue = (float) (color & 255) / 255.0f;
            glColor4f(red, green, blue, alpha);

            GL11.glVertex2d(pos.x, pos.y);
            t += precision;
        }
        float alpha = (float) (color2 >> 24 & 255) / 255.0f;
        float red = (float) (color2 >> 16 & 255) / 255.0f;
        float green = (float) (color2 >> 8 & 255) / 255.0f;
        float blue = (float) (color2 & 255) / 255.0f;
        glColor4f(red, green, blue, alpha);

        GL11.glVertex2d(last.x - first.x, last.y - first.y);
        GL11.glEnd();

        Gui.reset();
        GL11.glPopMatrix();
    }

    // Factorial
    private static float fact(float n) {
        float fact = 1;
        for (float i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    // Bernstein polynomial
    private static float bernstein(float t, float n, float i) {
        return (float) ((fact(n) / (fact(i) * fact(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t, i));
    }

    private static Vector2f bezierCurvePixel(float t, Vector2f origin, Vector2f... list) {

        float[] bPoly = new float[list.length];

        for (int i = 0; i < list.length; i++) {
            bPoly[i] = bernstein(t, list.length, i + 1);
        }

        float sumX = 0;
        float sumY = 0;

        for (int i = 0; i < list.length; i++) {
            sumX += bPoly[i] * (list[i].x - origin.x);
            sumY += bPoly[i] * (list[i].y - origin.y);
        }
        return new Vector2f(sumX,sumY);
    }
}

