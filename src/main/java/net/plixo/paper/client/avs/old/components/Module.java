package net.plixo.paper.client.avs.old.components;

import net.plixo.paper.client.avs.old.ui.Canvas;

import java.io.File;

public class Module {


    public Canvas canvas;

    public File location;
    public String name;

	boolean state = false;

    public Module(String name, File location) {
        this.name = name;
        this.location = location;
    }

    public Canvas getTab() {
        return canvas;
    }


}