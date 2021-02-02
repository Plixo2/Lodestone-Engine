package net.plixo.paper.client.editor.tabs;

import net.plixo.paper.client.UI.elements.*;
import net.plixo.paper.client.UI.UITab;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.UniformFunction;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.VariableType;
import net.plixo.paper.client.util.ColorLib;
import net.plixo.paper.client.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;

public class TabFunction extends UITab {

    UICanvas canvas;
    UICanvas search;
    UICanvas build;

    public TabFunction(int id) {
        super(id, "Functions");
        TheEditor.function = this;
    }

    @Override
    public void init() {
        canvas = new UICanvas(0);
        canvas.setDimensions(0, 0, parent.width, parent.height);
        canvas.setRoundness(0);
        canvas.setColor(ColorLib.getBackground(0.3f));

        search = new UICanvas(1);
        search.setDimensions(0, 0, 100, parent.height);
        search.setRoundness(3);
        search.setColor(ColorLib.getBackground(0.3f));


        build = new UICanvas(2);
        build.setDimensions(100,0, parent.width-100, parent.height);
        build.setRoundness(3);
        build.setColor(ColorLib.getBackground(0.3f));


        UIButton addFunction = new UIButton(0) {
            @Override
            public void actionPerformed() {
                TheManager.functions.add(new UniformFunction("" + (int)(Math.random()*100000.0)));
            }
        };
        addFunction.setDimensions(0, parent.height-20, 20,20);
        addFunction.setDisplayName("+F");
        search.add(addFunction);

        canvas.add(search);
        canvas.add(build);

        for (int i = 0; i < TheManager.functions.size(); i++) {
            UniformFunction function = TheManager.functions.get(i);
            UIButton functionButton = new UIButton(i) {
                @Override
                public void actionPerformed() {
                    if(TheManager.functions.contains(function) && KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
                        TheManager.functions.remove(function);
                        return;
                    }
                    initWithFunction(function);
                }
            };
            functionButton.setDimensions(0,i*15 , 100 , 15);
            functionButton.setDisplayName(function.getName());
            search.add(functionButton);
        }



        super.init();
    }

    //TODO redo load (save) system to exeption instead of statements
    public void initWithFunction(UniformFunction function) {
        build.clear();
        float midX = (parent.width-100) / 2;
        float w = 100;
        UITextbox field = new UITextbox(0) {
            @Override
            public void textFieldChanged() {
                function.setName(this.getText());
                super.textFieldChanged();
            }
        };
        //TODO redo drawFunction with custom inputs with UIButtons? if possible ... or a resource system like the behaviors... dont .eval at runtime .... use invoke and compile before

        UIVariable output = new UIVariable(3 , function.output , function);
        output.setDimensions(5 , 5 , w , 20);

        field.setDimensions(midX - w / 2, 10, w, 20);
        field.setDisplayName("Function name");
        field.setText(function.getName());

        UICanvas functionCanvas = new UICanvas(1);
        functionCanvas.setDimensions(midX - w / 2, 30, w, 100);
        functionCanvas.setColor(ColorLib.getDarker(ColorLib.getBackground(0.2f)));
        functionCanvas.setRoundness(4);


        initFromFunction(functionCanvas, function);

        UIButton uiButton = new UIButton(2) {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                super.mouseClicked(mouseX, mouseY, mouseButton);
                if (hovered(mouseX, mouseY)) {
                    Variable var = new Variable(VariableType.BOOLEAN, "");
                    function.addVariable(var);
                    initFromFunction(functionCanvas, function);
                }
            }
        };
        uiButton.setDimensions(midX-w/2 - 20, 10, 20, 20);
        uiButton.setDisplayName("+");
        build.add(uiButton);
        build.add(field);
        build.add(output);
        build.add(functionCanvas);

    }


    public void initFromFunction(UICanvas canvas, UniformFunction function) {
        canvas.clear();
        int index = 0;
        for (Variable var : function.variableArrayList()) {
            UIVariable button = new UIVariable(index, var , function) {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
                    super.mouseClicked(mouseX, mouseY, mouseButton);
                    if(!function.variableArrayList().contains(var)) {
                       // initFromFunction(canvas , function);
                        initWithFunction(function);
                    }
                }
            };
            button.setDimensions(0, index * 20, 100, 20);
            button.setRoundness(4);
            canvas.add(button);
            index += 1;
        }
    }


    //TODO replace draw keypressed mouseClicked ... with interface


    @Override
    public void drawScreen(float mouseX, float mouseY) {
        canvas.draw(mouseX, mouseY);
        //drawOutline();
    }

    @Override
    public void keyPressed(int key, int scanCode, int action) {
        canvas.keyPressed(key, scanCode, action);
        super.keyPressed(key, scanCode, action);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        canvas.keyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        canvas.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}
