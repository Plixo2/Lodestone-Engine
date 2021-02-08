package net.plixo.paper.client.util;

import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("CommentedOutCode")
public class Gui extends Lib2D {

    //TODO redo bezier functions lol
    @SuppressWarnings("unused")
    public static void Bezier(double xCoord, double zCoord, double xCoord2, double zCoord2, int color, int dir,
                              float width) {
        Bezier(xCoord, zCoord, xCoord2, zCoord2, color, dir, GL11.GL_LINE_STRIP, true, width, color, 10, 10, 10);
    }

    public static void Bezier(double xCoord, double zCoord, double xCoord2, double zCoord2, int color, int dir,
                              int mode, boolean blend, float width, int otherColor, int animationOffset, int animationSize,
                              int animationWidth) {

        double ix = xCoord - xCoord2;
        double iy = zCoord - zCoord2;

        animationOffsetF = animationOffset;
        animationSizeF = animationSize;
        animationWidthF = animationWidth;
        // GL11.GL_LINES
        // GL11.GL_LINE_STRIP
        // GL11.GL_POINTS

        @SuppressWarnings("UnusedAssignment") int i = (int) (Math.abs(iy) + Math.abs(ix)) / 2;

        i = 30;

        if (dir == 1) {
            i = -i;
        }

        float precision = (float) (1 / Math.sqrt(iy * iy + ix * ix)) / 0.2f;
        precision = Math.min(0.07f, precision);
        // precision = 0.01f;

        points[0] = new Vector3d(i, 0, 0);
        points[1] = new Vector3d((xCoord2 - i) - xCoord, zCoord2 - zCoord, 0);
        points[2] = new Vector3d(xCoord2 - xCoord, zCoord2 - zCoord, 0);

        index = 0;

        GL11.glPushMatrix();
        GL11.glTranslated(xCoord, zCoord, 0);

        GL11.glBlendFunc(770, 771);
        if (blend)
            GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(width);
        GL11.glPointSize(width);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        pColor = color;
        sColor = otherColor;
        // float f3 = (float) (color >> 24 & 255) / 255.0F;
        // float f = (float) (color >> 16 & 255) / 255.0F;
        // float f1 = (float) (color >> 8 & 255) / 255.0F;
        // float f2 = (float) (color & 255) / 255.0F;
        // GlStateManager.color(f, f1, f2, f3);

        GL11.glBegin(mode);

        GL11.glVertex2d(0, 0);
        drawScene(precision);
        GL11.glVertex2d(xCoord2 - xCoord, zCoord2 - zCoord);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

    }

    static public Vector3d[] points = {new Vector3d(0, 0, 0), new Vector3d(0, 0, 0), new Vector3d(0, 0, 0)};
    static int pointCount = 3;
    static int index = 0;
    static int pColor = 0;
    static int sColor = 0;
    static int animationOffsetF = 10;
    static int animationSizeF = 10;
    static int animationWidthF = 10;

    private static void drawScene(float precision) {

        if (pointCount > 1) {
            float t = 0;
            while (t <= 1) {
                bezierCurvePixel(t);
                t += precision;
            }
        }
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

    private static void bezierCurvePixel(float t) {

        float[] bPoly = new float[pointCount];

        for (int i = 0; i < pointCount; i++) {
            bPoly[i] = bernstein(t, pointCount, i + 1);
        }

        float sumX = 0;
        float sumY = 0;

        for (int i = 0; i < pointCount; i++) {
            sumX += bPoly[i] * points[i].x;
            sumY += bPoly[i] * points[i].y;
        }

        @SuppressWarnings("unused") float x, y;
        // x = Math.round(sumX);
        // y = Math.round(sumY);

        index += 1;

        GL11.glVertex2d(sumX, sumY);

        if (((index % animationSizeF) - animationOffsetF) >= 0
                && ((index % animationSizeF) - animationOffsetF) <= animationWidthF) {
            setColor(sColor);
        } else {
            setColor(pColor);
        }
    }

    static void setColor(int color) {
        float f3a = (float) (color >> 24 & 255) / 255.0F;
        float fa = (float) (color >> 16 & 255) / 255.0F;
        float f1a = (float) (color >> 8 & 255) / 255.0F;
        float f2a = (float) (color & 255) / 255.0F;
        GL11.glColor4f(fa, f1a, f2a, f3a);
    }



}
