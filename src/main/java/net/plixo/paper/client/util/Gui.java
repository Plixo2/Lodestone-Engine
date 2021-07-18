package net.plixo.paper.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
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

    public static int circle = 0;
    public static int rect = 0;
    public static int aText = 0;
    public static int line = 0;
    public static int bezierPixel = 0;
    public static int roundRects = 0;

    public static void setMatrix(MatrixStack matrix) {
        matrixStack = matrix;
    }

    public static void drawLinedRect(float left, float top, float right, float bottom, int color, float width) {

        rect += 1;
        mc.getProfiler().startSection("Lined Rect");
        float temp;

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

        mc.getProfiler().endSection();
    }

    public static void drawLinedRoundedRect(float left, float top, float right, float bottom, float radius, int color, float width) {
        rect += 1;
        mc.getProfiler().startSection("Lined Rounded Rect");
        radius *= Options.options.roundness.value;
        if (radius <= 1f || !Options.options.useRoundedRects.value) {
            drawLinedRect(left, top, right, bottom, color, width);
            mc.getProfiler().endSection();
            return;
        }

        float var5;

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
        radius = Math.min((left-right)/2, Math.min((top-bottom)/2,radius));

        circleLinedSection(left - radius, top - radius, radius, color, 0, 45, width);
        circleLinedSection(left - radius, bottom + radius, radius, color, 45, 90, width);
        circleLinedSection(right + radius, top - radius, radius, color, 135, 180, width);
        circleLinedSection(right + radius, bottom + radius, radius, color, 90, 135, width);

        drawLine(left - radius, top, right + radius, top, color, width);
        drawLine(left - radius, bottom, right + radius, bottom, color, width);
        drawLine(left, top - radius, left, bottom + radius, color, width);
        drawLine(right, top - radius, right, bottom + radius, color, width);

        mc.getProfiler().endSection();
    }

    public static void drawLine(float left, float top, float right, float bottom, int color, float width) {
        line += 1;
        mc.getProfiler().startSection("Line");
        set(color);

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(width);

        glBegin(GL_LINES);
        glVertex2d(left, top);
        glVertex2d(right, bottom);
        glEnd();

        reset();
        mc.getProfiler().endSection();
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        rect += 1;
        mc.getProfiler().startSection("Rect");

        float temp;

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

        mc.getProfiler().endSection();
    }

    public static void drawCircle(float x, float y, float radius, int color) {
        circle += 1;
        mc.getProfiler().startSection("Circle");
        set(color);
        glBegin(9);
        float s = (float) (Math.PI / 90.0);
        int i = 0;
        glVertex2d(x, y);
        float theta;
        while (i <= 360) {
            theta = i * s;

            glVertex2d((x + Math.sin(theta) * radius), (y + Math.cos(theta) * radius));
            i += 9;
        }
        glEnd();
        reset();
        mc.getProfiler().endSection();
    }

    public static void drawOval(float x, float y, float width, float height, int color) {
        circle += 1;
        set(color);
        glBegin(9);
        int i = 0;
        double theta = 0;
        glVertex2d(x, y);
        while (i <= 360) {
            theta = i * Math.PI / 90.0;
            GL11.glVertex2d(x + (width / 2 * Math.cos(theta)), (height / 2 * Math.sin(theta)));
            i += 3;
        }
        glEnd();
        reset();
    }

    public static void drawRoundedRect(float left, float top, float right, float bottom, float radius, int color) {
        roundRects += 1;
        rect += 1;
        mc.getProfiler().startSection("Rounded Rect");
        radius *= Options.options.roundness.value;
        if (radius <= 1f || !Options.options.useRoundedRects.value) {
            drawRect(left, top, right, bottom, color);
            mc.getProfiler().endSection();
            return;
        }

        float temp;

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

        radius = Math.min((left-right)/2, Math.min((top-bottom)/2,radius));

        set(color);

        drawCircleFast(left - radius, top - radius, radius, 0, 45);
        drawCircleFast(left - radius, bottom + radius, radius, 45, 90);
        drawCircleFast(right + radius, top - radius, radius, 135, 180);
        drawCircleFast(right + radius, bottom + radius, radius, 90, 135);

        drawRectFast(left - radius, top, right + radius, bottom);
        drawRectFast(left, top - radius, left - radius, bottom + radius);
        drawRectFast(right + radius, top - radius, right, bottom + radius);

        reset();
        mc.getProfiler().endSection();
    }

    public static void drawRectFast(float left, float top, float right, float bottom) {
        rect += 1;
        mc.getProfiler().startSection("Fast Rect");

        glBegin(GL_QUADS);
        glVertex2d(left, bottom);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glVertex2d(left, top);
        glEnd();

        mc.getProfiler().endSection();
    }

    public static void drawCircleFast(float x, float y, float radius, int from, int to) {
        circle += 1;
        mc.getProfiler().startSection("Fast Circle");
        float s = (float) (Math.PI / 90.0f);
        float yaw = 0;
        glBegin(9);
        glVertex2d(x, y);
        while (from <= to) {
            yaw = from * s;
            glVertex2d((x + Math.sin(yaw) * radius), (y + Math.cos(yaw) * radius));
            from += 9;
        }
        glEnd();

        mc.getProfiler().endSection();
    }

    public static void drawCircle(float x, float y, float radius, int color, int from, int to) {
        circle += 1;
        mc.getProfiler().startSection("Circle");
        set(color);
        glBegin(9);
        int i = from;
        glVertex2d(x, y);
        while (i <= to) {
            glVertex2d((x + Math.sin((float) i * Math.PI / 90.0) * radius), (y + Math.cos(i * Math.PI / 90.0) * radius));
            i += 9;
        }
        glEnd();
        reset();
        mc.getProfiler().endSection();
    }

    private static void circleLinedSection(float x, float y, float radius, int color, int from, int to, float width) {
        circle += 1;
        mc.getProfiler().startSection("Circle");
        set(color);

        glEnable(GL_LINE_SMOOTH);
        glLineWidth(width);

        glBegin(GL_LINES);
        int i = from;
        float toRadiant = (float) (Math.PI / 90);
        while (i <= to) {
            glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);


            i += 9;
            glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);

        }
        glEnd();
        reset();
        mc.getProfiler().endSection();
    }

    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        rect += 1;
        mc.getProfiler().startSection("Gradient");
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
        buffer.pos(mat, right, top, (float) 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, left, top, (float) 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, left, bottom, (float) 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(mat, right, bottom, (float) 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        mc.getProfiler().endSection();
    }

    public static void drawSideGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        rect += 1;
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
        buffer.pos(mat, right, top, (float) 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(mat, left, top, (float) 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, left, bottom, (float) 0).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(mat, right, bottom, (float) 0).color(endRed, endGreen, endBlue, endAlpha).endVertex();
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

        setColor(color);
    }

    public static void setColor(int color) {
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
        float factor = (float) mc.getMainWindow().getGuiScaleFactor();
        assert mc.currentScreen != null;
        float bottomY = mc.currentScreen.height - y2;
        glScissor(Math.round(x * factor), Math.round(bottomY * factor), Math.round(wDiff * factor), Math.round(hDiff * factor));
    }
    public static float[] getModelViewMatrix() {
        float[] mat = new float[16];
        GL11.glGetFloatv(GL11.GL_MODELVIEW_MATRIX, mat);
        return mat;
    }

    public static Vector2f toScreenSpace(float[] mat,float x,float y) {
            float inX = x;
            float inY = y;
            float inZ = 0;
            float nX  = mat[0] * inX + mat[4] * inY + mat[8] * inZ + mat[12];
            float nY = mat[1] * inX + mat[5] * inY + mat[9] * inZ + mat[13];
            return new Vector2f(nX,nY);
    }


    public static FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

    public static void drawCenteredString(String text, float x, float y, int color) {
        mc.getProfiler().startSection("Text");
        aText += 1;
        fontRenderer.drawString(matrixStack, text, x - getStringWidth(text) / 2f, y - 4, color);
        mc.getProfiler().endSection();
    }

    public static void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        aText += 1;
        mc.getProfiler().startSection("Text");
        fontRenderer.drawStringWithShadow(matrixStack, text, x - getStringWidth(text) / 2f, y - 4, color);
        mc.getProfiler().endSection();
    }

    public static void drawCenteredString(String text, float x, float y, int color, float width, float height) {
        aText += 1;
        mc.getProfiler().startSection("Text");
        fontRenderer.drawString(matrixStack, text, x - getStringWidth(text) / 2f + width / 2, y - 4 + height / 2, color);
        mc.getProfiler().endSection();
    }

    public static void drawCenteredStringWithShadow(String text, float x, float y, int color, float width, float height) {
        aText += 1;
        mc.getProfiler().startSection("Text");
        fontRenderer.drawStringWithShadow(matrixStack, text, x - getStringWidth(text) / 2f + width / 2, y - 4 + height / 2, color);
        mc.getProfiler().endSection();
    }

    public static void drawString(String text, float x, float y, int color) {
        aText += 1;
        mc.getProfiler().startSection("Text");
        fontRenderer.drawString(matrixStack, text, x, y - 4, color);
        mc.getProfiler().endSection();
    }

    public static void drawStringWithShadow(String text, float x, float y, int color) {
        aText += 1;
        mc.getProfiler().startSection("Text");
        fontRenderer.drawStringWithShadow(matrixStack, text, x, y - 4, color);
        mc.getProfiler().endSection();
    }

    public static float getStringWidth(String text) {
        try {
            return fontRenderer.getStringWidth(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void Bezier(int color, float width, Vector2f... list) {
        line += 1;
        mc.getProfiler().startSection("Bezier");
        if (list.length < 2) {
            mc.getProfiler().endSection();
            return;
        } else if (list.length == 2) {
            Gui.drawLine(list[0].x, list[0].y, list[1].x, list[1].y, color, width);
            mc.getProfiler().endSection();
            return;
        }

        Vector2f first = list[0];
        Vector2f last = list[list.length - 1];


        // GL11.GL_LINES
        // GL11.GL_LINE_STRIP
        // GL11.GL_POINTS


        float ix = first.x - last.x;
        float iy = first.y - last.y;
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
        mc.getProfiler().endSection();
    }

    public static void Bezier(int color, int color2, float width, Vector2f... list) {
        line += 1;
        mc.getProfiler().startSection("Bezier");
        if (list.length < 2) {
            mc.getProfiler().endSection();
            return;
        } else if (list.length == 2) {
            Gui.drawLine(list[0].x, list[0].y, list[1].x, list[1].y, color, width);
            mc.getProfiler().endSection();
            return;
        }

        Vector2f first = list[0];
        Vector2f last = list[list.length - 1];


        float ix = first.x - last.x;
        float iy = first.y - last.y;
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

            color = ColorLib.interpolateColor(color, color2, t);
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

        mc.getProfiler().endSection();
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

        bezierPixel += 1;
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
        return new Vector2f(sumX, sumY);
    }
}

