package net.plixo.lodestone.client.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import net.plixo.lodestone.client.ui.font.GlyphToMinecraft;
import net.plixo.lodestone.client.util.UColor;
import net.plixo.lodestone.client.util.serialiable.Options;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDisable;

public class UGui {

    public static Minecraft mc = Minecraft.getInstance();
    static MatrixStack matrixStack;

    public static FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
    static float yOffset = 4;


    public static void setDefaultFontRenderer() {
        fontRenderer = Minecraft.getInstance().fontRenderer;
        yOffset = 4;
    }

    public static void setGlyphFontRenderer(String font) {
        fontRenderer = new GlyphToMinecraft(font);
        yOffset = 6;
    }

    public static void setMatrix(MatrixStack matrix) {
        matrixStack = matrix;
    }
    public static MatrixStack getMatrixStack() {
        return matrixStack;
    }


    public static void drawLinedRect(float left, float top, float right, float bottom, int color, float width) {

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
        mc.getProfiler().startSection("Rect");
        glDisable(GL_POLYGON_SMOOTH);
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

        mc.getProfiler().startSection("Circle");
        set(color);
        glEnable(GL_POLYGON_SMOOTH);
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
        glDisable(GL_POLYGON_SMOOTH);
    }

    public static void drawOval(float x, float y, float width, float height, int color) {
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
        mc.getProfiler().startSection("Fast Rect");
        glDisable(GL_POLYGON_SMOOTH);
        glBegin(GL_QUADS);
        glVertex2d(left, bottom);
        glVertex2d(right, bottom);
        glVertex2d(right, top);
        glVertex2d(left, top);
        glEnd();
        mc.getProfiler().endSection();
    }

    public static void drawCircleFast(float x, float y, float radius, int from, int to) {

        mc.getProfiler().startSection("Fast Circle");
        float s = (float) (Math.PI / 90.0f);
        float yaw;
        glBegin(GL_TRIANGLE_FAN);
        glVertex2d(x, y);
        float offset = to-from;
        float rad = 0;
        while (rad <= offset) {
            yaw = (from + rad) * s;
            glVertex2d((x + Math.sin(yaw) * radius), (y + Math.cos(yaw) * radius));
            rad += 9;
        }
        glEnd();
        mc.getProfiler().endSection();

    }

    public static void drawCircle(float x, float y, float radius, int color, int from, int to) {

        mc.getProfiler().startSection("Circle");
        set(color);
        glEnable(GL_POLYGON_SMOOTH);
        glBegin(9);
        int i = from;
        glVertex2d(x, y);
        while (i <= to) {
            glVertex2d((x + Math.sin((float) i * Math.PI / 90.0) * radius), (y + Math.cos(i * Math.PI / 90.0) * radius));
            i += 9;
        }
        glEnd();
        glDisable(GL_POLYGON_SMOOTH);
        reset();
        mc.getProfiler().endSection();
    }

    private static void circleLinedSection(float x, float y, float radius, int color, int from, int to, float width) {
        mc.getProfiler().startSection("Circle");
        set(color);
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(width);

        glBegin(GL_LINES);
        int i = from;
        float toRadiant = (float) (Math.PI / 90);
        while (i < to) {
            glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);


            i += 9;
            glVertex2d(x + Math.sin(i * toRadiant) * radius, y + Math.cos(i * toRadiant) * radius);

        }
        glEnd();
        reset();
        mc.getProfiler().endSection();
    }

    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
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

    public static void reset() {
        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glColor4f(1, 1, 1, 1);
    }

    public static void setColor(int color) {
        float alpha = (float) (color >> 24 & 255) / 255.0f;
        float red = (float) (color >> 16 & 255) / 255.0f;
        float green = (float) (color >> 8 & 255) / 255.0f;
        float blue = (float) (color & 255) / 255.0f;
        glColor4f(red, green, blue, alpha);
    }

    public static void activateScissor() {
        glEnable(GL_SCISSOR_TEST);
    }

    public static void deactivateScissor() {
     glDisable(GL_SCISSOR_TEST);
      //  GL11.glPopAttrib();
    }

    public static void createScissorBox(float x, float y, float x2, float y2) {

        float wDiff = x2 - x;
        float hDiff = y2 - y;
        if(wDiff < 0 || hDiff < 0) {
            return;
        }
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
            float nX  = mat[0] * x + mat[4] * y + mat[8] * 0 + mat[12];
            float nY = mat[1] * x + mat[5] * y + mat[9] * 0 + mat[13];
            return new Vector2f(nX,nY);
    }

    

    public static void drawCenteredString(String text, float x, float y, int color) {
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        fontRenderer.drawString(matrixStack,text,x -  getStringWidth(text) / 2f,y-yOffset,color);
        reset();
    }

    public static void drawCenteredStringWithShadow(String text, float x, float y, int color) {
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        fontRenderer.drawStringWithShadow(matrixStack,text,x -  getStringWidth(text) / 2f,y-yOffset,color);
        reset();
    }


    public static void drawString(String text, float x, float y, int color) {
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        fontRenderer.drawString(matrixStack,text,x,y-yOffset,color);
        reset();
    }

    public static void drawStringWithShadow(String text, float x, float y, int color) {
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        fontRenderer.drawStringWithShadow(matrixStack,text,x,y-yOffset,color);
        reset();
    }

    public static float getStringWidth(String text) {
        try {
            return fontRenderer.getStringWidth(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void Bezier(int color, float width, Vector2f... list) {
        mc.getProfiler().startSection("Bezier");
        if (list.length < 2) {
            mc.getProfiler().endSection();
            return;
        } else if (list.length == 2) {
            UGui.drawLine(list[0].x, list[0].y, list[1].x, list[1].y, color, width);
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

        UGui.set(color);

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

        UGui.reset();
        GL11.glPopMatrix();
        mc.getProfiler().endSection();
    }

    public static boolean wasHovered = false;
    public static void Bezier(int color, int color2, float width, Vector2f... list) {

        wasHovered = false;
        mc.getProfiler().startSection("Bezier");



        if(list.length >= 2) {
            Vector2f first = list[0];
            Vector2f last = list[list.length - 1];


            int dirX = first.x > last.x ? -1 : 1;
            int dirY = first.y > last.y ? -1 : 1;
            float radius = 10;
            radius = Math.min(radius,Math.abs(first.y - last.y)/2);
            radius = Math.min(radius,Math.abs(first.x - last.x)/2);


            float midX = (first.x + last.x)/2;
            float midY = (first.y + last.y)/2;
            float w = (last.x - first.x)/2;
            final float midXl = first.x + w - (radius * dirX);
            final float midXr = last.x - w + (radius * dirX);
            drawLine(first.x,first.y, midXl,first.y,color,width);
            drawLine(last.x,last.y, midXr,last.y,color,width);


            drawLine(midX,midY,midX , last.y-(radius*dirY),color,width);
            drawLine(midX,midY,midX , first.y+(radius*dirY),color,width);

            int from = 0 , to = 45;
            int from2 = 90, to2 = 135;
            if(dirY == -1) {
                from = 45;
                to = 90;
                from2 = 135;
                to2 = 180;
            }

            if(dirX == 1) {
                from = 135;
                to = 180;
                from2 = 45;
                to2 = 90;

                if(dirY == -1) {
                    from = 90;
                    to = 135;
                    from2 = 0;
                    to2 = 45;
                }
            }

            circleLinedSection(midXl,first.y+(radius*dirY),radius,color,from2,to2,width);
            circleLinedSection(midXr,last.y-(radius*dirY),radius,color,from,to,width);

           // drawCircle(midX,midY,1,0xFFF23D4C);

            float[] modelViewMatrix = getModelViewMatrix();
            Vector2f mouse = new Vector2f(ScreenMain.instance.globalMouseX, ScreenMain.instance.globalMouseY);

            Vector2f start = toScreenSpace(modelViewMatrix,first.x,first.y);
            Vector2f startEnd = toScreenSpace(modelViewMatrix,last.x,last.y);
            float dx = start.x - mouse.x;
            float dy = start.y - mouse.y;
            float dx2 = startEnd.x - mouse.x;
            float dy2 = startEnd.y - mouse.y;

            float distance = (float) Math.min(Math.sqrt(dx2 * dx2 + dy2 * dy2),Math.sqrt(dx * dx + dy * dy));


            if(distance < 5 * getScale()) {
                wasHovered = true;
            }

            mc.getProfiler().endSection();
            return;
        }


        if (list.length < 2) {
            mc.getProfiler().endSection();
            return;
        } else if (list.length == 2) {
            UGui.drawLine(list[0].x, list[0].y, list[1].x, list[1].y, color, width);
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

        float[] modelViewMatrix = getModelViewMatrix();

        UGui.set(color);

        GL11.glEnable(GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);


        GL11.glBegin(GL11.GL_LINE_STRIP);

        GL11.glVertex2d(0, 0);
        float t = 0;

        while (t <= 1) {

            Vector2f pos = bezierCurvePixel(t, first, Arrays.copyOfRange(list, 1, list.length));
            Vector2f global = toScreenSpace(modelViewMatrix,pos.x,pos.y);
            Vector2f mouse = new Vector2f(ScreenMain.instance.globalMouseX, ScreenMain.instance.globalMouseY);
            float dx = global.x - mouse.x;
            float dy = global.y - mouse.y;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);


            int color3 = UColor.interpolateColor(color, color2, t);
            if(distance < 5 * getScale()) {
                wasHovered = true;
            }

            setColor(color3);

            GL11.glVertex2d(pos.x, pos.y);
            t += precision;
        }
        setColor(color2);

        GL11.glVertex2d(last.x - first.x, last.y - first.y);
        GL11.glEnd();

        UGui.reset();
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

    public static float getScale() {
        float[] modelViewMatrix = getModelViewMatrix();
        return (float) Math.sqrt(modelViewMatrix[0] * modelViewMatrix[0] + modelViewMatrix[1] * modelViewMatrix[1] + modelViewMatrix[2] * modelViewMatrix[2]);
    }
}

