package net.plixo.paper.client.engine.buildIn.scripting;

import java.io.File;
import java.io.FileReader;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;

import net.plixo.paper.Paper;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import org.apache.commons.io.FilenameUtils;

public class Script extends Behavior {

	ScriptEngine engine;

	public Script() {
		super("Script");
		setSerializableResources(new Resource("Javascript", File.class, null));
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void onEvent(String name , Variable var) {
		execute(name , var);
		super.onEvent(name , var);
	}

	@Override
	public void start() {

		Resource res = getResource(0);
		engine = Paper.paperEngine.scriptEngineManager.getEngineByName("nashorn");
		
		if (res.hasValue()) {
			try {
				File file = res.getAsFile();
				String extension = FilenameUtils.getExtension(file.getName());
				if(!extension.equalsIgnoreCase(SaveUtil.FileFormat.Code.format)) {
					System.out.println("Wrong Format");
					Paper.paperEngine.stopEngine();
					return;
				}
				engine.eval(new FileReader(file));

				Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);

				execute("onStart" , null);

			} catch (Exception e) {
				Paper.paperEngine.stopEngine();
				e.printStackTrace();
			}
		}

		super.start();
	}


	@Override
	public void stop() {
		execute("onStop" , null);
		super.stop();
	}

	public void execute(String name , Variable var) {
		try {
			Invocable invocable = (Invocable) engine;
			Object obj = engine.get(name);
			if (obj != null) invocable.invokeFunction(name, mc);
		}
		catch (Exception e) {
			Paper.paperEngine.stopEngine();
			e.printStackTrace();
		}
	}
	//TODO redo variable system to javascript
}
