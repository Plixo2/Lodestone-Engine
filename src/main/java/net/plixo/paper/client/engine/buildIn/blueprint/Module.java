package net.plixo.paper.client.engine.buildIn.blueprint;

import net.plixo.paper.client.editor.blueprint.Canvas;

import java.io.File;

public class Module {

	
	public Canvas canvas;
	
	public File location;
	public String name;
	boolean state = false;
	
	public Module(String name , File location) {
		this.name = name;  
		this.location = location;
	}
	
	public Canvas getTab() {
		return canvas;
	}
	
	

	

}
