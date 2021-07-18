package net.plixo.paper.client.ui.elements.canvas;

import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.clickable.UIButton;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.Gui;
import org.lwjgl.opengl.GL11;

public class UIPages extends UICanvas {

    public UIPages() {
        this.color = ColorLib.getBackground(-0.2f);
    }
    public static float headHeight = 15;
    float headWidth = 65;
    int page = 0;

    UICanvas header;
    UICanvas body;

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        clear();
        header = new UICanvas() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                Gui.drawLine(x,y,x+width,y,0x55FFFFFF,1);
                Gui.drawLine(x,y+height,x+width,y+height,0x55FFFFFF,1);
             //   Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness,0x55FFFFFF,1);
                super.drawScreen(mouseX, mouseY);
            }
        };
        body = new UICanvas() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                Gui.drawRoundedRect(x, y, x + width, y + height, roundness, this.color);

                GL11.glPushMatrix();
                GL11.glTranslated(x, y, 0);
                for (int i = 0; i < elements.size(); i++) {
                    if(i == page)
                        elements.get(i).drawScreen(mouseX - x, mouseY - y);
                }
                GL11.glPopMatrix();

                page = Math.min(page , elements.size()-1);
                updateHoverProgress(mouseX, mouseY);
            }

            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                for (int i = 0; i < elements.size(); i++) {
                    if(i == page)
                        elements.get(i).mouseClicked(mouseX - x, mouseY - y, mouseButton);
                }
            }

            @Override
            public void keyTyped(char typedChar, int keyCode) {
                for (int i = 0; i < elements.size(); i++) {
                    if(i == page)
                        elements.get(i).keyTyped(typedChar, keyCode);
                }
            }

            @Override
            public void keyPressed(int key, int scanCode, int action) {
                for (int i = 0; i < elements.size(); i++) {
                    if(i == page)
                        elements.get(i).keyPressed(key, scanCode, action);
                }
            }
        };

        header.setDimensions(0,0,width,headHeight);
        header.setColor(ColorLib.getBackground(0));
        header.setRoundness(0);
        body.setDimensions(0,headHeight,width,height-headHeight);
        body.setColor(0);

        elements.add(body);
        elements.add(header);
        super.setDimensions(x, y, width, height);
    }

    @Override
    public void drawScreen(float mouseX, float mouseY) {
        super.drawScreen(mouseX, mouseY);
    }

    @Override
    public void add(UIElement canvas) {
        
        canvas.setDimensions(0, 0, body.width, body.height);
        body.add(canvas);

        int id = header.elements.size();
        UIButton switchButton = new UIButton() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                if(id == page) {
                    this.color = ColorLib.getDarker(ColorLib.getMainColor());
                } else {
                    this.color = ColorLib.getMainColor();
                }
                super.drawScreen(mouseX, mouseY);
            }
        };

        switchButton.setAction(() -> {
            this.page = id;
        });
        switchButton.setDisplayName(canvas.displayName);
        switchButton.setDimensions(id * headWidth, 0, headWidth, header.height);
        header.add(switchButton);

    }
}
