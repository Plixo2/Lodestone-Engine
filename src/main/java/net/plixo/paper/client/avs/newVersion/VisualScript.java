package net.plixo.paper.client.avs.newVersion;

import java.io.File;
import java.util.ArrayList;

public class VisualScript {

    ArrayList<nFunction> functions = new ArrayList<>();
    public String name;
    public File location;

    public VisualScript(File fIle) {
        this.location = fIle;
    }
    public VisualScript() {

    }
}
