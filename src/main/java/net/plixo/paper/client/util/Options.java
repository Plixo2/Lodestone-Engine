package net.plixo.paper.client.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.ui.UIElement;
import net.plixo.paper.client.ui.elements.canvas.UIArray;
import net.plixo.paper.client.ui.elements.canvas.UICanvas;
import net.plixo.paper.client.ui.elements.values.UIResource;
import net.plixo.paper.client.util.simple.SimpleColor;
import org.plixo.jrcos.Initializer;
import org.plixo.jrcos.Serializer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Options {

    public static Options options = new Options();


    public Resource<Boolean> useUnicode = new Resource<>("Unicode",true);
    public Resource<Boolean> showMetadata = new Resource<>("Show Meta",false);
    public Resource<Boolean> usePreEvent = new Resource<>("Pre Event",true);

    public Resource<Boolean> useRoundedRects = new Resource<>("Round Rects",true);
    public Resource<Float> roundness = new Resource<>("Radius Modifier",1f);

    public Resource<SimpleColor> darkerFadeColor = new Resource<>("Darker Fade",new SimpleColor(0xFF000000));
    public Resource<Float> darkerFade = new Resource<>("Darker Fade Strength",0.3f);
    public Resource<SimpleColor> brighterFadeColor = new Resource<>("Brighter Fade",new SimpleColor(0xFFFFFFFF));
    public Resource<Float> brighterFade = new Resource<>("Brighter Fade Strength",0.3f);

    public Resource<SimpleColor> backgroundColor = new Resource<>("Background Color",new SimpleColor(0xFF202225));
    public Resource<SimpleColor> backgroundFade = new Resource<>("Fade Color",new SimpleColor(0xFFFFFFFF));
    public Resource<Integer> contrast = new Resource<>("Contrast",100);
    public Resource<SimpleColor> mainColor = new Resource<>("Main Color",new SimpleColor(0xFF2f98f5));


    public transient List<Resource> uiValues = new ArrayList<>();
    public Options()  {
        uiValues.add(useUnicode);
        uiValues.add(showMetadata);
        uiValues.add(usePreEvent);

        uiValues.add(useRoundedRects);
        uiValues.add(roundness);

       uiValues.add(darkerFadeColor);
       uiValues.add(darkerFade);
       uiValues.add(brighterFadeColor);
       uiValues.add(brighterFade);

       uiValues.add(backgroundColor);
       uiValues.add(backgroundFade);
       uiValues.add(contrast);
       uiValues.add(mainColor);
    }

    public UICanvas getOptionsCanvas(float width,float height) {
        UIArray canvas = new UIArray();
        canvas.space = 3;
        for (Resource<?> uiValue : uiValues) {
            UIResource resource = new UIResource();
            resource.setResource(uiValue);
            resource.setDimensions(3,0,width,height);
            canvas.add(resource);
        }

        return canvas;
    }

    static File file = SaveUtil.getFileFromName("options", SaveUtil.FileFormat.Meta);
    public void save() throws Exception {
        JsonElement jsonObject = Serializer.getJson(this);
        SaveUtil.saveJsonObj(file,jsonObject);
    }

    public static Options load(Options options) throws Exception {
        JsonElement element = SaveUtil.loadFromJson(file);
        try {
            return (Options) Initializer.getObject(options,element);
        } catch (Exception e) {
            e.printStackTrace();
        }
      return new Options();
    }
}
