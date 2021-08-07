package net.plixo.lodestone.client.visualscript;

import net.plixo.lodestone.client.manager.REditor;
import net.plixo.lodestone.client.ui.visualscript.UIFunction;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class VisualCode {

    public CopyOnWriteArrayList<Function> functions = new CopyOnWriteArrayList<>();

    public String name;
    public File location;
    public Object object;

    public VisualCode(File file) {
        this.location = file;
        this.name = FilenameUtils.removeExtension(file.getName());
    }

    public void addFunction(Function function, float x, float y) {
        functions.add(function);
        function.set();
        function.setUI(new UIFunction(function));
        function.getUI().setDimensions(x, y, function.getUI().getWidth(), 20);
        REditor.viewport.add(function.getUI());
    }


    public void removeFunction(Function function) {
        REditor.viewport.elements.remove(function.getUI());
        for (net.plixo.lodestone.client.visualscript.Function Function : functions) {
            for (int i = 0; i < Function.getLinks().length; i++) {
                net.plixo.lodestone.client.visualscript.Function link = Function.getLinks()[i];
                if (link == function) {
                    Function.getLinks()[i] = null;
                }
            }
            for (int i = 0; i < Function.getInput().length; i++) {
                net.plixo.lodestone.client.visualscript.Function.Output output = Function.getInput()[i];
                if (output != null) {
                    if (output.function == function) {
                        Function.getInput()[i] = null;
                    }
                }
            }
        }
        functions.remove(function);
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public Function getByUUID(UUID uuid) {
        for (Function function : functions) {
            if (function.getId().equals(uuid)) {
                return function;
            }
        }
        return null;
    }
}
