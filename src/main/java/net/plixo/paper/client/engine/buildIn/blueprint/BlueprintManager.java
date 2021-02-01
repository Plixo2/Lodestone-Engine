package net.plixo.paper.client.engine.buildIn.blueprint;

import net.plixo.paper.client.engine.buildIn.blueprint.event.buildIn.EventOnEnd;
import net.plixo.paper.client.engine.buildIn.blueprint.event.buildIn.EventOnKey;
import net.plixo.paper.client.engine.buildIn.blueprint.event.buildIn.EventOnStart;
import net.plixo.paper.client.engine.buildIn.blueprint.event.buildIn.EventOnTick;
import net.plixo.paper.client.engine.buildIn.blueprint.function.Function;

import net.plixo.paper.client.engine.buildIn.blueprint.function.buildIn.io.ELog;
import net.plixo.paper.client.util.Util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;


public class BlueprintManager {

    public static ArrayList<Function> allFunctions = new ArrayList<Function>();
    public static int draggedType = 0;
    public static int dragIndex = -1;

    public static int dragTab = -1;

    public static Function getFromList(String name, String textBox) {
        ArrayList<Function> functions = new ArrayList<Function>();

        for (Function fun : allFunctions) {
            Class<?> c;
            try {
                c = fun.getClass();
                Constructor<?> construct = null;
                for (Constructor<?> aw : c.getConstructors()) {
                    if (aw.getParameterCount() == 0) {
                        Object object = aw.newInstance();
                        functions.add((Function) object);
                        break;
                    } else if (aw.getParameterCount() >= 1) {
                        System.out.println("Error!!!");
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

        allFunctions.add(new EventOnKey());
        allFunctions.add(new EventOnTick());
        allFunctions.add(new EventOnStart());
        allFunctions.add(new EventOnEnd());
        allFunctions.add(new ELog());
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
}
