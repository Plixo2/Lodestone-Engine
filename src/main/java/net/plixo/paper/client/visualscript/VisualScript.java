package net.plixo.paper.client.visualscript;

import net.plixo.paper.client.visualscript.functions.events.Event;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class VisualScript {

    public CopyOnWriteArrayList<Function> functions = new CopyOnWriteArrayList<>();

    public String name;
    public File location;

    public VisualScript(File file) {
        this.location = file;
        this.name = FilenameUtils.removeExtension(file.getName());
    }

    public void addFunction(Function function, float x, float y) {
        functions.add(function);
        function.set();
        function.ui = new UIFunction(function);
        function.ui.setDimensions(x, y, 120, 20);
    }

    public void removeFunction(Function function) {
        for (Function Function : functions) {
            for (int i = 0; i < Function.links.length; i++) {
                Function link = Function.links[i];
                if (link == function) {
                    Function.links[i] = null;
                }
            }
            for (int i = 0; i < Function.input.length; i++) {
                Function.Output output = Function.input[i];
                if(output != null) {
                    if(output.function == function) {
                        Function.input[i] = null;
                    }
                }
            }
        }
        functions.remove(function);
    }
    public CopyOnWriteArrayList<Function> getFunctions() {
        return functions;
    }

    public Function getByUUID(UUID uuid) {
        for (Function function : functions) {
            if (function.id.equals(uuid)) {
                return function;
            }
        }
        return null;
    }
}
