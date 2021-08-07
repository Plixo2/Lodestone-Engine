package net.plixo.lodestone.client.ui.resource.resource;

import net.plixo.lodestone.client.ui.UGui;
import net.plixo.lodestone.client.engine.core.Resource;
import net.plixo.lodestone.client.ui.screens.ScreenCanvasUI;
import net.plixo.lodestone.client.ui.elements.canvas.UIArray;
import net.plixo.lodestone.client.ui.elements.canvas.UICanvas;
import net.plixo.lodestone.client.ui.elements.other.UIButton;
import net.plixo.lodestone.client.util.ClassPaths;
import net.plixo.lodestone.client.util.UColor;

/**
 * for displaying and choosing a file with {@code JFileChooser}
 **/
public class UIClass extends UICanvas {

    public Resource<Class<?>> classResource;

    public UIClass() {
        setColor(0);
    }


    @Override
    public void drawScreen(float mouseX, float mouseY) {

        drawDefault( UColor.getBackground(0.3f));
        drawHoverEffect();

        if (classResource != null) {
            UGui.drawString(classResource.value.getSimpleName()+".class",x+5,y+height/2,UColor.getTextColor());
        }

        super.drawScreen(mouseX, mouseY);
    }


    //set dimensions for the choose button
    @Override
    public void setDimensions(float x, float y, float width, float height) {



        UIButton button = new UIButton();
        button.setAction(() -> {
            ScreenCanvasUI guiCanvas = new ScreenCanvasUI() {
                @Override
                protected void init() {
                    super.init();

                    float widthH = canvas.width;

                    UIArray allClasses = new UIArray();
                    allClasses.setDimensions(0,15,widthH,canvas.height-15);
                    allClasses.setColor(UColor.getBackground(0.1f));


                    UITextBox textbox = new UITextBox();
                    textbox.setTextChanged(() -> {
                        allClasses.clear();

                        for (String name : ClassPaths.names) {
                            if(allClasses.elements.size() > 100) {
                                break;
                            }
                            try {
                                String[] splits = name.split("\\.");
                                String last = splits[splits.length-1];
                                if(last.toUpperCase().contains(textbox.getText().toUpperCase()))  {
                                    UIButton button1 = new UIButton();
                                    button1.alignLeft();
                                    button1.setDimensions(0,0,widthH,10);
                                    button1.setDisplayName(name);
                                    button1.setAction(() -> {
                                        try {
                                            classResource.value = Class.forName(name);
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        textbox.setText(classResource.value.getSimpleName());
                                    });
                                    allClasses.add(button1);
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    textbox.setDimensions(0,0,100,15);
                    if (classResource != null) {
                        textbox.setText(classResource.value.getSimpleName());
                    }

                    textbox.getTextChanged().run();
                    add(textbox);
                    add(allClasses);
                }
            };


            mc.displayGuiScreen(guiCanvas);
        });

        button.setDisplayName("?");
        button.setRoundness(2);
        button.setDimensions(width - height, 0, height, height);
        clear();
        add(button);
        super.setDimensions(x, y, width, height);
    }

    public void setReference(Resource<Class<?>> clazz) {
        this.classResource = clazz;
    }

    public Class<?> getClazz() {
        return classResource.value;
    }


}
