package net.plixo.paper.client.manager;

import net.plixo.paper.client.util.SaveUtil;
import net.plixo.paper.client.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.script.*;
import javax.tools.*;
//import javax.script.*;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptManager {


    public static ScriptEngine getNewEngine() {
        return new ScriptEngineManager(null).getEngineByName("Nashorn");
    }

    public static boolean setup(File file, ScriptEngine engine) {
        try {
            engine.eval("var util = Java.type(\"net.plixo.paper.client.util.Util\");");
            engine.eval("var mc = util.mc;");
            engine.eval(new FileReader(file));
            return true;
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        return false;
    }

    public static Object findIgnoreCase(String name, ScriptEngine engine) {
        Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        for (String str : b.keySet()) {
            if (str.equalsIgnoreCase(name)) {
                return b.get(str);
            }
        }
        return null;
    }

    public static Object find(String name, ScriptEngine engine) {
        try {
            return engine.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> getAllBindings(ScriptEngine engine) {
        Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        List<Object> list = new ArrayList<>();
        for (String str : b.keySet()) {
            list.add(b.get(str));
        }
        return list;
    }

    public static Object invokeFunction(String name, ScriptEngine engine, Object... objs) {
        try {
            Invocable invocable = (Invocable) engine;
            Object obj = find(name, engine);
            if (obj == null) {
                return null;
            }
            return invocable.invokeFunction(name, objs);
        } catch (Exception e) {
            Util.print(e);
            e.printStackTrace();
        }
        return null;
    }



    public static Object createClass(String JarPath, String JarCode, Object... objects) throws Exception {
        String className = JarPath;
        Path temp = Paths.get(System.getProperty("java.io.tmpdir") + "/VisualScript", className);
        Files.createDirectories(temp);
        Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
        System.out.println("The java source file is loacted at " + javaSourceFile);
        String code = JarCode;
        Files.write(javaSourceFile, code.getBytes());

        final String toolsJarFileName = "tools.jar";
        final String javaHome = System.getProperty("java.home");
        Path toolsJarFilePath = Paths.get(javaHome, "lib", toolsJarFileName);
        if (!Files.exists(toolsJarFilePath)) {
            System.out.println("The tools jar file (" + toolsJarFileName + ") could not be found at (" + toolsJarFilePath + ").");
        }


        File[] files1 = {javaSourceFile.toFile()};

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files1));


        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                null, compilationUnits
        );
        task.call();

        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            System.out.format("Error on line %d in %s%n",
                    diagnostic.getLineNumber(),
                    diagnostic.getSource());
            long line = diagnostic.getLineNumber();
            if (line >= 0)
                Util.print("Error on line " + line + " in " + diagnostic.getSource());
        }

        fileManager.close();

        try {
            ClassLoader classLoader = ScriptManager.class.getClassLoader();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{temp.toUri().toURL()}, classLoader);
            Class javaDemoClass = urlClassLoader.loadClass(className);
            Object runnable = javaDemoClass.getConstructors()[0].newInstance(objects);
            return runnable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteTemp() {
        File file = new File(System.getProperty("java.io.tmpdir") + "/VisualScript");
        System.out.println("temp path: " + file.getAbsolutePath());
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(listFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object loadClassFromFile(File file) {
        String name = FilenameUtils.removeExtension(file.getName());
        String code = SaveUtil.loadAsString(file.getAbsolutePath());
        try {
            return createClass(name, code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
