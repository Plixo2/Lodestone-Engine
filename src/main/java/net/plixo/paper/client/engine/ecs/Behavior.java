package net.plixo.paper.client.engine.ecs;

import net.minecraft.client.Minecraft;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;

public abstract class Behavior {

	public static Minecraft mc = Minecraft.getInstance();
	public GameObject entity;

	public String name;
	public Resource[] serializable = new Resource[0];

	public Behavior(String name) {
		this.name = name;
	}

	public void EditorUpdate() {

	}

	public Resource getResource(int index) {
		return serializable[index];
	}

	public void init() {

	}

	public void onEvent(String name , Variable var) {

	}

	public void setSerializableResources(Resource... res) {
		serializable = res;
	}

	public void start() {

	}

	public void stop() {

	}
}
