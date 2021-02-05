package net.plixo.paper.client.UI.elements;

import net.minecraft.util.math.vector.Vector3d;
import net.plixo.paper.client.editor.visualscript.Canvas;
import net.plixo.paper.client.util.ColorLib;

public class UIVector extends UICanvas {


    public UIPointNumber spinnerX;
    public UIPointNumber spinnerY;
    public UIPointNumber spinnerZ;

    public UIVector(int id) {
        super(id);
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        float spinnerWidth = width / 3;
        elements.clear();

        spinnerX = new UIPointNumber(0);
        spinnerX.setDimensions(0, 0, spinnerWidth, height);
        spinnerX.setRoundness(0);

        spinnerY = new UIPointNumber(1);
        spinnerY.setDimensions(spinnerWidth, 0, spinnerWidth, height);
        spinnerY.setRoundness(0);

        spinnerZ = new UIPointNumber(2);
        spinnerZ.setDimensions(spinnerWidth * 2, 0, spinnerWidth, height);
        spinnerZ.setRoundness(0);

        spinnerX.color = ColorLib.red();
        spinnerY.color = ColorLib.cyan();
        spinnerZ.color = ColorLib.blue();

        elements.add(spinnerX);
        elements.add(spinnerY);
        elements.add(spinnerZ);
    }

    public void setVector(Vector3d vector3d) {
        spinnerX.setValue(vector3d.x);
        spinnerY.setValue(vector3d.y);
        spinnerZ.setValue(vector3d.z);
    }

    //TODO remove UIMulibutton with UiCanvas
    //TODO add Float option in UISpinner
    public Vector3d getAsVector() {
        return new Vector3d(spinnerX.getAsDouble(), spinnerY.getAsDouble(), spinnerZ.getAsDouble());
    }
}
