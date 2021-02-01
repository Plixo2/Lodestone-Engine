package net.plixo.paper.client.engine.buildIn.blueprint;

import java.io.File;

import net.plixo.paper.Paper;
import net.plixo.paper.client.editor.blueprint.Canvas;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import org.apache.commons.io.FilenameUtils;


public class Blueprint extends Behavior {

	Module mod;

	public Blueprint() {
		super("Blueprint");
		setSerializableResources(new Resource("File", File.class, null));
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void onEvent(String name, Variable var) {
		try {
			if (mod != null) {
				mod.canvas.execute(name, var);
			}
		} catch (Exception e) {
			Paper.paperEngine.stopEngine();
			e.printStackTrace();
		}
		super.onEvent(name, var);
	}



	@Override
	public void start() {
		Resource res = getResource(0);
		if (res.hasValue()) {
			try {
				File file = res.getAsFile();
				String extension = FilenameUtils.getExtension(file.getName());
				if(!extension.equals(SaveUtil.FileFormat.VisualScript.format)) {
					System.out.println("Wrong Format");
					Paper.paperEngine.stopEngine();
					return;
				}
				String name = FilenameUtils.removeExtension(file.getName());
				mod = new Module(name, file);
				mod.canvas = new Canvas(mod);
				mod.canvas.init();
				mod.canvas.execute("onStart", null);

			} catch (Exception e) {
				Paper.paperEngine.stopEngine();
				e.printStackTrace();
			}
		}
		super.start();
	}

	@Override
	public void stop() {
		try {
			if (mod != null) {
				mod.canvas.execute("onStop", null);
			}
		} catch (Exception e) {
			Paper.paperEngine.stopEngine();
			e.printStackTrace();
		}
		super.stop();
	}
}