package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.editor.blueprint.Rect;
import net.plixo.paper.client.util.Gui;

import java.util.ArrayList;

public class TabHud extends UITab {

    public class RenderObject {

        public Rect backgroundRect;
        public String name;
        float width, height;
        float x, y;

        public RenderObject(String name, float width, float height) {
            this.name = name;
            this.x = 0;
            this.y = 0;
            this.width = width;
            this.height = height;

            backgroundRect = new Rect(0, 0, width, height, -1);
            backgroundRect.txt = name;
        }

        void draw(float totalX, float totalY) {
            redoDimenstion();

            backgroundRect.x = totalX;
            backgroundRect.y = totalY;
            backgroundRect.draw(-1, -1);
        }

        void redoDimenstion() {
            backgroundRect.width = width;
            backgroundRect.height = height;
            backgroundRect.txt = replaceWithAlias(name);
        }
    }


    ArrayList<RenderObject> renderStuff = new ArrayList<RenderObject>();

    public TabHud(int id) {
        super(id, "Hud");
        TheEditor.hud = this;
    }

    @Override
    public void draw(float mouseX, float mouseY) {

        super.draw(mouseX, mouseY);

        Gui.drawCircle(parent.width / 2, parent.height / 2, 4, 0xFF000000);
        Gui.drawCircle(parent.width / 2, parent.height / 2, 3f, 0xFF55AAFF);

        for (RenderObject render : renderStuff) {

            float absolutX = render.x * parent.width;
            float absolutY = render.y * parent.height;

            render.draw(absolutX, absolutY);
        }

    }

    @Override
    public void init() {
        renderStuff.clear();
        renderStuff.add(new RenderObject("My name is %Name%", 100, 30));
        renderStuff.get(0).backgroundRect.color = 0xFF55AAFF;
        renderStuff.get(0).x = 0.5f;
        renderStuff.get(0).y = 0.5f;
        super.init();
    }

    String replaceWithAlias(String txt) {
        String str = txt;


        //	str = Util.replace(str, "%Name%", mc.thePlayer.getName());
        //	str = Util.replace(str, "%Width%", ""+parent.width);
        //	str = Util.replace(str, "%Height%", ""+parent.height);

        return str;
    }

}
