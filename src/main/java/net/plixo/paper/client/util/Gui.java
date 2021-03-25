package net.plixo.paper.client.util;

import net.minecraft.util.math.vector.Vector2f;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;

public class Gui extends Lib2D {

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
            bezierCurvePixel(t, first, Arrays.copyOfRange(list, 1, list.length));
            t += precision;
        }
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

    private static void bezierCurvePixel(float t, Vector2f origin, Vector2f... list) {

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

        GL11.glVertex2d(sumX, sumY);
    }


}
