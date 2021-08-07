package net.plixo.lodestone.client.ui.elements.canvas;

import net.minecraft.util.math.vector.Vector2f;
import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.ui.elements.UIElement;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.util.UColor;
import org.lwjgl.opengl.GL11;

public class UIPages extends UICanvas {

    public UIPages() {
        setColor(UColor.getBackground(-0.2f));
    }

    public static float headHeight = 15;
    float headWidth = 65;
    int page = 0;

    UICanvas header;
    UICanvas body;

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        clear();
        int lineColor = 0;
        header = new UICanvas() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {

                //   Gui.drawLinedRoundedRect(x, y, x + width, y + height, roundness,0x55FFFFFF,1);
                super.drawScreen(mouseX, mouseY);

                UGui.drawLine(x, y, x + width, y,lineColor, 1);
                UGui.drawLine(x, y + height, x + width, y + height,lineColor, 1);
            }
        };
        body = new UICanvas() {
            @Override
            public void drawScreen(float mouseX, float mouseY) {
                drawDefault();
                UGui.drawRect(x-1 ,y-1,x,y+height+1,lineColor);
                UGui.drawRect(x-1 ,y-1,x+width+1,y,lineColor);

                UGui.drawRect(x+width ,y-1,x+width+1,y+height+1,lineColor);
                UGui.drawRect(x-1 ,y+height,x+width+1,y+height+1,lineColor);

                GL11.glPushMatrix();
                GL11.glTranslated(x, y, 0);
                //why?????????
                //why?????????
                //why?????????



                for (int i = 0; i < elements.size(); i++) {
                    if (i == page)
                        elements.get(i).drawScreen(mouseX - x, mouseY - y);
                }

                GL11.glPopMatrix();

                page = Math.min(page, elements.size() - 1);
                updateHoverProgress(mouseX, mouseY);
            }
                //why?????????
            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                for (int i = 0; i < elements.size(); i++) {
                    if (i == page)
                        elements.get(i).mouseClicked(mouseX - x, mouseY - y, mouseButton);
                }
            }
            //why?????????
            @Override
            public void keyTyped(char typedChar, int keyCode) {
                for (int i = 0; i < elements.size(); i++) {
                    if (i == page)
                        elements.get(i).keyTyped(typedChar, keyCode);
                }
            }
            //why?????????
            @Override
            public void keyPressed(int key, int scanCode, int action) {
                for (int i = 0; i < elements.size(); i++) {
                    if (i == page)
                        elements.get(i).keyPressed(key, scanCode, action);
                }
            }
        };

        header.setDimensions(0, 0, width, headHeight);
        header.setColor(UColor.getBackground(0.75f));
        header.setRoundness(0);
        body.setDimensions(0, headHeight, width, height - headHeight);
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
        canvas.setRoundness(0);
        body.add(canvas);

        int id = header.elements.size();
        UIButton switchButton = new UIButton();
        switchButton.setTickAction(() -> {
            if (id == page) {
                switchButton.setColor(UColor.getBackground(0.2f));
            } else {
                switchButton.setColor(0);
            }
        });

        switchButton.setAction(() -> {
            this.page = id;
        });
        switchButton.setRoundness(0);
        switchButton.alignLeft();
        switchButton.setDisplayName(canvas.getDisplayName());
        switchButton.setDimensions(id * headWidth, 0, headWidth, header.height);
        header.add(switchButton);

    }
}
