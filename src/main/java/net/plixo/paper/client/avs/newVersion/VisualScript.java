package net.plixo.paper.client.avs.newVersion;

import net.plixo.paper.client.avs.newVersion.functions.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class VisualScript {

    private CopyOnWriteArrayList<nFunction> functions = new CopyOnWriteArrayList<>();

    public String name;
    public File location;

    public VisualScript(File file) {
        this.location = file;
    }


    public void addFunction(nFunction function, float x, float y) {
        functions.add(function);
        function.set();
        function.ui = new nUIFunction(function);
        function.ui.setDimensions(x, y, 120, 20);
    }

    public void removeFunction(nFunction function) {

        for (nFunction nFunction : functions) {
            for (int i = 0; i < nFunction.links.length; i++) {
                nFunction link = nFunction.links[i];
                if (link == function) {
                    nFunction.links[i] = null;
                }
            }
            for (int i = 0; i < nFunction.input.length; i++) {
                net.plixo.paper.client.avs.newVersion.nFunction.Output output = nFunction.input[i];
                if(output != null) {
                    if(output.function == function) {
                        nFunction.input[i] = null;
                    }
                }
            }
        }
        functions.remove(function);
    }

    public CopyOnWriteArrayList<nFunction> getFunctions() {
        return functions;
    }

    public nFunction getByUUID(UUID uuid) {
        for (nFunction function : functions) {
            if (function.id.equals(uuid)) {
                return function;
            }
        }
        return null;
    }

    public nFunction getEvent() {
        for (nFunction function : functions) {
            if (function instanceof Event) {
                return function;
            }
        }
        return null;
    }
}
