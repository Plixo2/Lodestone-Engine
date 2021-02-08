package net.plixo.paper.client.engine.components.visualscript;

import net.plixo.paper.client.engine.components.visualscript.event.buildIn.EventOnEnd;
import net.plixo.paper.client.engine.components.visualscript.event.buildIn.EventOnKey;
import net.plixo.paper.client.engine.components.visualscript.event.buildIn.EventOnStart;
import net.plixo.paper.client.engine.components.visualscript.event.buildIn.EventOnTick;
import net.plixo.paper.client.engine.components.visualscript.function.Function;

import net.plixo.paper.client.engine.components.visualscript.function.buildIn.custom.JavaScriptFunction;
import net.plixo.paper.client.engine.components.visualscript.function.buildIn.io.ELog;
import net.plixo.paper.client.engine.components.visualscript.function.buildIn.other.EBranch;
import net.plixo.paper.client.engine.components.visualscript.function.buildIn.other.EIf;
import net.plixo.paper.client.engine.components.visualscript.function.buildIn.other.ELoop;
import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;


public class VisualScriptManager {

    public static ArrayList<Function> allFunctions = new ArrayList<Function>();
    public static int draggedType = 0;
    public static int dragIndex = -1;

    public static int dragTab = -1;

    public static Function getFromList(String name, String textBox) {
        ArrayList<Function> functions = new ArrayList<>();

        for (Function fun : allFunctions) {
            Class<?> c;
            try {
                c = fun.getClass();
                Constructor<?> construct = null;
                for (Constructor<?> aw : c.getConstructors()) {
                    if (aw.getParameterCount() == 0) {
                        Object object = aw.newInstance();
                        Function function = (Function) object;
                        if(function instanceof  JavaScriptFunction) {
                            JavaScriptFunction parent = (JavaScriptFunction) fun;
                            JavaScriptFunction child = (JavaScriptFunction) function;
                            child.set(parent.file);
                        }
                        functions.add(function);
                        break;
                    } else  {
                        Util.print("Error at loading a class" + fun.name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Function function : functions) {

            if (function.name.equalsIgnoreCase(name)) {

                if (!textBox.isEmpty() && function.customData != null) {
                    Util.loadIntoVar(function.customData, textBox);
                }

                return function;
            }
        }
        return null;
    }

    public static void register() {
        allFunctions.clear();
        allFunctions.add(new EventOnKey());
        allFunctions.add(new EventOnTick());
        allFunctions.add(new EventOnStart());
        allFunctions.add(new EventOnEnd());
        allFunctions.add(new ELog());

        allFunctions.add(new EIf());
        allFunctions.add(new EBranch());
        allFunctions.add(new ELoop());

        File library = SaveUtil.getFolderFromName("library");
        if(!library.exists()) {
            SaveUtil.makeFolder(library);
            return;
        }
        //  Collection<File> files = FileUtils.listFiles(library, new String[] {SaveUtil.FileFormat.Code.format} , true);
        ArrayList<File> files = new ArrayList<>();
        listf(library.getAbsolutePath(),files);
        for(File f : files) {
            JavaScriptFunction jsFunction = new JavaScriptFunction();
            jsFunction.set(f);
            allFunctions.add(jsFunction);
        }

		/*
		allFunctions.add(new getKey());



		allFunctions.add(new EJump());
		allFunctions.add(new EsetSpeed());
		allFunctions.add(new getSpeed());

		allFunctions.add(new EsetYaw());

		allFunctions.add(new getPlayerID());
		allFunctions.add(new getHealth());
		allFunctions.add(new getPosition());
		allFunctions.add(new getYaw());
		allFunctions.add(new Ground());
		allFunctions.add(new getNearestEntity());

		allFunctions.add(new EIf());
		allFunctions.add(new EBranch());
		allFunctions.add(new ELoop());

		// Vars
		allFunctions.add(new SAdd());
		allFunctions.add(new SEquals());
		allFunctions.add(new SEqualsCase());

		allFunctions.add(new VAdd());
		allFunctions.add(new VRotation());
		allFunctions.add(new VSubtract());

		allFunctions.add(new BAnd());
		allFunctions.add(new BEquals());
		allFunctions.add(new BNotequals());
		allFunctions.add(new BOr());
		allFunctions.add(new BNot());

		allFunctions.add(new FEquals());
		allFunctions.add(new FGreater());
		allFunctions.add(new FLess());
		allFunctions.add(new FAdd());
		allFunctions.add(new FMultiply());

		allFunctions.add(new IEquals());
		allFunctions.add(new IGreater());
		allFunctions.add(new ILess());
		allFunctions.add(new IAdd());
		allFunctions.add(new IMod());

		// getters and setters
		allFunctions.add(new getFloat());
		allFunctions.add(new getString());
		allFunctions.add(new getBoolean());
		allFunctions.add(new getVector());
		allFunctions.add(new getInt());

		allFunctions.add(new SetGlobalVariable());
		allFunctions.add(new GetGlobalVariable());
		*/

    }
    public static void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if(fList != null)
            for (File file : fList) {
                if (file.isFile() && file.getName().endsWith(SaveUtil.FileFormat.Code.format)) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }
}
