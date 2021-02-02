package net.plixo.paper.client.engine.buildIn.visualscript;

import java.io.File;

import net.plixo.paper.Lodestone;
import net.plixo.paper.client.editor.visualscript.Canvas;
import net.plixo.paper.client.engine.buildIn.visualscript.variable.Variable;
import net.plixo.paper.client.engine.ecs.Behavior;
import net.plixo.paper.client.engine.ecs.Resource;
import net.plixo.paper.client.util.SaveUtil;
import org.apache.commons.io.FilenameUtils;


public class VisualScript extends Behavior {

	Module mod;

	public VisualScript() {
		super("Visual Script");
		setSerializableResources(new Resource("File", File.class, null));
	}


	@Override
	public void onEvent(String name, Variable var) {
		try {
			if (mod != null) {
				mod.canvas.execute(name, var);
			}
		} catch (Exception e) {
			Lodestone.paperEngine.stopEngine();
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
					Lodestone.paperEngine.stopEngine();
					return;
				}
				String name = FilenameUtils.removeExtension(file.getName());
				mod = new Module(name, file);
				mod.canvas = new Canvas(mod);
				mod.canvas.init();
				mod.canvas.execute("onStart", null);

			} catch (Exception e) {
				Lodestone.paperEngine.stopEngine();
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
			Lodestone.paperEngine.stopEngine();
			e.printStackTrace();
		}
		super.stop();
	}
}
