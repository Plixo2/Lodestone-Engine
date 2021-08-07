package net.plixo.lodestone.client.ui.resource.resource;


import net.minecraft.util.math.vector.Vector3d;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;


/**
 *  for editing and displaying a vector in the UI
 *  using three {@code UIPointNumber}
 **/
public class UIVector extends UICanvas {
    //UIPointNumber for XYZ components
     UIPointNumber spinnerX;
     UIPointNumber spinnerY;
     UIPointNumber spinnerZ;


    //set number field position
    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        float spinnerWidth = width / 3;
        setColor(0);
        clear();

        spinnerX = new UIPointNumber();
        spinnerX.setDimensions(0, 0, spinnerWidth, height);
        spinnerX.setRoundness(getRoundness());

        spinnerY = new UIPointNumber();
        spinnerY.setDimensions(spinnerWidth, 0, spinnerWidth, height);
        spinnerY.setRoundness(getRoundness());

        spinnerZ = new UIPointNumber();
        spinnerZ.setDimensions(spinnerWidth * 2, 0, spinnerWidth, height);
        spinnerZ.setRoundness(getRoundness());


        //0xFFFA3237
        //  0xFF32FA68
        //  0xFF4053FA
        spinnerX.setColor(0xFFC2272B);
        spinnerY.setColor(0xFF1B8738);
        spinnerZ.setColor(0xFF303FBA);

        add(spinnerX);
        add(spinnerY);
        add(spinnerZ);
    }

    //set XYZ values
    public void setVector(Vector3d vector3d) {
        spinnerX.setValue(vector3d.x);
        spinnerY.setValue(vector3d.y);
        spinnerZ.setValue(vector3d.z);
    }

    //builds the vector
    public Vector3d getAsVector() {
        return new Vector3d(spinnerX.getAsDouble(), spinnerY.getAsDouble(), spinnerZ.getAsDouble());
    }
}
