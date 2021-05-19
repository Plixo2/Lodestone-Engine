package net.plixo.paper.client.ui;


import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3i;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;

public class Rect {

    public enum Alignment {
        DOWN, LEFT, RIGHT, UP
    }

    Alignment alignment;

    public int color, hoverColor;
    public Vector3i custom;
    public float height;
    String hoverString;

    public int hoverTxtColor = 0;

    public int id;
    public Rect parent;

    public float roundness = 4;

    public String txt;
    Alignment txtAlignment;

    public float width;

    public float x;

    public float y;

    @SuppressWarnings("unused")
    public Rect(float x, float y, float width, float height, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hoverColor = color;
    }

    public Rect(float x, float y, float width, float height, int color, int hoverColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.hoverColor = hoverColor;
    }

    public Rect(float x, float y, float width, float height, int color, int hoverColor, Rect parent,
                Alignment alignment) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.parent = parent;
        this.alignment = alignment;

        this.color = color;
        this.hoverColor = hoverColor;
    }

    public void draw(float mouseX, float mouseY) {

        boolean hover = mouseInside(mouseX, mouseY, -1);
        int color = hover ? hoverColor : this.color;


        Vector2f parentOffset = getOffset();


        //float f = Math.min(Math.min(this.width/2, this.height/2), 116);
        Gui.drawRoundedRect(parentOffset.x + x, parentOffset.y + y, parentOffset.x + x + width, parentOffset.y + y + height, roundness, ColorLib.getDarker(color));
        Gui.drawRoundedRect(parentOffset.x + x + 0.5f, parentOffset.y + y + 0.5f, parentOffset.x + x + width - 0.5f, parentOffset.y + y + height - 0.5f, roundness, color);

        //noinspection ConstantConditions
        if (txt != null && !txt.isEmpty() && !txt.isEmpty()) {
            String txt = this.txt;
            int txtColor = -1;
            if (hover) {
                if (hoverString != null) {
                    txt = hoverString;
                }

                if (hoverTxtColor != 0) {
                    txtColor = hoverTxtColor;
                }

            }

            if (txtAlignment != null) {
                switch (txtAlignment) {
                    case LEFT:
                        Gui.drawString(txt, parentOffset.x + x + 2, parentOffset.y + y + height / 2, txtColor);
                        return;
                    case RIGHT:
                        Gui.drawString(txt, parentOffset.x + x + width - Gui.getStringWidth(txt) - 2,
                                parentOffset.y + y + height / 2, txtColor);
                        return;
                    default:
                        break;
                }
            }

            Gui.drawCenteredString(txt, parentOffset.x + x + width / 2, parentOffset.y + y + height / 2,
                    txtColor);

        }
    }

    public float getHeight() {
        return height;
    }


    Vector2f getOffset() {
        float parentX = 0;
        float parentY = 0;

        if (parent != null) {
            Vector2f total = parent.getTotalPosition();
            parentX = total.x;
            parentY = total.y;

            switch (alignment) {
                case LEFT:
                    parentX += -width;
                    break;
                case RIGHT:
                    parentX += parent.width;
                    break;
                case UP:
                    parentY += -height;
                    break;
                case DOWN:
                    parentY += parent.height;
                    break;
            }
        }

        return new Vector2f(parentX, parentY);
    }

    public Vector2f getTotalPosition() {
        Vector2f parentOffset = getOffset();
        return new Vector2f(x + parentOffset.x, y + parentOffset.y);
    }

    @SuppressWarnings("unused")
    public boolean mouseInside(float mouseX, float mouseY, int mouseButton) {

        Vector2f parentOffset = getOffset();

        return mouseX > parentOffset.x + x && mouseX <= parentOffset.x + x + width && mouseY > parentOffset.y + y && mouseY <= parentOffset.y + y + height;

    }


    public void setCustomVector(Vector3i custom) {
        this.custom = custom;
    }

    public void setHoverTxt(String txt, int color) {
        this.hoverTxtColor = color;
        this.hoverString = txt;
    }


    public void setTxt(String txt, Alignment txtAlignment) {
        this.txt = txt;
        this.txtAlignment = txtAlignment;
    }
}
