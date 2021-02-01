package net.plixo.paper;

import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.plixo.paper.client.editor.TheEditor;
import net.plixo.paper.client.engine.PaperEngine;
import net.plixo.paper.client.engine.TheManager;
import net.plixo.paper.client.engine.buildIn.blueprint.BlueprintManager;
import net.plixo.paper.client.engine.buildIn.blueprint.Module;
import net.plixo.paper.client.engine.buildIn.blueprint.variable.Variable;

public class Paper {

	static long lastMS = 0;

	public static PaperEngine paperEngine;

	public static void load() {
		TheManager.loadEntiys();
		TheManager.loadGlobals();
		TheManager.loadResources();
		TheManager.loadFunctions();
		TheEditor.init();
	}


	public static void save() {
		Module modToSave = TheEditor.activeMod;
		if (modToSave != null) {
			modToSave.canvas.saveToFile();
		}
		TheManager.saveFunctions();
		TheManager.saveResources();
		TheManager.saveEntitys();
		TheManager.saveGlobals();
	}

	public static void startClient() {
		System.setProperty("java.awt.headless", "false");
		paperEngine = new PaperEngine();
		BlueprintManager.register();
		TheManager.register();

		load();


	}

	public static void update(String name, Variable var) {

		Paper.paperEngine.onEvent(name , var);
		if (System.currentTimeMillis() - lastMS > 60000) {
			if (!paperEngine.isRunning) {
				Paper.save();
			}
			lastMS = System.currentTimeMillis();
		}
	}

}
