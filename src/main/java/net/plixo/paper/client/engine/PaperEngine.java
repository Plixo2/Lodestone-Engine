package net.plixo.paper.client.engine;

import java.util.ArrayList;

import javax.script.ScriptEngineManager;

import net.plixo.paper.Paper;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.ecs.Entity;

public class PaperEngine {


	public boolean isRunning = false;

	public ScriptEngineManager scriptEngineManager = new ScriptEngineManager();


	public void onEvent(String name , Variable var) {
		if (isRunning) {
			for (Entity e : TheManager.allEntitys) {
				e.onEvent(name , var);
			}
		}
	}

	public void startEngine() {
		if (!isRunning) {
			isRunning = true;
			System.out.println("Starting Engine");
			Paper.save();

			for (Entity e : TheManager.allEntitys) {
				e.start();
			}

		}
	}

	public void stopEngine() {
		if (isRunning) {
			isRunning = false;
			System.out.println("Stoping Engine");
			for (Entity e : TheManager.allEntitys) {
				e.stop();
			}

			Paper.load();
		}
	}
}
