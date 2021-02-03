package net.plixo.paper.client.UI.elements;

import net.plixo.paper.client.engine.UniformFunction;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.VariableType;
import net.plixo.paper.client.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("unused")
public class UIVariable extends UISpinner {

    int number = 0;
    Variable var;
    UniformFunction function;

    public UIVariable(int id, Variable var, UniformFunction function) {
        super(id);
        this.var = var;
        this.function = function;
    }

    @Override
    public void otherButton(int id) {
        if (id == 0) {
            number -= 1;
        } else {
            number += 1;
        }
        //    TextFormatting lvt_10_1_ = TextFormatting.fromFormattingCode(type.getColor());
    }

    @Override
    public void setDimensions(float x, float y, float width, float height) {
        super.setDimensions(x, y, width, height);
        this.field.setText(var.name);
        number = var.type.ordinal();
    }

    public VariableType getType() {
        return VariableType.values()[Math.abs(number % VariableType.values().length)];
    }

    @Override
    public void draw(float mouseX, float mouseY) {
        VariableType type = getType();
        int color = type.getColor();
        this.field.setTextColor(color);
        this.setDisplayName(type.name());
        super.draw(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(float mouseX, float mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (hovered(mouseX, mouseY) && KeyboardUtil.isKeyDown(GLFW.GLFW_KEY_DELETE)) {
            function.variableArrayList().remove(var);
        }
        var.type = getType();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (field != null) {
            field.charTyped(typedChar, keyCode);
            var.name = field.getText();
        }
    }
}
