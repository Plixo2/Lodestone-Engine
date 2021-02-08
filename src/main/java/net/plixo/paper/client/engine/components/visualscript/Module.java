package net.plixo.paper.client.engine.components.visualscript;

import net.plixo.paper.client.editor.visualscript.Canvas;

import java.io.File;

public class Module {


    public Canvas canvas;

    public File location;
    public String name;
    @SuppressWarnings("unused")
	boolean state = false;

    public Module(String name, File location) {
        this.name = name;
        this.location = location;
    }

    public Canvas getTab() {
        return canvas;
    }


}
