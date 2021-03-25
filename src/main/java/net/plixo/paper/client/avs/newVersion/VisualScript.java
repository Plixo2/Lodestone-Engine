package net.plixo.paper.client.avs.newVersion;

import net.plixo.paper.client.avs.components.function.Function;

import java.io.File;
import java.util.ArrayList;

public class VisualScript {
    public ArrayList<Function> functions = new ArrayList<Function>();
    public String name;
    public File location;

    public VisualScript(File fIle) {
        this.location = fIle;
    }

}
